package com.pengjf.myapp.retrofit.downLoad;

import com.pengjf.myapp.MyApp;
import com.pengjf.myapp.retrofit.ApiService;
import com.pengjf.myapp.retrofit.RetrofitUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.pengjf.myapp.utils.ConstantUtil.BASE_URL;

/**
 * Created by pengjf on 2017/4/13.
 * 1042293434@qq.com
 */

public class DownLoadUtil {
    private static final int DEFAULT_TIMEOUT = 15;
    private Retrofit mRetrofit;
    private ApiService mApiService ;
    private static DownLoadUtil mInstance ;

    private DownLoadUtil (){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //日志拦截器
        builder.addInterceptor(new ProgressInterceptor());
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    public static DownLoadUtil getInstance(){
        if (mInstance == null){
            synchronized (RetrofitUtil.class){
                mInstance = new DownLoadUtil();
            }
        }
        return mInstance;
    }

    public void load(String url, final FileCallBack<ResponseBody> callBack){
        mApiService.download(url)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        callBack.saveFile(body);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new FileSubscriber<ResponseBody>(MyApp.getInstance(),callBack));
    }
}
