package com.pengjf.myapp.retrofit.downLoad;

import android.app.Application;

import rx.Subscriber;

/**
 * Created by pengjf on 2017/4/13.
 * 1042293434@qq.com
 */

public class FileSubscriber<T> extends Subscriber<T> {
    private Application application;
    private FileCallBack fileCallBack;

    public FileSubscriber(Application application, FileCallBack fileCallBack) {
        this.application = application;
        this.fileCallBack = fileCallBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (fileCallBack != null)
            fileCallBack.onStart();
    }

    @Override
    public void onCompleted() {
        if (fileCallBack != null)
            fileCallBack.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        if (fileCallBack != null)
            fileCallBack.onError(e);
    }

    @Override
    public void onNext(T t) {
        if (fileCallBack != null)
            fileCallBack.onSuccess(t);
    }

}
