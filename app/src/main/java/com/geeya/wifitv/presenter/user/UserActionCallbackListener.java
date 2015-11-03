/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-30 上午11:02:01
 * 
 */
package com.geeya.wifitv.presenter.user;

import com.geeya.wifitv.presenter.ActionCallback;

/**
 * @author Administrator
 *
 */
public interface UserActionCallbackListener<T> extends ActionCallback {
	 /**
	   * 成功回调
	   * 
	   * @param data
	   * @return void
	   * @throws
	   */
	  public void onSuccess(T data);

	  /**
	   * 失败回调
	   * 
	   * @param message
	   * @return void
	   * @throws
	   */
	  public void onFailure(String message);
}
