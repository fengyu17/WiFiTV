package com.geeya.wifitv.presenter;

import java.lang.ref.WeakReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.os.Environment;
import android.util.Log;

import com.geeya.wifitv.R;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.api.AppApi;
import com.geeya.wifitv.api.AppApiImpl;
import com.geeya.wifitv.api.user.UserApi;
import com.geeya.wifitv.api.user.UserApiImpl;
import com.geeya.wifitv.bean.ApkInfo;
import com.geeya.wifitv.bean.AreaInfo;
import com.geeya.wifitv.ui.interf.IAddWlan;
import com.geeya.wifitv.ui.interf.IHomeFrag;
import com.geeya.wifitv.ui.interf.IJoin;
import com.geeya.wifitv.ui.interf.ILoading;
import com.geeya.wifitv.ui.interf.IUpdate;

public class AppActionImpl implements AppAction {

    private Context context = WiFiTVApplication.getInstance().getApplicationContext();

    WeakReference<ILoading> iLoading;
    WeakReference<IUpdate> iAbout;
    private IHomeFrag iHomeFrag;
    private IAddWlan iAddWlan;
    private IJoin iJoin;
    private AppApi appApi;
    private UserApi userApi;

    public AppActionImpl(ILoading loading, Context context) {
        iLoading = new WeakReference<ILoading>(loading);
        this.appApi = new AppApiImpl(context);
        this.userApi = new UserApiImpl();
    }

    public AppActionImpl(IHomeFrag homeFrag) {
        this.iHomeFrag = homeFrag;
        this.appApi = new AppApiImpl();
    }

    public AppActionImpl(Context context, IAddWlan iAddWlan) {
        this.iAddWlan = iAddWlan;
        this.context = context;
        this.appApi = new AppApiImpl(context);
    }

    public AppActionImpl(IUpdate iUpdate) {
        this.iAbout = new WeakReference<IUpdate>(iUpdate);
        this.appApi = new AppApiImpl();
    }

    public AppActionImpl(IJoin iJoin) {
        this.iJoin = iJoin;
    }

    @Override
    public void setEnabled() {
        if ((iJoin.getName().length() > 1) && (isPhoneNum(iJoin.getTel())) && (iJoin.getLeave().length() > 5 && iJoin.getLeave().length() < 150))
            iJoin.setEnable(true);
        else
            iJoin.setEnable(false);
    }

    @Override
    public void update() {
        String verCode = getVersion();
        AreaInfo areaInfo = WiFiTVApplication.getInstance().getAreaInfo();
        String areaID = areaInfo.getAreaID();

        appApi.checkUpdate(new ActionCallbackListener<ApkInfo>() {

            @Override
            public void onSuccess(ApkInfo data) {
                // TODO Auto-generated method stub
                String url = data.getUpdateUrl();
                if (url != null) {
                    ((IUpdate) iAbout.get()).createDialog(data);
                } else {
                    ((IUpdate) iAbout.get()).showToast("暂时没有更新版本");
                }
            }

            @Override
            public void onFailure(String message) {
                ((IUpdate) iAbout.get()).showToast(message);
            }
        }, areaID, verCode);
    }

    @Override
    public long download(String url) {
        // TODO Auto-generated method stub
        if (null == url)
            return -1;
        String[] str = url.split("\\/");
        String fileName = str[str.length - 1];
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        return downloadManager.enqueue(request);
    }

    @Override
    public void initLoad() {
        // TODO Auto-generated method stub
        if (appApi.isFirstStart()) {
            ((ILoading) iLoading.get()).initViewPager();
        } else {
            ((ILoading) iLoading.get()).initWelcome();
            appApi.delayLaunch(new CallBack());
        }
        appApi.initConfig();
        if (WiFiTVApplication.getInstance().getNetworkCheck().checkNetWorkState()) {
            appApi.getAreaInfo();
            userApi.autoLogin();
        }
    }

