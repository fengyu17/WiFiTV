/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-7-16 下午7:57:45
 */
package com.geeya.wifitv.api.user;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;

import com.geeya.wifitv.AppConfig;
import com.geeya.wifitv.R;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.bean.Guest;
import com.geeya.wifitv.bean.Register;
import com.geeya.wifitv.bean.VipRegister;
import com.geeya.wifitv.presenter.user.UserActionCallbackListener;
import com.geeya.wifitv.utils.VolleyUtil;

/**
 * @author Administrator
 *
 */
public class UserApiImpl implements UserApi {

    private Context context;
    private WiFiTVApplication application;

    public UserApiImpl() {
        application = WiFiTVApplication.getInstance();
        this.context = application.getApplicationContext();
    }

    @Override
    public void userLogin(String userAccount, String passWD, final UserActionCallbackListener<Void> listener) {
        // TODO Auto-generated method stub
        final HashMap<String, String> userLoginInfo = new HashMap<String, String>();
        String mac = application.getMacAddress();
        userLoginInfo.put("account", userAccount);
        userLoginInfo.put("passwd", passWD);
        userLoginInfo.put("mac_addr", mac);
        String url = AppConfig.CLOUD_IP + AppConfig.URL_LOGIN;
        VolleyUtil.volleyPost(url, userLoginInfo, new UserApiResponse<String>() {

            @Override
            public void callback(String str) {
                int result = loginDataAnalysis(str);
                if (listener != null) {
                    if (result == 0)
                        listener.onSuccess(null);
                    else if (1 == result) {
                        listener.onFailure("登录失败");
                    } else if (2 == result) {
                        listener.onFailure("登录失败");
                    } else {
                        listener.onFailure("登录失败");
                    }
                }
            }
        });
    }

    @Override
    public void userRegist(String userAccount, String userName, String passWD, String repeatPassWD, final UserActionCallbackListener<Void> listener) {
        if (!passWD.equals(repeatPassWD)) {
            listener.onFailure("*两次密码输入不一致");
        } else {
            final HashMap<String, String> userRegistInfo = new HashMap<String, String>();
            String mac = application.getMacAddress();
            userRegistInfo.put("account", userAccount);
            userRegistInfo.put("username", userName);
            userRegistInfo.put("passwd", passWD);
            userRegistInfo.put("mac_addr", mac);
            userRegistInfo.put("areaID", "0");
            String url = AppConfig.CLOUD_IP + AppConfig.URL_REGISTER;
            VolleyUtil.volleyPost(url, userRegistInfo, new UserApiResponse<String>() {
                @Override
                public void callback(String str) {
                    if (str != null) {
                        String result = userRegistDataAnalysis(str);
                        if (result.equals("注册成功")) {
                            listener.onSuccess(null);
                        } else {
                            listener.onFailure(result);
                        }
                    } else {
                        listener.onFailure("网络异常");
                    }
                }
            });
        }
    }

    /**
     *
     * @param data
     * @return 0:登录成功; 1:获取Json字符串异; 2:连接服务器异常; -1:用户名或密码错误;
     */
    @Override
    public int loginDataAnalysis(String data) {
        int errorCode = 2;//
        if (null != data && (!data.equals(""))) {
            try {
                int istelephone = data.indexOf("telephone");
                JSONObject response = new JSONObject(data);
                Boolean flag = response.getBoolean("flag");
                if (flag) {
                    JSONObject content = response.getJSONObject("content");
                    if (null != content && (!content.equals(""))) {
                        // WiFiTVApplication.getInstance().getUserInfo().setUserName(content.getString("userName"));
                        String userName = content.getString("userName");
                        int isGuest = content.getInt("isGuest");// 通过用户身份来实例化对象
                        switch (isGuest) {
                            case 0:
                                WiFiTVApplication.getInstance().setUserInfo(new Register());
                                break;
                            case 1:
                                WiFiTVApplication.getInstance().setUserInfo(new Guest());
                                break;
                            case 2:
                                WiFiTVApplication.getInstance().setUserInfo(new VipRegister());
                                break;
                            default:
                                break;
                        }
                        if (null == userName || userName.equals(""))
                            userName = "游客";
                        application.getUserInfo().setUserName(userName);
                        application.getUserInfo().setUserEmail(content.getString("email"));
                        application.getUserInfo().setCreateTime(content.getString("createTime"));
                        application.getUserInfo().setUserId(content.getString("userID"));
                        application.getUserInfo().setUserIdentify(isGuest);
                        if (istelephone == -1)
                            application.getUserInfo().setUserTel(content.getString("tel"));
                        else
                            application.getUserInfo().setUserTel(content.getString("telephone"));
                        errorCode = 0; //登录成功
                    } else
                        errorCode = 1; //获取Json字符串异常
                } else {
                    errorCode = -1; //用户名或密码错误
                }
            } catch (Exception e) {
                errorCode = 1;
            }
        }
        return errorCode;
    }

    @Override
    public String userRegistDataAnalysis(String data) {
        String result = "服务器异常，请稍后重试";
        if (null != data && (!data.equals(""))) {
            try {
                JSONObject response = new JSONObject(data);
                Boolean flag = response.getBoolean("flag");
                result = response.getString("desc");
                if (!flag)
                    result = "*" + result;
                else
                    result = "注册成功";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void setUserLoginInfo(String userAccount, String passWD) {
        application.getPreferenceUtils().saveEncryptValue("userAccount", userAccount);
        application.getPreferenceUtils().saveEncryptValue("passWD", passWD);
    }

    @Override
    public String getUserLoginInfo(String key) {
        return application.getPreferenceUtils().getEncryptValue(key);
    }

    @Override
    public Boolean getBooleanValue(String key) {
        return application.getPreferenceUtils().getBooleanValue(key);
    }

    @Override
    public void guestLogin(final UserActionCallbackListener<Void> listener) {
        final HashMap<String, String> guestLoginInfo = new HashMap<String, String>();
        String mac = application.getMacAddress();
        guestLoginInfo.put("mac_addr", mac);
        String url = AppConfig.CLOUD_IP + AppConfig.URL_LOGIN;
        VolleyUtil.volleyPost(url, guestLoginInfo, new UserApiResponse<String>() {
            @Override
            public void callback(String str) {
                int result = loginDataAnalysis(str);
                if (listener != null) {
                    switch (result) {
                        case 0:
                            listener.onSuccess(null);
                            break;
                        case 1:
                            listener.onFailure("数据异常，获取失败请稍候重试");
                            break;
                        case 2:
                            listener.onFailure("数据异常，获取失败请稍候重试");
                            break;
                        default:
                            listener.onFailure("数据异常，获取失败请稍候重试");
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void autoLogin() {
        Boolean isAutoLogin = getBooleanValue("autoLogin");
        if (isAutoLogin) {
            final String userAccount = getUserLoginInfo("userAccount");
            final String userPassWord = getUserLoginInfo("passWD");
            if ((null != userAccount && (!userAccount.equals(""))) && (null != userPassWord && (!userPassWord.equals("")))) {
                userLogin(userAccount, userPassWord, null);
            } else {
                guestLogin(null);
            }
        } else {
            guestLogin(null);
        }
    }

}
