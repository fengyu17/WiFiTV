/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-9-6 上午10:57:57
 */
package com.geeya.wifitv.bean;

import android.content.Context;

/**
 * 程序配置信息
 * @author Administrator
 *
 */
public class ConfigInfo extends Entity {

    private static final long serialVersionUID = 1L;

    private boolean autoCheckWifi;
    private boolean acceptNotice;
    private boolean autoUpdate;
    private String downloadPath;

    public ConfigInfo(Context context) {
        autoCheckWifi = true;
        acceptNotice = true;
        autoUpdate = true;
        downloadPath = context.getCacheDir().toString();
    }

    public void setAutoCheckWifi(boolean autoCheckWifi) {
        this.autoCheckWifi = autoCheckWifi;
    }

    public boolean getAutoCheckWifi() {
        return autoCheckWifi;
    }

    public void setAcceptNotice(boolean acceptNotice) {
        this.acceptNotice = acceptNotice;
    }

    public boolean getAcceptNotice() {
        return acceptNotice;
    }

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public boolean getAutoUpdate() {
        return autoUpdate;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

}
