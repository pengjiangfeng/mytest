package com.pengjf.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pengjf.myapp.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengjf on 2017/4/21.
 * 1042293434@qq.com
 */

public class TestView extends View {
    private int width;
    private Paint mPaint;
    private List<ChartEntity> mList = new ArrayList<>();

    public TestView(Context context) {
        super(context);
        init(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        if (withMode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = DisplayUtil.dp2px(getContext(), 20) * 24;
        }

        setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mList.size() == 0) {
            return;
        }
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        Path path = new Path();
        float scale = getMeasuredHeight() / getMax(mList); //ChartEntity y值占多少像素
        for (int i = 0; i < mList.size(); i++) {
            if (i == 0) {
                path.moveTo(0,
                        mList.get(0).getyValue() * scale);
            } else {
                path.lineTo(width / 24 * i,
                        mList.get(i).getyValue() * scale);
            }
        }
        canvas.drawPath(path, mPaint);
    }

    public void setList(List<ChartEntity> charts) {
        mList = charts;
        invalidate();
    }

    private float getMax(List<ChartEntity> charts) {
        float max = 0;
        for (int i = 0; i < charts.size(); i++) {
            float value = charts.get(i).getyValue();
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private List<Point> mPoints = new ArrayList<>();

    private void getPoint() {

    }
}
