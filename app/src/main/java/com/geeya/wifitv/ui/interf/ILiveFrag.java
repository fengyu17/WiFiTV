/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-27 下午2:58:10
 * 
 */
package com.geeya.wifitv.ui.interf;

import java.util.ArrayList;

import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.bean.ChannelInfo;

/**
 * @author Administrator
 *
 */
public interface ILiveFrag {
	
	public void initSlideView(ArrayList<ADInfo> adInfoes);
	
	public void initLiveList(ArrayList<ChannelInfo> channelInfoes);
	
	public void showToast(int msg);

}
