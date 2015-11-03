/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-16 下午7:57:18
 * 
 */
package com.geeya.wifitv.api.advertisement;

import java.util.ArrayList;

import com.geeya.wifitv.api.AppApiResponse;
import com.geeya.wifitv.bean.ADInfo;

/**
 * @author Administrator
 *
 */
public abstract class AdApiResponse<T> extends AppApiResponse<T> {
	
	protected ArrayList<ADInfo> adInfoes;
	
	public AdApiResponse() {
      // TODO Auto-generated constructor stub
    }

}
