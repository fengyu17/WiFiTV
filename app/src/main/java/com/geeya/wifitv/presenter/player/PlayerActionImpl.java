/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-22 上午11:31:14
 * 
 */
package com.geeya.wifitv.presenter.player;

import java.util.ArrayList;

import com.geeya.wifitv.AppConfig;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.api.advertisement.AdApi;
import com.geeya.wifitv.api.advertisement.AdApiImpl;
import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.bean.AreaInfo;
import com.geeya.wifitv.ui.interf.ILivePlay;
import com.geeya.wifitv.ui.interf.IProgramPlay;

/**
 * @author Administrator
 * 
 */
public class PlayerActionImpl implements PlayerAction {

	private IProgramPlay iProgramPlay;
	private ILivePlay iLivePlay;
	private AdApi adApi;

	public PlayerActionImpl(IProgramPlay iProgramPlay) {
		this.iProgramPlay = iProgramPlay;
		this.adApi = new AdApiImpl();
	}

	public PlayerActionImpl(ILivePlay iLivePlay) {
		this.iLivePlay = iLivePlay;
		this.adApi = new AdApiImpl();
	}

	@Override
	public void liveADPlayerAction() {
		// TODO Auto-generated method stub
		AreaInfo areaInfo = WiFiTVApplication.getInstance().getAreaInfo();
		String areaID = areaInfo.getAreaID();

		// 请求开播广告
		adApi.getAD(new PlayerActionCallbackListener<ArrayList<ADInfo>>() {

			@Override
			public void onSuccess(ArrayList<ADInfo> adInfoes) {
				// TODO Auto-generated method stub
				iLivePlay.initStartAD(adInfoes);
			}

			@Override
			public void onFailure(int message) {
				// TODO Auto-generated method stub
			}

		}, AppConfig.ADTYPE_STARTUP, null, areaID, null);

		// 请求挂角广告
		adApi.getAD(new PlayerActionCallbackListener<ArrayList<ADInfo>>() {

			@Override
			public void onSuccess(ArrayList<ADInfo> adInfoes) {
				// TODO Auto-generated method stub
				iLivePlay.initCornerAD(adInfoes);
			}

			@Override
			public void onFailure(int message) {
				// TODO Auto-generated method stub
			}
		}, AppConfig.ADTYPE_CORNER, null, areaID, null);

		// 请求字幕广告
		adApi.getAD(new PlayerActionCallbackListener<ArrayList<ADInfo>>() {

			@Override
			public void onSuccess(ArrayList<ADInfo> adInfoes) {
				// TODO Auto-generated method stub
				iLivePlay.initSubtitleAD(adInfoes);
			}

			@Override
			public void onFailure(int message) {
				// TODO Auto-generated method stub
			}
		}, AppConfig.ADTYPE_SUBTITLE, null, areaID, null);
	}

	@Override
	public void programADPlayerAction() {
		// TODO Auto-generated method stub
		AreaInfo areaInfo = WiFiTVApplication.getInstance().getAreaInfo();
		String areaID = areaInfo.getAreaID();

		// 请求开播广告
		adApi.getAD(new PlayerActionCallbackListener<ArrayList<ADInfo>>() {

			@Override
			public void onSuccess(ArrayList<ADInfo> adInfoes) {
				// TODO Auto-generated method stub
				iProgramPlay.initStartAD(adInfoes);
			}

			@Override
			public void onFailure(int message) {
				// TODO Auto-generated method stub
			}

		}, AppConfig.ADTYPE_STARTUP, null, areaID, null);

		// 请求挂角广告
		adApi.getAD(new PlayerActionCallbackListener<ArrayList<ADInfo>>() {

			@Override
			public void onSuccess(ArrayList<ADInfo> adInfoes) {
				// TODO Auto-generated method stub
				iProgramPlay.initCornerAD(adInfoes);
			}

			@Override
			public void onFailure(int message) {
				// TODO Auto-generated method stub
			}
		}, AppConfig.ADTYPE_CORNER, null, areaID, null);

		// 请求暂停广告
		adApi.getAD(new PlayerActionCallbackListener<ArrayList<ADInfo>>() {

			@Override
			public void onSuccess(ArrayList<ADInfo> adInfoes) {
				// TODO Auto-generated method stub
				iProgramPlay.initPauseAD(adInfoes);
			}

			@Override
			public void onFailure(int message) {
				// TODO Auto-generated method stub
			}
		}, AppConfig.ADTYPE_PAUSE, null, areaID, null);

		// 请求字幕广告
		adApi.getAD(new PlayerActionCallbackListener<ArrayList<ADInfo>>() {

			@Override
			public void onSuccess(ArrayList<ADInfo> adInfoes) {
				// TODO Auto-generated method stub
				iProgramPlay.initSubtitleAD(adInfoes);
			}

			@Override
			public void onFailure(int message) {
				// TODO Auto-generated method stub
			}
		}, AppConfig.ADTYPE_SUBTITLE, null, areaID, null);

	}

	@Override
	public void livePlayerAction() {
		// TODO Auto-generated method stub
		iLivePlay.initLivePlay();
		liveADPlayerAction();
	}

	@Override
	public void programPlayerAction() {
		// TODO Auto-generated method stub
		iProgramPlay.initProgramPlay();
		programADPlayerAction();
	}

}
