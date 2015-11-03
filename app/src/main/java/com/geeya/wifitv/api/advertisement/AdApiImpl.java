/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-16 下午7:56:31
 * 
 */
package com.geeya.wifitv.api.advertisement;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.geeya.wifitv.AppConfig;
import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.presenter.player.PlayerActionCallbackListener;
import com.geeya.wifitv.utils.VolleyUtil;

/**
 * @author Administrator
 * 
 */
public class AdApiImpl implements AdApi {

	@Override
	public void getAD(final PlayerActionCallbackListener<ArrayList<ADInfo>> listener, int adType, String videoID, String areaID, String userID) {
		// TODO Auto-generated method stub
//		String url = AppConfig.CLOUD_IP + AppConfig.URL_AD + "?adType=" + adType;
//		if (videoID != null && videoID != "") {
//			url += "&videoID=" + videoID;
//		}
//		if (areaID != null && areaID != "") {
//			url += "&areaID=" + areaID;
//		} else {
//			url += "&areaID=0";
//		}
//		if (userID != null && userID != "") {
//			url += "&userID=" + userID;
//		}
		
		String url = AppConfig.CLOUD_IP;
		StringBuilder stringBuilder = new StringBuilder(url);
		stringBuilder.append(AppConfig.URL_AD).append("?adType=").append(adType);
		if (videoID != null && !videoID.equals("")) {
			stringBuilder.append("&videoID=").append(videoID);
		}
		if (areaID != null && !areaID.equals("")) {
			stringBuilder.append("&areaID=").append(areaID);
		} else {
			stringBuilder.append("&areaID=0");
		}
		if (userID != null && !userID.equals("")) {
			stringBuilder.append("&userID=").append(userID);
		}
		

		VolleyUtil.volleyGet(stringBuilder.toString(), new AdApiResponse<JSONObject>() {
			@Override
			public void callback(JSONObject jsonObject) {
				if (jsonObject != null) {
					adInfoes = parseADInfoJson(jsonObject);
					if (adInfoes != null) {
						listener.onSuccess(adInfoes);
					} else {
						listener.onFailure(R.string.net_exception);
					}
				} else {
					listener.onFailure(R.string.net_exception);
				}
			}
		});
	}

	@Override
	public ArrayList<ADInfo> parseADInfoJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		ArrayList<ADInfo> list = new ArrayList<ADInfo>();
		try {
			boolean flag = jsonObject.getBoolean("flag");
			if (flag) {
				JSONArray jsonArray = jsonObject.getJSONArray("content");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					String adContent  = jsonObject2.getString("adContent");
					String adDuration = jsonObject2.getString("adDuration");
					String adID       = jsonObject2.getString("adID");
					String adLink     = jsonObject2.getString("adLink");
					String adType     = jsonObject2.getString("adType");
					String subtitle   = jsonObject2.getString("subtitle");
					ADInfo adInfo = new ADInfo(adContent, adDuration, adID, adLink, adType, subtitle);
					list.add(adInfo);
				}
				return list;
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
