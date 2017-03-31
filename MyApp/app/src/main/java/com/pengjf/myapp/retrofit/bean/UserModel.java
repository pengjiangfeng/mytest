package com.pengjf.myapp.retrofit.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiangfeng  on 2017/3/29 0029 18:20
 * 邮箱：pengjf@hadlinks.com
 */

public class UserModel implements Parcelable{
    public String username;
    public String password;

    protected UserModel(Parcel in) {
        username = in.readString();
        password = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
    }
}
