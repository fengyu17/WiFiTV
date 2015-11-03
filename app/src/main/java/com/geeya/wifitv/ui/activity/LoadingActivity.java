package com.geeya.wifitv.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.geeya.wifitv.Log;
import com.geeya.wifitv.R;
import com.geeya.wifitv.base.BaseActivity;
import com.geeya.wifitv.ui.interf.iLoadingActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends BaseActivity implements iLoadingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        initView();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        if (isFirst()) {
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            app.getPreferenceUtils().saveBooleanValue("isFirst", true);
            finish();
        } else {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    directTo();
                }
            };
            timer.schedule(task, 2500);
        }
    }

    private boolean isFirst() {

        boolean isFirst = app.getPreferenceUtils().getBooleanValue("isFirst");
        if (isFirst)
            return false;
        else
            return true;

    }

    @Override
    public void directTo() {
        Intent intent = new Intent(this, MainTabActivity.class);
        startActivity(intent);
        finish();
    }
}
