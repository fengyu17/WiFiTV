/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-20 下午3:21:58
 * 
 */
package com.geeya.wifitv.bean;

import java.util.ArrayList;


/**
 * @author Administrator
 * 
 */
public class ProgramDetail extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String director;
	private String describ;
	private String leadingActor;
	private ArrayList<ProgramSource> programSources;
	
	public ProgramDetail() {
		this.programSources = new ArrayList<ProgramSource>();
	}

	public ProgramDetail(String director, String describ, String leadingActor) {
		this.director = director;
		this.describ = describ;
		this.leadingActor = leadingActor;
		this.programSources = new ArrayList<ProgramSource>();
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getDirector() {
		return director;
	}

	public void setDescrib(String describ) {
		this.describ = describ;
	}

	public String getDescrib() {
		return describ;
	}

	public void setLeadingActor(String leadingActor) {
		this.leadingActor = leadingActor;
	}

	public String getLeadingActor() {
		return leadingActor;
	}
	
	public void add(ProgramSource programSource) {
		programSources.add(programSource);
	}
	
	public ArrayList<ProgramSource> getList() {
		return programSources;
	}
	
	public ProgramSource getItem(int index) {
		return programSources.get(index);
	}
	
	public int getLength() {
		return programSources.size();
	}

}
