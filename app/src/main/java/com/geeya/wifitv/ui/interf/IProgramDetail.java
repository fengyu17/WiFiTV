/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-8-5 上午9:11:36
 * 
 */
package com.geeya.wifitv.ui.interf;

import com.geeya.wifitv.bean.ProgramDetail;

/**
 * @author Administrator
 *
 */
public interface IProgramDetail {
	
	
	/**
	 * 更新UI
	 * 
	 * @param programDetail
	 *
	 * Created by Administrator
	 * Created on 2015-8-5 上午10:48:49
	 */
	public void updateView(ProgramDetail programDetail);
	
	/**
	 * 显示通知信息
	 *
	 * Created by Administrator
	 * Created on 2015-8-5 上午9:33:43
	 */
	public void showToast(int message);

}
