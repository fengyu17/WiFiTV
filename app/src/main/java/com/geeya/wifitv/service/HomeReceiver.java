package com.geeya.wifitv.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.view.View;

import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.api.AppApi;
import com.geeya.wifitv.api.AppApiImpl;
import com.geeya.wifitv.api.user.UserApi;
import com.geeya.wifitv.api.user.UserApiImpl;
import com.geeya.wifitv.ui.interf.IHomeFrag;

public class HomeReceiver extends BroadcastReceiver {

    private IHomeFrag iHomeFrag;
    private AppApi appApi;
    private UserApi userApi;
    private WifiManager wifiManager;

    HomeReceiver() {

    }

    public HomeReceiver(IHomeFrag iHomeFrag, Context context) {
        this.iHomeFrag = iHomeFrag;
        this.appApi = new AppApiImpl(context);
        this.userApi = new UserApiImpl();
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            List<ScanResult> list = wifiManager.getScanResults();
            Collections.sort(list, new Comparator<ScanResult>() {

                public int compare(ScanResult lhs, ScanResult rhs) {
                    return rhs.level - lhs.level;
                }
            });
            int count = 0;
            for (int i = 0; i < list.size(); i++) {

                String ssid = list.get(i).SSID;

                int location = ssid.indexOf("g");
                if (location == 0) {
                    ScanResult temp = list.get(count);
                    list.set(count, list.get(i));
                    list.set(i, temp);
                    count++;
                }
            }
            iHomeFrag.setWifiListVisibility(View.VISIBLE);
            iHomeFrag.setWifiBarHeightOpen();
            iHomeFrag.updateAdapter(list);
            iHomeFrag.endScan();
        }
        if (intent.getAction().equals("android.intent.action.startScan")) {
            iHomeFrag.startScan();
        }

        if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int wifiState = wifiManager.getWifiState();
            iHomeFrag.changeToggleEnable(wifiState);
            if (wifiState == WifiManager.WIFI_STATE_DISABLING) {
                iHomeFrag.setWifiListVisibility(View.GONE);
                iHomeFrag.setWifiBarHeightClose();
                iHomeFrag.endScan();
                iHomeFrag.showConnectivityState("正在关闭WIFI");
            }
            if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
                iHomeFrag.showConnectivityState("WIFI已开启");
                iHomeFrag.startScan();
                wifiManager.startScan();
            }
            if (wifiState == WifiManager.WIFI_STATE_ENABLING) {
                iHomeFrag.showConnectivityState("正在开启WIFI");
            }
            if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
                iHomeFrag.showConnectivityState("WIFI已关闭");
            }
        }

        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            Parcelable parcelable = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            NetworkInfo networkInfo = null;
            if (parcelable != null) {
                networkInfo = (NetworkInfo) parcelable;
                DetailedState detailedState = networkInfo.getDetailedState();
                SupplicantState supplicantState = wifiManager.getConnectionInfo().getSupplicantState();
                if (supplicantState.equals(SupplicantState.ASSOCIATING))
                    iHomeFrag.showConnectivityState("正在验证身份．．．");
                if (detailedState.equals(DetailedState.OBTAINING_IPADDR))
                    iHomeFrag.showConnectivityState("正在获取IP．．．");
                if (detailedState.equals(DetailedState.CONNECTED) && supplicantState.equals(SupplicantState.COMPLETED)) {
                    String ssId = wifiManager.getConnectionInfo().getSSID();
                    iHomeFrag.showConnectivityState("已连接至Wifi " + ssId);
                    appApi.getAreaInfo();
                    if (WiFiTVApplication.getInstance().getUserInfo() == null)
                        userApi.autoLogin();
                }
            }
            //NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //DetailedState connetivityState = networkInfo.getDetailedState();
            //System.out.println(wifiManager.getConnectionInfo().getSupplicantState());
            //System.out.println(ssId);
//			switch (networkInfo.getState()) {			
//			case CONNECTED:
//				System.out.println("已连接至WIFI");
//				break;
//			case CONNECTING:
//				System.out.println("正在连接至WIFI");
//				break;
//			case DISCONNECTED:
//				System.out.println("已经断开连接");
//				break;
//			default:
//				break;
//			}
            //System.out.println(networkInfo.getState());
//			if (connetivityState.equals(NetworkInfo.DetailedState.CONNECTING)) {
//				iHomeFrag.showConnectivityState("正在连接");
//			}
//			if (connetivityState.equals(NetworkInfo.DetailedState.CONNECTED)) {
//				iHomeFrag.showConnectivityState("已连接至WIFI " + ssId);
//				
//				// }
//			}
//			if (connetivityState.equals(NetworkInfo.DetailedState.OBTAINING_IPADDR)) {
//				iHomeFrag.showConnectivityState("正在获取IP");
//			}
//			if (connetivityState.equals(NetworkInfo.DetailedState.FAILED) || connetivityState.equals(NetworkInfo.DetailedState.DISCONNECTED)) {
//			}
        }
    }
}
