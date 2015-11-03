/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-28 下午1:38:03
 * 
 */
package com.geeya.wifitv.bean;


/**
 * @author Administrator
 * 
 */
public class ADInfo extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String adContent; // 广告内容
	private String adDuration; // 广告时间间隔
	private String adID; // 广告ID
	private String adLink = "http://www.wifitv.com.cn"; // 广告链接
	private String adType; // 广告类型
	private String subtitle; // 字幕广告

	public ADInfo(String adContent, String adDuration, String adID, String adLink, String adType, String subtitle) {
		this.adContent = adContent;
		this.adDuration = adDuration;
		this.adID = adID;
		this.adLink = adLink;
		this.adType = adType;
		this.subtitle = subtitle;
	}

	public void setADContent(String adContent) {
		this.adContent = adContent;
	}

	public String getADContent() {
		return adContent;
	}

	public void setADDuration(String adDuration) {
		this.adDuration = adDuration;
	}

	public String getADDuration() {
		return adDuration;
	}

	public void setADID(String adID) {
		this.adID = adID;
	}

	public String getADID() {
		return adID;
	}

	public void setADLink(String adLink) {
		if (adLink != null && !adLink.equals("")) {
			this.adLink = adLink;
		}
	}

	public String getADLink() {
		return adLink;
	}

	public void setADType(String adType) {
		this.adType = adType;
	}

	public String getADType() {
		return adType;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getSubtitle() {
		return subtitle;
	}

}
