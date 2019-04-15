package com.yexin.httplib;

import okhttp3.ResponseBody;

/**
 * author: zengven
 * date: 2019/4/2 15:07
 * desc: Convert objects to and from their representation in HTTP.
 */
public interface Converter<F, T> {

    T convert(ResponseBody value, Class<?> clazz) throws Exception;
}
