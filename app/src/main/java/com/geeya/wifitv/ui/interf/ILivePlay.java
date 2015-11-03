/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-8-10 下午3:15:39
 * 
 */
package com.geeya.wifitv.ui.interf;

import java.util.ArrayList;

import com.geeya.wifitv.bean.ADInfo;

/**
 * @author Administrator
 * 
 */
public interface ILivePlay {

	public void initLivePlay();

	public void initStartAD(ArrayList<ADInfo> adInfoes);
	
	public void initCornerAD(ArrayList<ADInfo> adInfoes);
	
	public void initSubtitleAD(ArrayList<ADInfo> adInfoes);

}
