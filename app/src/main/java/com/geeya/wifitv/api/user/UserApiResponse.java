/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-16 下午7:57:57
 * 
 */
package com.geeya.wifitv.api.user;

import com.geeya.wifitv.api.AppApiResponse;
import com.geeya.wifitv.bean.UserInfo;

/**
 * @author Administrator
 *
 */
public abstract class UserApiResponse<T> extends AppApiResponse<T> {
	
	protected UserInfo userInfo;
	
	public UserApiResponse() {
      // TODO Auto-generated constructor stub
    }

}
