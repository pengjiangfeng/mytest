package com.pengjf.myapp.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.pengjf.myapp.R;
import com.pengjf.myapp.ble.BluetoothUtil;
import com.pengjf.myapp.utils.LogUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class BleActivity extends AppCompatActivity {
    private Context mContext ;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.connect)
    TextView connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        ButterKnife.bind(this);
        mContext = this ;
    }

    @OnClick({R.id.search, R.id.connect})
    public void onViewClicked(View view) {
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
                break;
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
