package com.pengjf.myapp.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by jiangfeng  on 2017/4/6 0006 14:22
 * 邮箱：pengjf@hadlinks.com
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BleScanResult extends ScanCallback {
    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);
        BluetoothDevice device = result.getDevice();
        ScanRecord record =  result.getScanRecord();
    }
}
