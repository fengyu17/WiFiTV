/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-17 上午10:28:29
 * 
 */
package com.geeya.wifitv.api;


/**
 * @author Administrator
 *
 */
public abstract class AppApiResponse<T> {
	
	public String code;
	
	public String flag;
	
	public String describle;
	
	public boolean isOK;
	
	public int errorCode;
	
	public T object;
	
	public void setObject(T object) {
		this.object = object;
	}
	public T getObject() {
		return object;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlag() {
		return flag;
	}
	
	public void setDescrible(String describle) {
		this.describle = describle;
	}
	public String getDescrible() {
		return describle;
	}
	
	public void setIsOK(boolean isOK) {
		this.isOK = isOK;
	}
	public boolean getIsOK() {
		return isOK;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public abstract void callback(T object);
	
}
