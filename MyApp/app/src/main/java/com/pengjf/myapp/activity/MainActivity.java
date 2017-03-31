package com.pengjf.myapp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.pengjf.myapp.R;
import com.pengjf.myapp.utils.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.line_chart)
    TextView lineChart;
    @BindView(R.id.number_progress_bar)
    NumberProgressBar numberProgressBar;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        TextView lineChart = (TextView) findViewById(R.id.line_chart);
//        lineChart.setOnClickListener(this);
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

    public void doGet() {
        Intent intent = new Intent(this, DouBanMovieActivity.class);
        startActivity(intent);
//        SubscriberOnNextListener mListener = new SubscriberOnNextListener<List<Cook>>() {
//            @Override
//            public void onNext(List<Cook> cooks) {
//                ToastUtil.ShortToast("size:" + cooks.size());
//            }
//        };
//        RetrofitUtil.getInstance().getCookList(1, 5, new ProgressSubscriber<List<Cook>>(mListener, this));
//        SubscriberOnNextListener listener = new SubscriberOnNextListener<BaseResponse<List<UserModel>>>() {
//            @Override
//            public void onNext(BaseResponse<List<UserModel>> listBaseResponse) {
//                ToastUtil.ShortToast("size:"+ listBaseResponse.data.size());
//            }
//        };
//        RetrofitUtil.getInstance().getUsers(new ProgressSubscriber<BaseResponse<List<UserModel>>>(listener,this));


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
        Intent intent;
        switch (view.getId()) {
            case R.id.line_chart:
                intent = new Intent(this, LineChartActivity.class);
                startActivity(intent);
                break;
            case R.id.bar_chart:
                intent = new Intent(this, BarChartActivity.class);
                startActivity(intent);
                break;
            case R.id.retrofit:

                rxPermissions
                        .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                Log.i(TAG, "get:" + granted);
                                doGet();
                            } else {
                                Log.i(TAG, "get:" + granted);
                            }
                        });

                break;
        }
    }

    @OnClick(R.id.line_chart)
    public void onViewClicked() {
        Intent intent = new Intent(this, LineChartActivity.class);
        startActivity(intent);
    }

}
