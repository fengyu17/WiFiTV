/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-24 下午3:06:54
 * 
 */
package com.geeya.wifitv.ui.interf;

/**
 * @author Administrator
 * 
 */
public interface IRegister {

	public String getEtName();

	public String getEtAccount();

	public String getEtPassword();

	public String getEtRepeatPassword();

	void showErrorInfo(String message);

	public void showToast(String message);

	public void kill();

}
