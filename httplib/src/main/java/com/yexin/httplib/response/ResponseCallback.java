package com.yexin.httplib.response;

/**
 * author: zengven
 * date: 2019/4/4 11:34
 * desc: TODO
 */
public abstract class ResponseCallback<T> {
    public Class<T> clazz;

    public ResponseCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    public abstract void onStart();

    public abstract void onSuccess(T t);

    public abstract void onFailure(Exception e);
}
