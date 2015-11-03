/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-20 下午4:08:22
 * 
 */
package com.geeya.wifitv.bean;

/**
 * @author Administrator
 * 
 */
public class ProgramSource extends Entity {

	private static final long serialVersionUID = 1L;

	private int episodeNum;
	private String sourceName;
	private String url;

	public ProgramSource(int episodeNum, String sourceName, String url) {
		this.episodeNum = episodeNum;
		this.sourceName = sourceName;
		this.url = url;
	}

	public void setEpisodeNum(int episodeNum) {
		this.episodeNum = episodeNum;
	}

	public int getEpisodeNum() {
		return episodeNum;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
