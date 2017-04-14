package com.pengjf.myapp.retrofit.downLoad;

/**
 * Created by pengjf on 2017/4/13.
 * 1042293434@qq.com
 */

public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
