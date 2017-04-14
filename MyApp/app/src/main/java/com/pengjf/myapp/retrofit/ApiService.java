package com.pengjf.myapp.retrofit;

import com.pengjf.myapp.bean.NewsData;
import com.pengjf.myapp.recyclerView.DataBean;
import com.pengjf.myapp.recyclerView.HeaderBean;
import com.pengjf.myapp.retrofit.bean.Cook;
import com.pengjf.myapp.retrofit.bean.Movie;
import com.pengjf.myapp.retrofit.bean.UserModel;
import com.pengjf.myapp.retrofit.response.BaseResponse;
import com.pengjf.myapp.retrofit.response.HttpFhsResult;
import com.pengjf.myapp.retrofit.response.HttpResult;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by jiangfeng  on 2017/3/29 0029 18:10
 * 邮箱：pengjf@hadlinks.com
 */

public interface ApiService {
    @GET("ezSQL/get_user.php")
    Observable<BaseResponse<List<UserModel>>> getUsersByRx();

    @GET("api/cook/list")
    Observable<BaseResponse<List<Cook>>> getCookList(@Query("page") int page, @Query("rows") int rows);

    //忘记-获取验证码
    @FormUrlEncoded
    @POST("api/account/password/get/smscode")
    Observable<BaseResponse<UserModel>> getForgetSmsCode(@Field("accessKey") String key,
                                                         @Field("username") String name
    );

    @GET("top250")
    Observable<HttpResult<List<Movie>>> getMovies(@Query("start") int start, @Query("count") int count);
//    /*上传文件*/
//    @Multipart
//    @POST("AppYuFaKu/uploadHeadImg")
//    Observable<BaseResponse<UploadResulte>> uploadImage(@Part("uid") RequestBody uid, @Part("auth_key") RequestBody auth_key, @Part MultipartBody.Part file);

    @GET("banner/query")
    Observable<List<HeaderBean>> getHeader(@Query("type")int type);
    @GET("campaign/recommend")
    Observable<List<DataBean>>getData();
    //https://fhs-test.yunext.com
    @FormUrlEncoded
    @POST("api/news/query")
    Observable<HttpFhsResult<List<NewsData>>>getNews
    (@Field("accessKey") String accessKey,@Field("token") String token ,@Field("page") int page);

    @GET("api/news/getCover")
    Observable<HttpFhsResult<Map<String ,String>>> getCorver
            (@Query("accessKey") String accessKey,@Query("token") String token);

    @POST("web/image/upload")
    Observable<HttpFhsResult<String>>uploadImg
            (@Body RequestBody body);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
