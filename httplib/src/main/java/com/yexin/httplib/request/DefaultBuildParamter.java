package com.yexin.httplib.request;


import com.yexin.httplib.annotation.Field;
import com.yexin.httplib.utils.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * author: zengven
 * date: 2019/4/3 17:43
 * desc: 默认参数构造器
 */
public class DefaultBuildParamter implements IBuildParamter {

    @Override
    public Object build(String httpMethod, LinkedList<Pair<Type, Annotation>> paramterPairs, Object[] args) {
        if (httpMethod.equals("GET")) {
            return buildGetParamters(paramterPairs, args);
        } else if (httpMethod.equals("POST")) {
            return buildPostParamters(paramterPairs, args);
        } else {
            throw new IllegalArgumentException("not support http request method");
        }
    }

    private JSONObject buildPostParamters(LinkedList<Pair<Type, Annotation>> paramterPairs, Object[] args) {
        JSONObject jsonObject = new JSONObject();
        int size = paramterPairs.size();
        for (int i = 0; i < size; i++) {
            Pair<Type, Annotation> pair = paramterPairs.get(i);
            Type type = pair.first;
            Annotation annotation = pair.second;
            Object arg = args[i];
            if (annotation instanceof Field) {
                Field field = (Field) annotation;
                try {
                    jsonObject.put(field.value(), arg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                throw new IllegalArgumentException("not support annotation");
            }
        }
        return jsonObject;
    }

    private Object buildGetParamters(LinkedList<Pair<Type, Annotation>> paramterPairs, Object[] args) {
        StringBuilder paramters = new StringBuilder();
        int size = paramterPairs.size();
        for (int i = 0; i < size; i++) {
            Pair<Type, Annotation> pair = paramterPairs.get(i);
            Type type = pair.first;
            Annotation annotation = pair.second;
            Object arg = args[i];
            if (annotation instanceof Field) {
                Field field = (Field) annotation;
                paramters.append(field.value()).append("=").append(arg);
                if (i < size - 1) {
                    paramters.append("&");
                }
            } else {
                throw new IllegalArgumentException("not support annotation");
            }
        }
        return paramters.toString();
    }
}
