package com.pengjf.myapp.activity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by jiangfeng  on 2017/3/29 0029 17:16
 * 邮箱：pengjf@hadlinks.com
 */

public class Test {
    public void test(){
        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {

            }
        });
    }
}
