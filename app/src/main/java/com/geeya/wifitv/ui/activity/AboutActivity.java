package com.geeya.wifitv.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geeya.wifitv.R;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.base.BaseActivity;
import com.geeya.wifitv.bean.ApkInfo;
import com.geeya.wifitv.presenter.AppAction;
import com.geeya.wifitv.presenter.AppActionImpl;
import com.geeya.wifitv.ui.interf.IUpdate;

public class AboutActivity extends Activity implements IUpdate, OnClickListener {

    private AppAction actionUpdate;
    private Button btnUpdate;
    private TextView tvCooperation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        actionUpdate = new AppActionImpl(this);
        initView();
    }

    private void initView() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("关于");
        btnUpdate = (Button) findViewById(R.id.bt_about_update);
        tvCooperation = (TextView) findViewById(R.id.tv_about_cooperation);
//        TextView title = (TextView) findViewById(R.id.title);
//        title.setText(R.string.user_about);
//        LinearLayout ll_back = (LinearLayout) findViewById(R.id.back);
//        ll_back.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        tvCooperation.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_title:
                onBackPressed();
                finish();
                break;
            case android.R.id.home:
                onBackPressed();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_about_update:
                checkUpdate();
                break;
            case R.id.tv_about_cooperation:
                Intent intent = new Intent(AboutActivity.this, JoinActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    private void checkUpdate() {
        actionUpdate.update();
    }

    @Override
    public void createDialog(final ApkInfo apkInfo) {
        // TODO Auto-generated method stub
        if (getApplicationContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
            builder.setTitle(R.string.checkupdate);
            builder.setMessage(apkInfo.getDescrible());
            builder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    String url = WiFiTVApplication.getInstance().getAreaInfo().getNetElementIP();
                    if (url != null)
                        url += apkInfo.getUpdateUrl();
                    actionUpdate.download(url);
                }
            });
            builder.setNegativeButton(R.string.update_after, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
