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
import com.pengjf.myapp.utils.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.line_chart)
    TextView lineChart;
    @BindView(R.id.bar_chart)
    TextView barChart;
    @BindView(R.id.retrofit)
    TextView retrofit;
    @BindView(R.id.test_recycler_view)
    TextView testRecyclerView;
    @BindView(R.id.ble)
    TextView ble;

    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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


    @OnClick({R.id.line_chart, R.id.bar_chart, R.id.retrofit, R.id.test_recycler_view, R.id.ble})
    public void onViewClicked(View view) {
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
            case R.id.test_recycler_view:
                intent = new Intent(this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.ble:
                intent = new Intent(this, BleActivity.class);
                startActivity(intent);
                break;
        }
    }
}
