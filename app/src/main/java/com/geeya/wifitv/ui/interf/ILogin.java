/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-24 下午2:41:02
 * 
 */
package com.geeya.wifitv.ui.interf;

/**
 * @author Administrator
 *
 */
public interface ILogin {
	
	public String getUserName();
	
	public String getUserPassword();
	
	public void showToast(String message);
	
	public void kill();

}
