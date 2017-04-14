package com.pengjf.myapp.activity;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by jiangfeng  on 2017/3/29 0029 17:16
 * 邮箱：pengjf@hadlinks.com
 */

public class TestIntentService extends IntentService{


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TestIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void test(){
        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {

            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(runnable);

        ExecutorService executorService1 = Executors.newFixedThreadPool(2);
        executorService1.execute(runnable);

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(runnable,2000,1,TimeUnit.HOURS);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
