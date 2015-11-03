/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-8-6 下午1:41:26
 * 
 */
package com.geeya.wifitv.ui.interf;

import java.util.ArrayList;

import com.geeya.wifitv.bean.ADInfo;

/**
 * @author Administrator
 *
 */
public interface IProgramPlay {
	
	public void initProgramPlay();
	
	public void initStartAD(ArrayList<ADInfo> adInfoes);
	
	public void initCornerAD(ArrayList<ADInfo> adInfoes);
	
	public void initSubtitleAD(ArrayList<ADInfo> adInfoes);
	
	public void initPauseAD(ArrayList<ADInfo> adInfoes);
	
}
