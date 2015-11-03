package com.geeya.wifitv.presenter;



public interface AppAction {
	
	public void initLoad();
	
	public void initWifiControl();
	
	public void openWifi();
	
	public void closeWifi();
	
	public void wifiConnection(int arg2);
	
	public void wifiConnection(String SSID, String str, int cipherType);
	
	public void wifiConnection();
	
	public void shrinkTitleBar();
	
	public void expandTitleBar();
	
	public void update();
	
	public long download(String url);
	
	public void setEnabled();
	
}
