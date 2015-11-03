/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-8-12 上午9:06:27
 * 
 */
package com.geeya.wifitv.bean;

/**
 * @author Administrator
 * 
 */
public class ProgramInfo extends Entity {

	private static final long serialVersionUID = 1L;

	private String coverImg;
	private String name;
	private String programID;

	public ProgramInfo(String coverImg, String name, String programID) {
		this.coverImg = coverImg;
		this.name = name;
		this.programID = programID;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setProgramID(String programID) {
		this.programID = programID;
	}

	public String getProgramID() {
		return programID;
	}

}
