/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-9-2 上午11:52:24
 */
package com.geeya.wifitv.ui.activity;

import java.io.File;
import java.math.BigDecimal;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.ConfigInfo;
import com.geeya.wifitv.utils.Tools;
import com.geeya.wifitv.utils.DataCleanManager;

/**
 * @author Administrator
 */
public class SettingActivity extends Activity implements OnClickListener, OnCheckedChangeListener {

    private Context mContext;
    private ConfigInfo configInfo;

    private CheckBox cbCheckWifi;
    private CheckBox cbNotice;
    private CheckBox cbUpdate;
    private FrameLayout flDownloadPath;
    private TextView tvDownloadPath;
    private FrameLayout flClearCache;
    private TextView tvClearCache;
    private ProgressBar pbClearCache;
    private FrameLayout flReset;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_setting);
        mContext = getApplicationContext();
        configInfo = Tools.loadConfig(mContext);
        initView();
//        updateView();
    }

    private void initView() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("设置");

        cbCheckWifi = (CheckBox) findViewById(R.id.cb_setting_checkwifi);
        cbNotice = (CheckBox) findViewById(R.id.cb_setting_notice);
        cbUpdate = (CheckBox) findViewById(R.id.cb_setting_update);
        flDownloadPath = (FrameLayout) findViewById(R.id.fl_setting_downloadpath);
        tvDownloadPath = (TextView) findViewById(R.id.tv_setting_downloadpath);
        flClearCache = (FrameLayout) findViewById(R.id.fl_setting_clearcache);
        tvClearCache = (TextView) findViewById(R.id.tv_setting_clearcache);
        pbClearCache = (ProgressBar) findViewById(R.id.pb_clearcache);
        flReset = (FrameLayout) findViewById(R.id.fl_setting_reset);
//        LinearLayout llBack = (LinearLayout) findViewById(R.id.back);
//        TextView tvTitle = (TextView) findViewById(R.id.title);
//        tvTitle.setText(R.string.user_set);
//        llBack.setOnClickListener(this);
        String size = getCacheSize();
        tvClearCache.setText(size);
        flDownloadPath.setOnClickListener(this);
        flClearCache.setOnClickListener(this);
        flReset.setOnClickListener(this);
        cbCheckWifi.setOnCheckedChangeListener(this);
        cbNotice.setOnCheckedChangeListener(this);
        cbUpdate.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_setting_checkwifi:
                if (isChecked) {
                    configInfo.setAutoCheckWifi(true);
                } else {
                    configInfo.setAutoCheckWifi(false);
                }
                Tools.saveConfig(mContext, configInfo);
                break;
            case R.id.cb_setting_update:
                if (isChecked) {
                    configInfo.setAutoUpdate(true);
                } else {
                    configInfo.setAutoUpdate(false);
                }
                Tools.saveConfig(mContext, configInfo);
                break;
            case R.id.cb_setting_notice:
                if (isChecked) {
                    configInfo.setAcceptNotice(true);
                } else {
                    configInfo.setAcceptNotice(false);
                }
                Tools.saveConfig(mContext, configInfo);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.fl_setting_downloadpath:
                Intent intent = new Intent(mContext, FolderSelectedActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.fl_setting_clearcache:
                tvClearCache.setVisibility(View.GONE);
                pbClearCache.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        tvClearCache.setText(getCacheSize());
                        pbClearCache.setVisibility(View.GONE);
                        tvClearCache.setVisibility(View.VISIBLE);
                    }
                }, 1000);
                clearCache();
                break;
            case R.id.fl_setting_reset:
                configInfo = new ConfigInfo(mContext);
                Tools.saveConfig(mContext, configInfo);
                updateView();
                break;
            default:
                break;
        }
    }

    private void updateView() {
        cbCheckWifi.setChecked(configInfo.getAutoCheckWifi());
        cbNotice.setChecked(configInfo.getAcceptNotice());
        cbUpdate.setChecked(configInfo.getAutoUpdate());
        if (configInfo.getDownloadPath().equals(mContext.getCacheDir().toString())) {
            tvDownloadPath.setText("默认设置");
        } else {
            tvDownloadPath.setText(" [" + configInfo.getDownloadPath() + "]");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            String newDownloadPath = data.getStringExtra("result");
            if (newDownloadPath != null && !newDownloadPath.equals("")) {
                tvDownloadPath.setText(R.string.setting_downloadpath + " [" + newDownloadPath + "]");
                configInfo.setDownloadPath(newDownloadPath);
                Tools.saveConfig(mContext, configInfo);
            }
        }
    }

    private void clearCache() {
        File directory = mContext.getCacheDir();
        DataCleanManager.deleteFilesByDirectory(directory);
    }

    private String getCacheSize() {
        try {
            return formatSize(getFolderSize(mContext.getCacheDir()) + getFolderSize(mContext.getExternalCacheDir()) + getFolderSize(new File(mContext.getFilesDir().getParent() + "/app_webview/Cache/")));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "wrong";
        }
    }

    private String formatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
        return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "GB";

    }

    private double getFolderSize(File file) throws Exception {
        double size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size += getFolderSize(fileList[i]);
                } else {
                    size += fileList[i].length();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return size;
    }

}
