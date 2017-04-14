package com.pengjf.myapp.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pengjf.myapp.R;
import com.pengjf.myapp.ble.BluetoothUtil;
import com.pengjf.myapp.retrofit.downLoad.DownLoadUtil;
import com.pengjf.myapp.retrofit.downLoad.FileCallBack;
import com.pengjf.myapp.utils.FileStorage;
import com.pengjf.myapp.utils.LogUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.functions.Action1;

public class TestBLEActivity extends AppCompatActivity {

    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.connect)
    TextView connect;
    private SeekBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dou_ban_movie);
        ButterKnife.bind(this);
        pb = (SeekBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.GONE);
    }

    @OnClick({R.id.search, R.id.connect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean b) {
                                if (b){
                                    scan();

                                }else {

                                }
                            }
                        });

                break;
            case R.id.connect:
//                Intent intent = new Intent(this,TestIntentService.class);
//                startService(intent);
                download();
                break;
        }
    }

    private void download() {
        String dir = new FileStorage().getIconDir().getAbsolutePath();
        String fileName = "456.jpeg";
        FileCallBack<ResponseBody> callBack = new FileCallBack<ResponseBody>(dir,fileName) {

            @Override
            public void onSuccess(ResponseBody body) {
                LogUtil.i("success");
            }

            @Override
            public void progress(long progress, long total) {
                LogUtil.i("progress"+ progress + "total"+total);
                update(progress,total);
            }

            @Override
            public void onStart() {
                pb.setVisibility(View.VISIBLE);
                pb.setMax(100);
                pb.setProgress(50);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
        };
        String url = "UploadedFile/123.jpeg";
        DownLoadUtil.getInstance().load(url,callBack);
    }

    private void update(long progress, long total) {
        if (progress > 0){
            pb.setProgress(100);
        }
    }

    private void scan() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            BluetoothLeScanner scanner = BluetoothUtil.getBluetoothAdapter(this).getBluetoothLeScanner();
            scanner.startScan(new ScanCallback() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    LogUtil.i("result:"+ result.getDevice().getAddress() +
                            "name:"+result.getScanRecord().getDeviceName()
                    );
                    if (result.getScanRecord().getServiceUuids() != null){
                        for (ParcelUuid uuid :result.getScanRecord().getServiceUuids()){
                            LogUtil.i(uuid.toString());
                        }
                    }

                }
            });
        }else {
            BluetoothUtil.getBluetoothAdapter(this).startLeScan(new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

                }
            });
        }
    }
}
