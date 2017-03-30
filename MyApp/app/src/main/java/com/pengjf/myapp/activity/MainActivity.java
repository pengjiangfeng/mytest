package com.pengjf.myapp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.pengjf.myapp.R;
import com.pengjf.myapp.retrofit.BaseResponse;
import com.pengjf.myapp.retrofit.ProgressSubscriber;
import com.pengjf.myapp.retrofit.RetrofitUtil;
import com.pengjf.myapp.retrofit.SubscriberOnNextListener;
import com.pengjf.myapp.retrofit.UserModel;
import com.pengjf.myapp.utils.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView lineChart = (TextView) findViewById(R.id.line_chart);
        lineChart.setOnClickListener(this);
        TextView barCart = (TextView) findViewById(R.id.bar_chart);
        barCart.setOnClickListener(this);
        TextView retrofit = (TextView) findViewById(R.id.retrofit);
        retrofit.setOnClickListener(this);
        rxPermissions = new RxPermissions(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp();
        }

        return true;
    }

    public void doGet(){
//        SubscriberOnNextListener mListener = new SubscriberOnNextListener<TngouResponse<List<Cook>>>() {
//            @Override
//            public void onNext(TngouResponse<List<Cook>> listTngouResponse) {
//                Log.i(TAG,"size:"+ listTngouResponse.tngou.size());
//                ToastUtil.ShortToast("size:"+ listTngouResponse.tngou.size());
//            }
//
//        };
//        RetrofitUtil.getInstance().getCookList(2,5,new ProgressSubscriber<TngouResponse<List<Cook>>>(mListener,this));
        SubscriberOnNextListener listener = new SubscriberOnNextListener<BaseResponse<List<UserModel>>>() {
            @Override
            public void onNext(BaseResponse<List<UserModel>> listBaseResponse) {
                ToastUtil.ShortToast("size:"+ listBaseResponse.data.size());
            }
        };
        RetrofitUtil.getInstance().getUsers(new ProgressSubscriber<BaseResponse<List<UserModel>>>(listener,this));
    }

    /**
     * 双击退出App
     */
    private long exitTime;

    private void exitApp() {

        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.ShortToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent ;
        switch (view.getId()) {
            case R.id.line_chart:
                intent = new Intent(this,LineChartActivity.class);
                startActivity(intent);
                break;
            case R.id.bar_chart:
                intent = new Intent(this,BarChartActivity.class);
                startActivity(intent);
                break;
            case R.id.retrofit:
                doGet();
                rxPermissions
                        .request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                Log.i(TAG,"get:"+ granted);

                            } else {
                                Log.i(TAG,"get:"+ granted);
                            }
                        });

                break;
        }
    }
}
