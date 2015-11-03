/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-16 下午7:58:08
 * 
 */
package com.geeya.wifitv.api.video;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.geeya.wifitv.bean.ChannelInfo;
import com.geeya.wifitv.bean.ProgramDetail;
import com.geeya.wifitv.bean.ProgramInfo;
import com.geeya.wifitv.presenter.video.VideoActionCallbackListener;


/**
 * @author Administrator
 *
 */
public interface VideoApi {
	
	public void getProgramInfo(VideoActionCallbackListener<ArrayList<ProgramInfo>> listener, String areaId, String programType, String key, int page, int numberpage);
	
	public ArrayList<ProgramInfo> parseProgramInfoJson(JSONObject jsonObject);

	public void getProgramDetail(VideoActionCallbackListener<ProgramDetail> listener, String programID);
	
	public ProgramDetail parseProgramDetailJson(JSONObject jsonObject);
	
	public void getChannelInfo(VideoActionCallbackListener<ArrayList<ChannelInfo>> listener);
	
	public List<ChannelInfo> parseChannelInfoJson(JSONObject jsonObject);
	
}
