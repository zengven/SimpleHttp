package com.yexin.httplib;

import android.util.ArrayMap;
import android.util.Log;

import com.yexin.httplib.request.DefaultBuildParamter;
import com.yexin.httplib.request.IBuildParamter;
import com.yexin.httplib.response.DefaultResponseConverter;
import com.yexin.httplib.response.ProgressCallback;
import com.yexin.httplib.response.ResponseCallback;
import com.yexin.httplib.utils.CheckUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * author: zengven
 * date: 2019/4/2 14:09
 * desc:
 */
public final class SimpleHttp {
    private static final String TAG = "SimpleHttp";
    private final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();
    private final Executor mainThreadExecutor = Platform.get().defaultCallbackExecutor();
    HttpUrl baseUrl;
    Call.Factory callFactory;
    IBuildParamter buildParamterFactory;
    Converter<ResponseBody, ?> converter;

    SimpleHttp(Call.Factory callFactory, HttpUrl baseUrl, IBuildParamter buildParamterFactory, Converter<ResponseBody, ?> converter) {
        this.callFactory = callFactory;
        this.baseUrl = baseUrl;
        this.buildParamterFactory = buildParamterFactory;
        this.converter = converter;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> service) {
        CheckUtil.validateServiceInterface(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // If the method is a method from Object then defer to normal invocation.
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                ServiceMethod serviceMethod = loadServiceMethod(method);
                //获取最后一个参数,默认为响应回调
                Object responseCallback = args[args.length - 1];
                if (!(responseCallback instanceof ResponseCallback)) {
                    throw new IllegalArgumentException("the last parmerter must be extends ResponseCallback class");
                }
                //网络请求
                requestAsync(serviceMethod, args, (ResponseCallback) responseCallback);
                return null;
            }
        });
    }

    private void requestAsync(ServiceMethod serviceMethod, Object[] args, final ResponseCallback<Object> responseCallback) {
        Object paramters = serviceMethod.buildParamters(args);
        Request.Builder builder = null;
        if (serviceMethod.httpMethod.equals("GET")) {
            StringBuilder link = new StringBuilder(serviceMethod.relativeUrl);
            if (!serviceMethod.relativeUrl.endsWith("?")) {
                link.append("?");
            }
            link.append(paramters.toString());
            HttpUrl url = serviceMethod.baseUrl.newBuilder(link.toString()).build();
            Log.i(TAG, "requestAsync get : " + url.toString());
            builder = new Request.Builder().get().url(url);
        } else if (serviceMethod.httpMethod.equals("POST")) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), paramters.toString());
            HttpUrl url = serviceMethod.baseUrl.newBuilder(serviceMethod.relativeUrl).build();
            Log.i(TAG, "requestAsync post : " + url.toString());
            builder = new Request.Builder().post(requestBody).url(url);
        } else {
            throw new IllegalArgumentException("not support request method ");
        }
        //添加header
        addHeaders(builder, serviceMethod);
        responseCallback.onStart();
        callFactory.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mainThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        responseCallback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                ResponseBody body = response.body();
                try {
                    final Object convert = converter.convert(body, responseCallback.clazz);
                    mainThreadExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            responseCallback.onSuccess(convert);
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    mainThreadExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            responseCallback.onFailure(e);
                        }
                    });
                } finally {
                    body.close();
                }
            }
        });
    }

    private void addHeaders(Request.Builder builder, ServiceMethod serviceMethod) {
        ArrayMap<String, String> headers = serviceMethod.headers;
        if (headers != null && !headers.isEmpty()) {
            Set<Map.Entry<String, String>> entries = headers.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                builder.addHeader(next.getKey(), next.getValue());
            }
        }
    }

    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result = serviceMethodCache.get(method);
        if (result != null) return result;
        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(this, method).build();
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }

    /**
     * down file
     *
     * @param url
     * @param file
     * @param progressCallback
     */
    public void download(String url, final File file, final ProgressCallback progressCallback) {
        if (progressCallback == null) {
            throw new IllegalArgumentException("the progresscallback must be not null ");
        }
        Request request = new Request.Builder().url(url).build();
        progressCallback.onStart();
        callFactory.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mainThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        progressCallback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    InputStream inputStream = responseBody.byteStream();
                    long contentLength = responseBody.contentLength();
                    MediaType mediaType = responseBody.contentType();
                    if (file.exists()) {
                        file.delete();
                    }
                    BufferedOutputStream bos = null;
                    byte[] buffer = new byte[2048];
                    int length = 0;
                    int currentLength = 0;
                    try {
                        bos = new BufferedOutputStream(new FileOutputStream(file));
                        while ((length = inputStream.read(buffer)) != -1) { //-1,输入流已经读完
                            bos.write(buffer, 0, length);
                            bos.flush();
                            progressCallback.onProgress(currentLength += length);
                        }
                        mainThreadExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                progressCallback.onSuccess(file);
                            }
                        });
                    } catch (final IOException e) {
                        e.printStackTrace();
                        mainThreadExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                progressCallback.onFailure(e);
                            }
                        });
                    } finally {
                        try {
                            if (bos != null) {
                                bos.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    mainThreadExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            progressCallback.onFailure(new Exception("response is null "));
                        }
                    });
                }
            }
        });
    }

    public static final class Builder {

        private HttpUrl baseUrl;
        private Call.Factory callFactory;
        private IBuildParamter buildParamterFactory;
        private Converter<ResponseBody, ?> converter;

        /**
         * Set the API base URL.
         *
         * @see #baseUrl(HttpUrl)
         */
        public Builder baseUrl(String baseUrl) {
            CheckUtil.checkNotNull(baseUrl, "baseUrl == null");
            HttpUrl httpUrl = HttpUrl.parse(baseUrl);
            if (httpUrl == null) {
                throw new IllegalArgumentException("Illegal URL: " + baseUrl);
            }
            return baseUrl(httpUrl);
        }

        /**
         * Set the API base URL.
         *
         * @param baseUrl
         */
        public Builder baseUrl(HttpUrl baseUrl) {
            CheckUtil.checkNotNull(baseUrl, "baseUrl == null");
            List<String> pathSegments = baseUrl.pathSegments();
            if (!"".equals(pathSegments.get(pathSegments.size() - 1))) {
                throw new IllegalArgumentException("baseUrl must end in /: " + baseUrl);
            }
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * The HTTP client used for requests.
         * <p>
         * This is a convenience method for calling {@link #callFactory}.
         */
        public Builder client(OkHttpClient client) {
            return callFactory(CheckUtil.checkNotNull(client, "client == null"));
        }

        /**
         * Specify a custom call factory for creating {@link Call} instances.
         * <p>
         * Note: Calling {@link #client} automatically sets this value.
         */
        public Builder callFactory(okhttp3.Call.Factory factory) {
            this.callFactory = CheckUtil.checkNotNull(factory, "factory == null");
            return this;
        }

        public Builder buildParamterFactory(IBuildParamter buildParamterFactory) {
            this.buildParamterFactory = CheckUtil.checkNotNull(buildParamterFactory, "factory == null");
            return this;
        }

        public Builder addResponseConverter(Converter<ResponseBody, ?> converter) {
            this.converter = CheckUtil.checkNotNull(converter, "converter == null");
            return this;
        }

        public SimpleHttp build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }

            okhttp3.Call.Factory callFactory = this.callFactory;
            if (callFactory == null) {
                callFactory = new OkHttpClient();
            }

            IBuildParamter buildParamterFactory = this.buildParamterFactory;
            if (buildParamterFactory == null) {
                buildParamterFactory = new DefaultBuildParamter();
            }

            Converter<ResponseBody, ?> converter = this.converter;
            if (converter == null) {
                converter = new DefaultResponseConverter();
            }

            return new SimpleHttp(callFactory, baseUrl, buildParamterFactory, converter);
        }
    }
}
