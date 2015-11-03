/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-22 上午11:32:39
 * 
 */
package com.geeya.wifitv.presenter.video;

import com.geeya.wifitv.bean.ProgramInfo;

/**
 * @author Administrator
 *
 */
public interface VideoAction {
	
	/**
	 * 直播页面
	 *
	 * Created by Administrator
	 * Created on 2015-8-5 上午9:06:18
	 */
	public void liveAction();
	
	/**
	 * 点播页面
	 * 
	 * @param index 当前请求第几页
	 * @param isInit 是否是第一次请求
	 *
	 * Created by Administrator
	 * Created on 2015-8-4 下午3:37:29
	 */
	public void programAction(int index, boolean isInit);
	
	/**
	 * 点播详情页面
	 *
	 * Created by Administrator
	 * Created on 2015-8-5 上午9:06:47
	 */
	public void programDetailAction(ProgramInfo programInfo);
	
}
