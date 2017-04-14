package com.pengjf.myapp.retrofit.downLoad;

/**
 * Created by pengjf on 2017/4/13.
 * 1042293434@qq.com
 */

public class FileLoadEvent {
    long total;
    long bytesLoaded;
    public long getBytesLoaded() {
        return bytesLoaded;
    }

    public long getTotal() {
        return total;
    }

    public FileLoadEvent(long total, long bytesLoaded ) {
        this.total = total;
        this.bytesLoaded = bytesLoaded;
    }
}
