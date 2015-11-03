/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-22 上午11:30:44
 * 
 */
package com.geeya.wifitv.presenter.player;

/**
 * @author Administrator
 *
 */
public interface PlayerAction {
	
	/**
	 * 直播广告播放
	 *
	 * Created by Administrator
	 * Created on 2015-8-7 下午4:08:02
	 */
	public void liveADPlayerAction();
	
	/**
	 * 点播广告播放
	 *
	 * Created by Administrator
	 * Created on 2015-8-11 上午9:23:57
	 */
	public void programADPlayerAction();
	
	/**
	 * 直播播放
	 *
	 * Created by Administrator
	 * Created on 2015-8-6 下午1:34:59
	 */
	public void livePlayerAction();
	
	/**
	 * 点播播放
	 *
	 * Created by Administrator
	 * Created on 2015-8-6 下午1:33:56
	 */
	public void programPlayerAction();

}
