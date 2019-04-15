package com.yexin.httplib;

import android.util.ArrayMap;

import com.yexin.httplib.annotation.Headers;
import com.yexin.httplib.request.IBuildParamter;
import com.yexin.httplib.annotation.method.GET;
import com.yexin.httplib.annotation.method.POST;
import com.yexin.httplib.utils.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.HttpUrl;

/**
 * author: zengven
 * date: 2019/4/2 18:23
 * desc: TODO
 */
public class ServiceMethod {
    // Upper and lower characters, digits, underscores, and hyphens, starting with a character.
    static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
    static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{(" + PARAM + ")\\}");
    static final Pattern PARAM_NAME_REGEX = Pattern.compile(PARAM);

    final HttpUrl baseUrl;
    final IBuildParamter buildParamterFactory;
    final String httpMethod;
    final String relativeUrl;
    final boolean hasBody;
    final LinkedList<Pair<Type, Annotation>> paramterPairs;
    final ArrayMap<String, String> headers;

    public ServiceMethod(Builder builder) {
        this.baseUrl = builder.simpleHttp.baseUrl;
        this.buildParamterFactory = builder.simpleHttp.buildParamterFactory;
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.hasBody = builder.hasBody;
        this.paramterPairs = builder.paramterPairs;
        this.headers = builder.headers;
    }

    public Object buildParamters(Object[] args) {
        return buildParamterFactory.build(httpMethod, paramterPairs, args);
    }

    public static final class Builder {
        final SimpleHttp simpleHttp;
        final Method method;
        final Annotation[] methodAnnotations;
        final Type[] genericParameterTypes;
        final Annotation[][] parameterAnnotations;

        String httpMethod;
        boolean hasBody;
        String relativeUrl;
        Set<String> relativeUrlParamNames;
        LinkedList<Pair<Type, Annotation>> paramterPairs = new LinkedList<>();
        ArrayMap<String, String> headers = new ArrayMap<>();

        Builder(SimpleHttp simpleHttp, Method method) {
            this.simpleHttp = simpleHttp;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.genericParameterTypes = method.getGenericParameterTypes();
            this.parameterAnnotations = method.getParameterAnnotations();
        }

        public ServiceMethod build() {
            //解析http请求方法注解
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }

            if (httpMethod == null) {
                throw methodError("HTTP method annotation is required (e.g., @GET, @POST, etc.).");
            }

            int length = parameterAnnotations.length;
            for (int i = 0; i < length; i++) {
                Annotation[] annotations = parameterAnnotations[i];
                System.out.println("type :" + genericParameterTypes[i]);
                if (annotations.length > 1) {
                    throw new IllegalArgumentException("");
                }
                if (annotations.length <= 0) {
                    continue;
                }
                parseParamterAnnotation(annotations[0], genericParameterTypes[i]);
            }
            return new ServiceMethod(this);
        }

        private void parseParamterAnnotation(Annotation annotation, Type genericParameterType) {
            Pair<Type, Annotation> pair = new Pair<>(genericParameterType, annotation);
            paramterPairs.add(pair);
        }

        private void parseMethodAnnotation(Annotation annotation) {
            System.out.println("annotation : " + annotation);
            if (annotation instanceof GET) { //get request method
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            } else if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
            } else if (annotation instanceof Headers) {
                parseHttpHeaders(((Headers) annotation).value());
            } else {

            }
        }

        private void parseHttpHeaders(String[] value) {
            if (value != null && value.length > 0) {
                for (String header : value) {
                    String[] split = header.split(":");
                    if (split.length != 2) {
                        throw new IllegalArgumentException("header must be error");
                    }
                    headers.put(split[0], split[1]);
                }
            }
        }

        private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
            this.httpMethod = httpMethod;
            this.hasBody = hasBody;
            if (value.isEmpty()) {
                return;
            }

            this.relativeUrl = value;
            this.relativeUrlParamNames = parsePathParameters(value);
        }

        /**
         * Gets the set of unique path parameters used in the given URI. If a parameter is used twice
         * in the URI, it will only show up once in the set.
         */
        static Set<String> parsePathParameters(String path) {
            Matcher m = PARAM_URL_REGEX.matcher(path);
            Set<String> patterns = new LinkedHashSet<>();
            while (m.find()) {
                patterns.add(m.group(1));
            }
            return patterns;
        }

        private RuntimeException methodError(String message, Object... args) {
            return methodError(null, message, args);
        }

        private RuntimeException methodError(Throwable cause, String message, Object... args) {
            message = String.format(message, args);
            return new IllegalArgumentException(message
                    + "\n    for method "
                    + method.getDeclaringClass().getSimpleName()
                    + "."
                    + method.getName(), cause);
        }

    }
}
