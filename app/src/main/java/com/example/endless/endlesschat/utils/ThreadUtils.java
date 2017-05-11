package com.example.endless.endlesschat.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by endless .
 */
public class ThreadUtils {

    public static Executor sExecutor = Executors.newSingleThreadExecutor();
    public static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void runOnBackgroundThread(Runnable runnable){
        sExecutor.execute(runnable);
    }

    public static void RunMainThread(Runnable runnable){
        mHandler.post(runnable);
    }
}
