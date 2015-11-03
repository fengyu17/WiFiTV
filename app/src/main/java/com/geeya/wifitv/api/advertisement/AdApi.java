/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-16 下午7:55:53
 * 
 */
package com.geeya.wifitv.api.advertisement;

import java.util.ArrayList;

import org.json.JSONObject;

import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.presenter.player.PlayerActionCallbackListener;


/**
 * @author Administrator
 *
 */
public interface AdApi {
	
	public void getAD(PlayerActionCallbackListener<ArrayList<ADInfo>> listener, int adType, String videoID, String areaID, String userID);
	
	public ArrayList<ADInfo> parseADInfoJson(JSONObject jsonObject);

}
