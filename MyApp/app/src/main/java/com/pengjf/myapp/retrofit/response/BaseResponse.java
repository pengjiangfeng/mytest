package com.pengjf.myapp.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiangfeng  on 2017/3/29 0029 18:13
 * 邮箱：pengjf@hadlinks.com
 */

public class BaseResponse<T> implements Parcelable {
    public int code;
    public String msg;
    public T data;

    protected BaseResponse(Parcel in) {
        code = in.readInt();
        msg = in.readString();
    }

    public static final Creator<BaseResponse> CREATOR = new Creator<BaseResponse>() {
        @Override
        public BaseResponse createFromParcel(Parcel in) {
            return new BaseResponse(in);
        }

        @Override
        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
    }
}
