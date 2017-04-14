package com.pengjf.myapp.retrofit;

import com.pengjf.myapp.utils.ConstantUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by pengjf on 2017/4/12.
 * 1042293434@qq.com
 */

public class LoadImg {
    public static RequestBody getRequestBody(String name, File file){
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("accessKey", ConstantUtil.ACCESS_KEY)
                .addFormDataPart("imageName", name)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        return  requestBody ;
    }
}
