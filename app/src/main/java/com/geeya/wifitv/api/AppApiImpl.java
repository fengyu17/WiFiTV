/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-7-17 上午10:27:52
 */
package com.geeya.wifitv.api;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;

import com.geeya.wifitv.AppConfig;
import com.geeya.wifitv.R;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.bean.ApkInfo;
import com.geeya.wifitv.bean.AreaInfo;
import com.geeya.wifitv.bean.ConfigInfo;
import com.geeya.wifitv.presenter.ActionCallbackListener;
import com.geeya.wifitv.presenter.AppActionImpl.CallBack;
import com.geeya.wifitv.utils.Tools;
import com.geeya.wifitv.utils.VolleyUtil;

/**
 * @author Administrator
 */
public class AppApiImpl implements AppApi {

    private Context context;
    private WifiManager wifiManager;
    private ConnectivityManager connectManager;

    public AppApiImpl(Context context) {
        this.context = context;
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public AppApiImpl() {
        this.context = WiFiTVApplication.getInstance().getApplicationContext();
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean isFirstStart() {
        // TODO Auto-generated method stub
        if (WiFiTVApplication.getInstance().getPreferenceUtils().getBooleanValue("isFirstStart")) {
            WiFiTVApplication.getInstance().getPreferenceUtils().saveBooleanValue("isFirstStart", false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void delayLaunch(CallBack callback) {
        // TODO Auto-generated method stub
        new Thread(new MyThread(callback)).start();
    }

    private static class MyThread implements Runnable {

        WeakReference<CallBack> callbackWR;

        public MyThread(CallBack callback) {
            callbackWR = new WeakReference<CallBack>(callback);
        }

        @Override
        public void run() {
            Looper.prepare();
            final CallBack callback = (CallBack) callbackWR.get();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    // Go to main activity, and finish load activity
                    callback.back();
                }
            }, 2500);
            Looper.loop();
        }

    }

    @Override
    public void initConfig() {
        // TODO Auto-generated method stub
        // 初始化配置
        ConfigInfo configInfo = Tools.loadConfig(context);
        if (configInfo == null) {
            configInfo = WiFiTVApplication.getInstance().getConfigInfo();
            Tools.saveConfig(context, configInfo);
        } else {
            WiFiTVApplication.getInstance().setConfigInfo(configInfo);
        }
    }

    @Override
    public void getAreaInfo() {
        // 获取本机mac和AP mac
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        String dns2 = intToIP(dhcpInfo.dns2);
        WiFiTVApplication.getInstance().getAreaInfo().setNetElementIP(dns2);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String macAddress = wifiInfo.getMacAddress().replaceAll(":", "");
        WiFiTVApplication.getInstance().setMacAddress(macAddress);
        String apMacAddress = wifiInfo.getBSSID().replaceAll(":", "");

        String url = AppConfig.CLOUD_IP + AppConfig.URL_LOCATION;

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apMacAddress", apMacAddress);
        VolleyUtil.volleyPost(url, map, new AppApiResponse<String>() {

            @Override
            public void callback(String str) {
                // TODO Auto-generated method stub
                parseAreaInfo(str);
            }
        });
    }

    /**
     * 将int类型的IP地址转化为String类型的IP地址
     *
     * @param paramInt
     * @return Created by Administrator Created on 2015-7-30 下午4:31:58
     */
    private String intToIP(int paramInt) {
        return (0xff & paramInt) + "." + (0xff & paramInt >> 8) + "." + (0xff & paramInt >> 16) + "." + (0xff & paramInt >> 24);
    }

    /**
     * 解析位置信息
     *
     * @param str Created by Administrator Created on 2015-7-29 上午8:54:03
     */
    private void parseAreaInfo(String str) {
        if (str != null) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                boolean flag = jsonObject.getBoolean("flag");
                if (flag) {
                    JSONObject jsonObject2 = jsonObject.getJSONObject("content");
                    AreaInfo areaInfo = new AreaInfo(jsonObject2);
                    WiFiTVApplication.getInstance().setAreaInfo(areaInfo);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    // 检查更新
    @Override
    public void checkUpdate(final ActionCallbackListener<ApkInfo> listener, String areaId, String verCode) {
        // TODO Auto-generated method stub
        String platForm = "android";
        if (null != verCode) {
            String netElementIP = WiFiTVApplication.getInstance().getAreaInfo().getNetElementIP();
            if (netElementIP == null) {
                return;
            }
            String url = netElementIP + AppConfig.URL_CHECKUPDATE + "?";
            url += "platForm=" + platForm + "&areaId=" + areaId + "&version=" + verCode;
            VolleyUtil.volleyGet(url, new AppApiResponse<JSONObject>() {
                @Override
                public void callback(JSONObject object) {
                    if (null != object) {
                        ApkInfo apkInfo = parseUpdateInfo(object);
                        if (apkInfo != null)
                            listener.onSuccess(apkInfo);
                        else
                            listener.onFailure("没有更新版本");
                    } else {
                        listener.onFailure(context.getResources().getString(R.string.net_exception));
                    }
                }
            });
        } else {
            return;
        }
    }

    private ApkInfo parseUpdateInfo(JSONObject object) {
        try {
            String version = null;
            String url = null;
            String describle = object.getString("desc");
            if (object.getBoolean("flag")) {
                JSONObject content = object.getJSONObject("content");
                version = content.getString("version");
                url = content.getString("url");
                ApkInfo apkInfo = new ApkInfo(version, describle, url);
                return apkInfo;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**************************
     * 网络连接
     ****************************************/
    @Override
    public void connectWifi(int netId) {
        int priority = 0;
        WifiConfiguration config = null;
        List<WifiConfiguration> wifiCfgList = wifiManager.getConfiguredNetworks();
        for (int i = 0; i < wifiCfgList.size(); i++) {
            if (priority < wifiCfgList.get(i).priority) {// 地址相同
                priority = wifiCfgList.get(i).priority;
            }
            if (wifiCfgList.get(i).networkId == netId)
                config = wifiCfgList.get(i);
        }
        //当wifi优先级超标时，重置优先级
        if (priority < 0 || priority > 999999) {
            for (WifiConfiguration configuration : wifiCfgList) {
                if (configuration.networkId != -1) {
                    configuration.priority = 0;
                    wifiManager.updateNetwork(configuration);
                }
            }
            priority = 0;
        }
        //设置连接wifi的优先级
        if (config != null) {
            config.priority = ++priority;
            wifiManager.updateNetwork(config);
        }
        wifiManager.enableNetwork(netId, true);
        wifiManager.saveConfiguration();
        wifiManager.reconnect();
    }

    @Override
    public void openWifi() {
        wifiManager.setWifiEnabled(true);
    }

    @Override
    public void closewifi() {
        // TODO Auto-generated method stub
        wifiManager.setWifiEnabled(false);
    }

    @Override
    public boolean isWifiOpen() {
        int wifiState = wifiManager.getWifiState();
        if (wifiState == WifiManager.WIFI_STATE_ENABLING || wifiState == WifiManager.WIFI_STATE_ENABLED)
            return true;
        else
            return false;
    }

    @Override
    public boolean isWifiConnected() {
        NetworkInfo networkInfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        DetailedState connetivityState = networkInfo.getDetailedState();
        if (connetivityState.equals(DetailedState.CONNECTED)) {
            return true;
        } else
            return false;
    }

    @Override
    public boolean isConnectedWifiRight() {
        if (isWifiConnected()) {
            String ssid = wifiManager.getConnectionInfo().getSSID();
            if (ssid != null && ssid.length() > 3) {
                // 正式使用请反注释此句
                // if(ssid.substring(0, 4).equals("ai-"))
                return true;
            }
        }
        return false;
    }

    @Override
    public int isConfiguration(String SSID) {
        List<WifiConfiguration> wifiCfgList = wifiManager.getConfiguredNetworks();
        for (int i = 0; i < wifiCfgList.size(); i++) {
            if (wifiCfgList.get(i).SSID.equals(SSID)) {// 地址相同
                return wifiCfgList.get(i).networkId;
            }
        }
        return -1;
    }

    @Override
    public int chargeCipherType(String cipherType) {
        if (cipherType.contains("WPA") || cipherType.contains("wpa"))
            return 1;
        else if (cipherType.contains("WEP") || cipherType.contains("wep"))
            return 2;
        else if (cipherType.contains("EAP") || cipherType.contains("eap"))
            return 3;
        else
            return 0;
    }

    @Override
    public int addWifiConfiguration(String SSID, int cipherType) {
        return addWifiConfiguration(SSID, null, cipherType);
    }

    @Override
    public int addWifiConfiguration(String SSID, String passWD, int cipherType) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";
        if (0 == cipherType) {
            config.wepKeys[0] = "\"" + "\"";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (1 == cipherType) {
            config.preSharedKey = "\"" + passWD + "\"";
            config.hiddenSSID = false;
            config.status = WifiConfiguration.Status.ENABLED;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        } else if (2 == cipherType) {
            config.hiddenSSID = false;
            config.wepKeys[0] = "\"" + passWD + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (3 == cipherType) {
            return -2;
        }
        return wifiManager.addNetwork(config);
    }

}
