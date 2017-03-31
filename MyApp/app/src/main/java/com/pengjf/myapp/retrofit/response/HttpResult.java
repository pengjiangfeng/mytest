package com.pengjf.myapp.retrofit.response;

/**
 * Created by jiangfeng  on 2017/3/31 0031 15:32
 * 邮箱：pengjf@hadlinks.com
 */

public class HttpResult<T> {
    public int count;
    public int start;
    public int total;
    public String title;

    public T subjects;
}
