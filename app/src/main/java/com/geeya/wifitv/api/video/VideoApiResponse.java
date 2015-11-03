/**
 * Copyright 2015 GYYM
 * 
 * All right reserved
 * 
 * Created on 2015-7-16 下午7:58:43
 * 
 */
package com.geeya.wifitv.api.video;

import java.util.ArrayList;

import com.geeya.wifitv.api.AppApiResponse;
import com.geeya.wifitv.bean.ChannelInfo;
import com.geeya.wifitv.bean.ProgramDetail;
import com.geeya.wifitv.bean.ProgramInfo;

/**
 * @author Administrator
 * 
 */
public abstract class VideoApiResponse<T> extends AppApiResponse<T> {

	protected ArrayList<ProgramInfo> programInfoes;
	protected ProgramDetail programDetail;
	protected ArrayList<ChannelInfo> channelInfoes;

	public VideoApiResponse() {
		// TODO Auto-generated constructor stub
	}

}
