package com.geeya.wifitv.presenter;

public interface ActionCallbackListener<T> extends ActionCallback {

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
