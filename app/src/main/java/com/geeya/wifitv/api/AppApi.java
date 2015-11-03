package com.geeya.wifitv.api;

import com.geeya.wifitv.bean.ApkInfo;
import com.geeya.wifitv.presenter.ActionCallbackListener;
import com.geeya.wifitv.presenter.AppActionImpl.CallBack;

public interface AppApi {
	
	public boolean isFirstStart();
	
	public void delayLaunch(CallBack callback);
	
	public void initConfig();
	
	public void getAreaInfo();
	
	public int chargeCipherType(String cipherType);
	
	public boolean isWifiOpen();
	
	public boolean isWifiConnected();
	
	public int isConfiguration(String SSID);
	
	public int addWifiConfiguration(String SSID, int cipherType);
	
	public int addWifiConfiguration(String SSID, String passWD, int cipherType);
	
	public void openWifi();
	
	public void closewifi();
	
	public void connectWifi(int netId);
	
	public void checkUpdate(ActionCallbackListener<ApkInfo> listener, String areaId, String verCode);
	
	public boolean isConnectedWifiRight();
	
}
