package com.geeya.wifitv.bean;

public class ApkInfo extends Entity{
	
	private static final long serialVersionUID = 1L;
	
	private String apkVersion;
	private String describle;
	private String updateUrl;
	
	public ApkInfo(String apkVersion, String describle, String updateUrl){
		this.apkVersion = apkVersion;
		this.describle = describle;
		this.updateUrl = updateUrl;
	}
	
	public void setApkVersion(String apkVersion){
		this.apkVersion = apkVersion;
	}
	
	public void setDescrible(String describle){
		this.describle = describle;
	}
	
	public void setUpdateUrl(String updateUrl){
		this.updateUrl = updateUrl;
	}
	
	public String getApkVersion(){
		return apkVersion;
	}
	
	public String getDescrible(){
		return describle;
	}
	
	public String getUpdateUrl(){
		return updateUrl;
	}

}
