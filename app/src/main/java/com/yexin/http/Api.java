package com.yexin.http;

import com.yexin.httplib.annotation.Field;
import com.yexin.httplib.annotation.Headers;
import com.yexin.httplib.annotation.method.GET;
import com.yexin.httplib.annotation.method.POST;
import com.yexin.httplib.response.ResponseCallback;

/**
 * author: zengven
 * date: 2019/4/2 17:59
 * desc: TODO
 */
public interface Api {

    @GET("/v5/weather")
    void getWeatherService(@Field("city") String city, @Field("key") String key, ResponseCallback<?> callback);

    @Headers({"Token:c036d725635ea6d1a40b8bd6ee1db769"})
    @POST("https://dwz.cn/admin/v2/create")
    void getShortUrl(@Field("url") String url, ResponseCallback<?> callback);

}
