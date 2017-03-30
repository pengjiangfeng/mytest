package com.pengjf.myapp.retrofit;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jiangfeng  on 2017/3/29 0029 18:16
 * 邮箱：pengjf@hadlinks.com
 */

public class RetrofitUtil {
    public static final int DEFAULT_TIMEOUT = 5;

    private Retrofit mRetrofit;
    private ApiService mApiService;

    private static RetrofitUtil mInstance;

    private RetrofitUtil(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    public static RetrofitUtil getInstance(){
        if (mInstance == null){
            synchronized (RetrofitUtil.class){
                mInstance = new RetrofitUtil();
            }
        }
        return mInstance;
    }

    /**
     * 用于获取用户信息
     * @param subscriber
     */
    public void getUsers(Subscriber<BaseResponse<List<UserModel>>> subscriber){
        mApiService.getUsersByRx()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getCookList(int page, int rows, Subscriber<TngouResponse<List<Cook>>> subscriber){

        toSubscribe(mApiService.getCookList(page,rows),subscriber);
    }

    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
