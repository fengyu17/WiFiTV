/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-31 下午3:14:01
 * 
 */
package com.geeya.wifitv.ui.interf;

import java.util.ArrayList;

import com.geeya.wifitv.bean.ProgramInfo;

/**
 * @author Administrator
 *
 */
public interface IProgramFrag {
	
	public void initGridView(ArrayList<ProgramInfo> programInfoes);
	
	public void updateGridView();
	
	public int getCurrentPosition();
	
	public void showToast(int msg);

}
