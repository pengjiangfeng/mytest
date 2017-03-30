package com.pengjf.myapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by jiangfeng  on 2017/3/29 0029 10:35
 * 邮箱：pengjf@hadlinks.com
 */

public class MyApp extends Application{
    public static MyApp mInstance;
    @Override
    public void onCreate() {

        super.onCreate();

        mInstance = this;
        init();
    }

    private void init() {

    }

    public static MyApp getInstance() {

        return mInstance;
    }

    public static Context getAppContext(){
        return mInstance == null ? null : mInstance.getApplicationContext();
    }
}
