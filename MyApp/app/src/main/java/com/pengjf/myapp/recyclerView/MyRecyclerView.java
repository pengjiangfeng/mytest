package com.pengjf.myapp.recyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jiangfeng  on 2017/4/1 0001 16:53
 * 邮箱：pengjf@hadlinks.com
 */

public class MyRecyclerView extends RecyclerView{
    private int mLastX = 0 ;
    private int mLastY = 0 ;
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean intercept = false ;
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX ;
                int dy = y - mLastY ;
                if (Math.abs(dx) < Math.abs(dy) ){
                    intercept = true ;
                }else {
                    intercept = false ;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false ;
                break;
            default:
                intercept = false ;
                break;
        }
        mLastX = x ;
        mLastY = y ;
        return intercept;

    }
}
