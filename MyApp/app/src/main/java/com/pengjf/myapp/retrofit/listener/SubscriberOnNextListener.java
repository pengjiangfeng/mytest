package com.pengjf.myapp.retrofit.listener;

/**
 * Created by jiangfeng  on 2017/3/29 0029 18:26
 * 邮箱：pengjf@hadlinks.com
 */

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onFinish();
}
