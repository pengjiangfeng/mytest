/**
 * Copyright (c) www.bugull.com
 */
package com.pengjf.myapp.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * @author wuhao on 2016/7/30
 */
public class BluetoothUtil {
	private static final String TAG = BluetoothUtil.class.getSimpleName();

	/**
	 * 请求打开蓝牙
	 *
	 * @param context 上下文对象
	 * @return 如果请求成功则返回true，否则返回false
	 */
	public static boolean requestBluetooth(Context context) {
		if (context == null) {
			return false;
		}
		// request for enabling bluetooth
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		// 非Activity的context若要startActivity需要添加以下Flag
		enableBtIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(enableBtIntent);
		return true;
	}

	/**
	 * 开关蓝牙
	 *
	 * @param context 上下文对象
	 * @param enable  开启或关闭
	 * @return 如果成功执行则返回true，否则返回false
	 */
	public static boolean setBluetoothState(Context context, boolean enable) {
		if (context == null) {
			return false;
		}
		BluetoothAdapter bluetoothAdapter = getBluetoothAdapter(context);
		if (bluetoothAdapter == null) {
			return false;
		}
		return enable ? bluetoothAdapter.enable() : bluetoothAdapter.disable();
	}

	/**
	 * 文档见{@link BluetoothAdapter#checkBluetoothAddress(String)}
	 */
	public static boolean checkBluetoothAddress(String macAddress) {
		return BluetoothAdapter.checkBluetoothAddress(macAddress);
	}

	/**
	 * 获取BluetoothManager对象
	 *
	 * @param context 上下文对象
	 * @return BluetoothManager对象
	 */
	private static BluetoothManager getBluetoothManager(Context context) {
		if (context == null) {
			return null;
		}
		return (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
	}

	/**
	 * 获取BluetoothAdapter对象
	 *
	 * @param context 上下文对象
	 * @return BluetoothAdapter对象
	 */
	public static BluetoothAdapter getBluetoothAdapter(Context context) {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			return BluetoothAdapter.getDefaultAdapter();
		} else {
			if (context == null) {
				return null;
			}
			return getBluetoothManager(context).getAdapter();
		}
	}

	/**
	 * 获取目标设备的BluetoothDevice对象
	 *
	 * @param context    上下文对象
	 * @param macAddress 目标设备的mac地址
	 * @return 目标设备的BluetoothDevice对象
	 */
	public static BluetoothDevice getBluetoothDevice(Context context, String macAddress) {
		if (context == null) {
			return null;
		}
		BluetoothAdapter bluetoothAdapter = getBluetoothAdapter(context);
		if (bluetoothAdapter == null) {
			return null;
		}
		return bluetoothAdapter.getRemoteDevice(macAddress);
	}

	/**
	 * 获取Gatt连接的状态
	 *
	 * @param context    上下文对象
	 * @param macAddress 目标设备的mac地址
	 * @return 如果有和目标设备建立Gatt连接的话，则返回{@link BluetoothAdapter#STATE_CONNECTED}，
	 * 否则返回{@link BluetoothAdapter#STATE_DISCONNECTED}。当context为空时返回-1。
	 */
	public static int getBluetoothGattState(Context context, String macAddress) {
		if (context == null) {
			return -1;
		}
		return getBluetoothManager(context).getConnectionState(
				getBluetoothDevice(context, macAddress), BluetoothProfile.GATT);
	}
}
