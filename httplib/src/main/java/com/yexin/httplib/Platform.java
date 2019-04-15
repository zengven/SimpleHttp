package com.yexin.httplib;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * author: zengven
 * date: 2019/4/4 16:12
 * desc: TODO
 */
public class Platform {
    private static final Platform PLATFORM = findPlatform();

    static Platform get() {
        return PLATFORM;
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new Android();
            }
        } catch (ClassNotFoundException ignored) {
        }
        return new Platform();
    }

    Executor defaultCallbackExecutor() {
        return null;
    }

    static class Android extends Platform {
        @Override
        public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        static class MainThreadExecutor implements Executor {
            private final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable r) {
                handler.post(r);
            }
        }
    }
}
