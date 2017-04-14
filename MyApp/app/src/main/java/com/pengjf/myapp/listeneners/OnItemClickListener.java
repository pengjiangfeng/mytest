package com.pengjf.myapp.listeneners;

import android.view.View;

/**
 * Created by pengjf on 2017/4/8.
 * 1042293434@qq.com
 */

public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}
