package com.geeya.wifitv;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.geeya.wifitv.bean.AreaInfo;
import com.geeya.wifitv.bean.ConfigInfo;
import com.geeya.wifitv.bean.UserInfo;
import com.geeya.wifitv.utils.NetworkCheck;
import com.geeya.wifitv.utils.PreferenceUtils;
import com.geeya.wifitv.utils.VolleyUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/10/27.
 */
public class WiFiTVApplication extends Application {
    /**
     * app全局数据
     */
    private UserInfo userInfo = null;
    private AreaInfo areaInfo = null;
    private ConfigInfo configInfo = null;
    private String macAddress;
    private PreferenceUtils preferenceUtils;
    private NetworkCheck networkCheck = null;

    private static WiFiTVApplication application;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        application = this;
        init();
    }

    public static synchronized WiFiTVApplication getInstance() {
        return application;
    }

    private void init() {
        VolleyUtil.init(this);
        networkCheck = new NetworkCheck(getApplicationContext());
        userInfo = new UserInfo();
        configInfo = new ConfigInfo(this);
        preferenceUtils = PreferenceUtils.getInstance(this);
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setAreaInfo(AreaInfo areaInfo) {
        this.areaInfo = areaInfo;
        preferenceUtils.saveStringValue("areaInfo", areaInfo.toJSONString());
    }

    public AreaInfo getAreaInfo() {
        if (areaInfo != null) {
            return areaInfo;
        } else {
            String jsonString = preferenceUtils.getStringValue("areaInfo");
            if (jsonString.equals("")) {
                return areaInfo = new AreaInfo();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    return areaInfo = new AreaInfo(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return areaInfo = new AreaInfo();
                }
            }
        }
    }

    public PreferenceUtils getPreferenceUtils() {
        return preferenceUtils;
    }

    public void setConfigInfo(ConfigInfo configInfo) {
        this.configInfo = configInfo;
    }

    public ConfigInfo getConfigInfo() {
        if (null == configInfo) {
            return new ConfigInfo(this);
        } else {
            return configInfo;
        }
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public NetworkCheck getNetworkCheck() {
        if (networkCheck == null) {
            return new NetworkCheck(getApplicationContext());
        } else {
            return networkCheck;
        }
    }

}
