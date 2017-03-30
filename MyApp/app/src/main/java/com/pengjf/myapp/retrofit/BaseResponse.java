package com.pengjf.myapp.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jiangfeng  on 2017/3/29 0029 18:13
 * 邮箱：pengjf@hadlinks.com
 */

public class BaseResponse<T> implements Serializable {
    @SerializedName("code")
    public int code;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    public T data;
}