    @Override
    public void initWifiControl() {
        // TODO Auto-generated method stub
        boolean wifiState = appApi.isWifiOpen();
        iHomeFrag.setToggleChecked(wifiState);
        if (!wifiState) {
            iHomeFrag.shrinkTitleBar();
            iHomeFrag.toggleLocationClose();
        } else
            iHomeFrag.toggleLocationOpen();
    }

    @Override
    public void shrinkTitleBar() {
        // TODO Auto-generated method stub
        iHomeFrag.shrinkTitleBar();
    }

    @Override
    public void expandTitleBar() {
        // TODO Auto-generated method stub
        iHomeFrag.expandTitleBar();
    }

    @Override
    public void openWifi() {
        // TODO Auto-generated method stub
        appApi.openWifi();
        iHomeFrag.toggleLocationOpen();
        iHomeFrag.playOpenAnimation();
    }

    @Override
    public void closeWifi() {
        // TODO Auto-generated method stub
        appApi.closewifi();
        iHomeFrag.toggleLocationClose();
        iHomeFrag.playCloseAnimation();
    }

    @Override
    public void wifiConnection(int arg2) {
        // TODO Auto-generated method stub
        final ScanResult wifiItem = iHomeFrag.getListItem(arg2);
        int itemWifiId = appApi.isConfiguration("\"" + wifiItem.SSID + "\"");
        String cipher = wifiItem.capabilities;
        final int cipherType = appApi.chargeCipherType(cipher);
        if (appApi.isConfiguration("\"" + wifiItem.SSID + "\"") != -1)
            appApi.connectWifi(itemWifiId);
        else {
            if (0 == cipherType) {
                itemWifiId = appApi.addWifiConfiguration(wifiItem.SSID, cipherType);
                if (-1 != itemWifiId) {
                    appApi.connectWifi(itemWifiId);
                }
            } else {
                iHomeFrag.showDialog(wifiItem.SSID, cipherType);
            }
        }
    }

    @Override
    public void wifiConnection(String SSID, String str, int cipherType) {
        int netId = appApi.addWifiConfiguration(SSID, str, cipherType);
        if (netId != -1)
            appApi.connectWifi(netId);
    }

    @Override
    public void wifiConnection() {
        final String ssid = iAddWlan.getSsid();
        final String passWd = iAddWlan.getPassWd();
        final int cipherType = iAddWlan.getcipherType();
        int itemWifiId = appApi.isConfiguration("\"" + ssid + "\"");
        if (itemWifiId != -1)
            appApi.connectWifi(itemWifiId);
        else {
            int netId = appApi.addWifiConfiguration(ssid, passWd, cipherType);
            if (netId != -1)
                appApi.connectWifi(netId);
        }
    }

    public class CallBack implements ActionCallback {
        public void back() {
            ((ILoading) iLoading.get()).directTo();
        }
    }

    /**
     * 获取当前版本
     *
     * @return
     */
    private String getVersion() {
        String verCode = null;
        try {
            verCode = context.getPackageManager().getPackageInfo("com.geeya.wifitv", 0).versionName;
        } catch (NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verCode;
    }

    /**
     * 判断输入是否是电话号码
     *
     * @param phoneNum
     * @return
     */
    private boolean isPhoneNum(String phoneNum) {
        String cellphoneNumber = "^[1][3,4,5,7,8][0-9]{9}$";
        String areaPhoneNumber = "^[0][1-9]{2,3}-[1-9]{1}[0-9]{6,7}$";
        String phoneNumber = "^[1-9]{1}[0-9]{6,7}$";
        if (phoneNum.length() > 0) {
            if (phoneNum.length() == 11 && phoneNum.substring(0, 1).equals("1")) {
                return match(cellphoneNumber, phoneNum);
            } else if (phoneNum.substring(0, 1).equals("0")) {
                return match(areaPhoneNumber, phoneNum);
            } else {
                return match(phoneNumber, phoneNum);
            }
        } else
            return false;
    }

    private boolean match(String phoneNumber, String phoneNum) {
        Pattern p = Pattern.compile(phoneNumber);
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

}
