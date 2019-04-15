package com.yexin.httplib.response;

import com.google.gson.Gson;
import com.yexin.httplib.Converter;

import okhttp3.ResponseBody;

/**
 * author: zengven
 * date: 2019/4/4 10:41
 * desc: response converter to java bean by gson
 */
public class DefaultResponseConverter implements Converter<ResponseBody, Object> {
    private static Gson sGson = new Gson();

    @Override
    public Object convert(ResponseBody value, Class<?> clazz) throws Exception {
        return sGson.fromJson(value.charStream(), clazz);
    }
}
