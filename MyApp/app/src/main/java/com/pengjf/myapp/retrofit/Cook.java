package com.pengjf.myapp.retrofit;

import java.io.Serializable;

/**
 * Created by jiangfeng  on 2017/3/29 0029 18:56
 * 邮箱：pengjf@hadlinks.com
 */

public class Cook implements Serializable{
    private int id;
    private String name;//名称
    private String  food;//食物
    private String img;//图片
    private String images;//图片,
    private String description;//描述
    private String keywords;//关键字
    private String message;//资讯内容
    private int count ;//访问次数
    private int fcount;//收藏数
    private int rcount;//评论读数
}
