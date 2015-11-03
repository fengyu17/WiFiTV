package com.geeya.wifitv.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiManager;

public class NetworkCheck {

	private Context context;
	private WifiManager wifiManager;

	public NetworkCheck(Context context) {
		this.context = context;
		this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	}

	/**
	 * 检测网络状态，是wifi连接正常返回true，否则返回false
	 * 
	 * @return
	 *
	 * Created by Administrator
	 * Created on 2015-9-23 上午8:56:10
	 */
	public boolean checkNetWorkState() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		DetailedState detailedState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getDetailedState();
		if (detailedState.equals(DetailedState.CONNECTED) && wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkWifiRight() {
		if(checkNetWorkState()){			
			String ssid = wifiManager.getConnectionInfo().getSSID();
			if(ssid != null && ssid.length() > 3){
				// 正式使用请反注释此句
//				if(ssid.substring(0, 4).equals("ai-"))
					return true;				
			}
		}
		return false;
	}

}
