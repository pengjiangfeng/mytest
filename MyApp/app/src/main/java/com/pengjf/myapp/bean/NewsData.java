package com.pengjf.myapp.bean;

/**
 * Created by jiangfeng  on 2016/10/24 0024 15:24
 * 邮箱：pengjf@hadlinks.com
 */

public class NewsData {

    /**
     * id : 583cd390a9b921abae5c5c84
     * title : fffff
     * publishTime : 2016-11-30
     * coverId : a2a98ab4-4a4a-4b4a-b077-070b8a4e6dc6.jpg
     */

    private String id;
    private String title;
    private String publishTime;
    private String coverId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }
}
