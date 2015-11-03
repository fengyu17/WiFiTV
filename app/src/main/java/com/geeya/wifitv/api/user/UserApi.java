/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-16 下午7:57:34
 * 
 */
package com.geeya.wifitv.api.user;

import com.geeya.wifitv.presenter.user.UserActionCallbackListener;

/**
 * @author Administrator
 * 
 */
public interface UserApi {

	/**
	 * 用户登录接口
	 * @param userAccount
	 * @param passWD
	 * @param listener
	 *
	 * Created by Administrator
	 * Created on 2015-7-29 上午10:27:38
	 */
	public void userLogin(String userAccount, String passWD, UserActionCallbackListener<Void> listener);

	/**
	 * 用户登录数据分析
	 * @param data
	 * @return
	 *
	 * Created by Administrator
	 * Created on 2015-7-29 上午10:28:28
	 */
	public int loginDataAnalysis(String data);


	/**
	 * 用户注册接口
	 * @param userAccount
	 * @param userName
	 * @param passWD
	 * @param repeatPassWD
	 * @param listener
	 *
	 * Created by Administrator
	 * Created on 2015-7-29 上午10:28:47
	 */
	public void userRegist(String userAccount, String userName, String passWD, String repeatPassWD, UserActionCallbackListener<Void> listener);

	/**
	 * 用户注册数据分析
	 * @param data
	 * @return
	 *
	 * Created by Administrator
	 * Created on 2015-7-29 上午10:29:03
	 */
	public String userRegistDataAnalysis(String data);
	
	public void setUserLoginInfo(String userAccount, String passWD);
	
	public String getUserLoginInfo(String key);
	
	public Boolean getBooleanValue(String key);
	
	public void guestLogin(UserActionCallbackListener<Void> listener);
	
	/**
	 * 用户自动登录接口
	 *
	 * Created by Administrator
	 * Created on 2015-8-25 下午3:30:03
	 */
	public void autoLogin();
	

}
