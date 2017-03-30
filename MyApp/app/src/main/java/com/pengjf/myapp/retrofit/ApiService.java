package com.pengjf.myapp.retrofit;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiangfeng  on 2017/3/29 0029 18:10
 * 邮箱：pengjf@hadlinks.com
 */

public interface ApiService {
    String BASE_URL = "http://www.tngou.net/";
    @GET("ezSQL/get_user.php")
    Observable<BaseResponse<List<UserModel>>> getUsersByRx();

    @GET("api/cook/list")
    Observable<TngouResponse<List<Cook>>> getCookList(@Query("page") int page, @Query("rows") int rows);


}
