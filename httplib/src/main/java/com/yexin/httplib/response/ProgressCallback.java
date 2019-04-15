package com.yexin.httplib.response;

import java.io.File;

/**
 * author: zengven
 * date: 2019/4/9 17:26
 * desc: 进度回调
 */
public interface ProgressCallback {
    void onStart();

    void onFailure(Exception e);

    void onSuccess(File file);

    /**
     * run in child thread
     *
     * @param progress
     */
    void onProgress(int progress);
}
