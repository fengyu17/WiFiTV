package com.geeya.wifitv.ui.interf;

import java.util.List;

import android.net.wifi.ScanResult;

public interface IHomeFrag {
	
	public void updateAdapter(List<ScanResult> list);
	
	public void changeToggleEnable(int value);
	
	public void setWifiListVisibility(int value);
	
	public void toggleLocationClose();
	
	public void toggleLocationOpen();
	
	public void setToggleChecked(boolean value);
	
	public void playOpenAnimation();
	
	public void playCloseAnimation();
	
	public ScanResult getListItem(int arg2);
	
	public void startScan();
	
	public void endScan();
	
	public void showConnectivityState(String message);
	
	public void shrinkTitleBar();
	
	public void expandTitleBar();
	
	public void setWifiBarHeightOpen();
	
	public void setWifiBarHeightClose();
	
	public void showDialog(final String SSID, final int cipherType);
		
}
