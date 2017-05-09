package com.pengjf.myapp.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.ParcelUuid;
import android.os.RemoteException;
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
import com.pengjf.myapp.view.ChartEntity;
import com.pengjf.myapp.view.TestView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        init();
    }

    private void init() {
        List<ChartEntity> charts = new ArrayList<>();
        Random random = new Random();
        for (int i = 0 ; i< 24 ;i++){
            ChartEntity chart = new ChartEntity(i+"test", (float) random.nextInt(300));
            charts.add(chart);
        }
        TestView testView = (TestView) findViewById(R.id.test_view);
        testView.setList(charts);
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
                bindService();
//                download();
                break;
        }
    }
    private boolean isBound ;
    private void bindService() {

       if (!isBound){
           isBound = bindService(new Intent(this,TestIntentService.class),serviceConnection,BIND_AUTO_CREATE);
       }else {
           sendMessage();
       }
    }
    private Messenger mLocalMessenger ;
    private Messenger mServiceMessenger ;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLocalMessenger = new Messenger(mHandler);
            mServiceMessenger = new Messenger(service);
            sendMessage();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceMessenger = null ;
        }
    };

    private void sendMessage() {
        Message message = Message.obtain(null,TestIntentService.CUSGET);
        message.replyTo = mLocalMessenger ;
        try {
            mServiceMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x02:
                    try {
                        mServiceMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null && isBound){
            unbindService(serviceConnection);
        }
    }
}
