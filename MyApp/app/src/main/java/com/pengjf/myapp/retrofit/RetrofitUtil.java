package com.pengjf.myapp.retrofit;

import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;
import com.pengjf.myapp.recyclerView.DataBean;
import com.pengjf.myapp.recyclerView.HeaderBean;
import com.pengjf.myapp.retrofit.bean.Cook;
import com.pengjf.myapp.retrofit.bean.Movie;
import com.pengjf.myapp.retrofit.bean.UserModel;
import com.pengjf.myapp.retrofit.response.BaseResponse;
import com.pengjf.myapp.retrofit.response.HttpResult;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.pengjf.myapp.utils.ConstantUtil.BASE_URL;

/**
 * Created by jiangfeng  on 2017/3/29 0029 18:16
 * 邮箱：pengjf@hadlinks.com
 */

public class RetrofitUtil {

    public static final int DEFAULT_TIMEOUT = 15;
    private Retrofit mRetrofit;
    private ApiService mApiService;

    private static RetrofitUtil mInstance;

    private RetrofitUtil(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            //日志拦截器
        builder.addInterceptor(new LogInterceptor());
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
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


    public void getCookList(int page, int rows, Subscriber<List<Cook>> subscriber){
       Observable observable  =  mApiService.getCookList(page,rows)
                .map(new HttpResultFunc<List<Cook>>());
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
        toSubscribe(observable,subscriber);
    }

    private void toSubscribe(Observable o, Subscriber s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public void getMovie(int start ,int end,Subscriber<List<Movie>> subscriber){
        Observable observable = mApiService.getMovies(0,5).map(new HttpMovieResult<List<Movie>>());
        toSubscribe(observable ,subscriber);
    }

    public void getHeader (int type, Subscriber<List<HeaderBean>> subscriber){
        Observable observable = mApiService.getHeader(type);
        toSubscribe(observable ,subscriber);
    }

    public void getCenter ( Subscriber<List<DataBean>> subscriber){
        Observable observable = mApiService.getData();
        toSubscribe(observable ,subscriber);
    }

    private class HttpResultFunc<T> implements Func1<BaseResponse<T>, T> {


        @Override
        public T call(BaseResponse<T> tBaseResponse) {
            if (tBaseResponse.code != 100){
                throw new ApiException(tBaseResponse.code);
            }
            return tBaseResponse.data;
        }
    }

    private class HttpMovieResult <T> implements Func1<HttpResult<T>,T>{

        @Override
        public T call(HttpResult<T> result) {
            if (result.count <= 0)
                throw new ApiException("出错了");
            return result.subjects;
        }
    }
}
