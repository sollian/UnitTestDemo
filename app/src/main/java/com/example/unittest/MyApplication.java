package com.example.unittest;

import android.app.Application;

/**
 * Application类
 *
 * @author shouxianli on 2020/7/17.
 */
public class MyApplication extends Application {

    private static Application mApplication;

    public static Application getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
