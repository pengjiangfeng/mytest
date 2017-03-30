package com.pengjf.myapp.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jiangfeng  on 2017/3/29 0029 18:57
 * 邮箱：pengjf@hadlinks.com
 */

public class TngouResponse<T> implements Serializable {

    @SerializedName("status")
    public boolean status;

    @SerializedName("total")
    public int total;

    @SerializedName("tngou")
    public T tngou;

}
