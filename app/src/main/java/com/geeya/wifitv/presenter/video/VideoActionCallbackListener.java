/**
 * Copyright 2015 GYYM
 * 
 * All right reserved
 * 
 * Created on 2015-7-30 上午9:51:00
 * 
 */
package com.geeya.wifitv.presenter.video;

import com.geeya.wifitv.presenter.ActionCallback;

/**
 * @author Administrator
 * 
 */
public interface VideoActionCallbackListener<T> extends ActionCallback {

  /**
   * 成功回调
   * 
   * @param data
   * @return void
   * @throws
   */
  public void onSuccess(T programInfoes);

  /**
   * 失败回调
   * 
   * @param message
   * @return void
   * @throws
   */
  public void onFailure(int message);

}
