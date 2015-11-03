/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-22 上午11:31:43
 * 
 */
package com.geeya.wifitv.presenter.user;

import java.lang.ref.WeakReference;
import com.geeya.wifitv.api.user.UserApi;
import com.geeya.wifitv.api.user.UserApiImpl;
import com.geeya.wifitv.ui.interf.ILogin;
import com.geeya.wifitv.ui.interf.ILogout;
import com.geeya.wifitv.ui.interf.IRegister;

/**
 * @author Administrator
 * 
 */
public class UserActionImpl implements UserAction {

	WeakReference<ILogin> iLogin;
	WeakReference<ILogout> iLogout;
	private IRegister iRegister;
	private UserApi userApi;
	
	public UserActionImpl() {
		this.userApi = new UserApiImpl();
	}
	
	public UserActionImpl(ILogin login) {
		this.iLogin = new WeakReference<ILogin>(login);
		this.userApi = new UserApiImpl();
	}

	public UserActionImpl(IRegister register) {
		this.iRegister = register;
		this.userApi = new UserApiImpl();
	}

	public UserActionImpl(ILogout logout) {
		this.iLogout = new WeakReference<ILogout>(logout);
		this.userApi = new UserApiImpl();
	}

	@Override
	public void userLogin() {
		// TODO Auto-generated method stub
		final String account = ((ILogin)iLogin.get()).getUserName();
		final String passWord = ((ILogin)iLogin.get()).getUserPassword();

		userApi.userLogin(account, passWord, new UserActionCallbackListener<Void>() {

			@Override
			public void onSuccess(Void data) {
				// TODO Auto-generated method stub
				userApi.setUserLoginInfo(account, passWord);
				((ILogin)iLogin.get()).kill();
			}

			@Override
			public void onFailure(String message) {
				// TODO Auto-generated method stub
				((ILogin)iLogin.get()).showToast(message);
			}
		});
	}

	@Override
	public void userRegist() {
		// TODO Auto-generated method stub
		String registAccount = iRegister.getEtAccount();
		String registerName = iRegister.getEtName();
		String registPassWord = iRegister.getEtPassword();
		String registRepeatPassword = iRegister.getEtRepeatPassword();
		userApi.userRegist(registAccount, registerName, registPassWord, registRepeatPassword, new UserActionCallbackListener<Void>() {
			@Override
			public void onSuccess(Void data) {
				iRegister.kill();
			}

			@Override
			public void onFailure(String message) {
				iRegister.showErrorInfo(message);
			}
		});
	}

	@Override
	public void guestLogin() {
		userApi.guestLogin(null);
	}

	@Override
	public void userLogout() {
		userApi.guestLogin(new UserActionCallbackListener<Void>() {
			@Override
			public void onSuccess(Void data) {
				((ILogout)iLogout.get()).showMessage("注销成功");
				((ILogout)iLogout.get()).afterLogout();
				userApi.setUserLoginInfo("", "");
			}

			@Override
			public void onFailure(String message) {
				// 网络连接异常的做法
				((ILogout)iLogout.get()).showMessage(message);
			}
		});
	}

}
