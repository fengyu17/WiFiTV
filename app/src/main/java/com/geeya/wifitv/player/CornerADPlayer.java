/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-9-29 上午10:42:56
 */
package com.geeya.wifitv.player;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.utils.Tools;
import com.geeya.wifitv.utils.VolleyUtil;
import com.geeya.wifitv.widget.MediaControlBar.OnPauseListener;
import com.geeya.wifitv.widget.OnClickDialog;
import com.geeya.wifitv.widget.ScrollForeverTextView;

/**
 * @author Administrator
 */
public class CornerADPlayer implements OnPauseListener {

    private Activity activity;
    private ImageView corner; // 挂角广告显示控件
    private ScrollForeverTextView subtitle; // 字幕广告显示控件
    private ImageView pause; // 暂停广告插件
    private ADInfo cornerADInfo, pauseADInfo, subtitleADInfo; // 挂角广告信息，暂停广告，字幕广告信息

    private MyThread myThread = null; // 广告显示线程
    private boolean startThread = true; // 默认线程开启
    private ADPlayerHandler handler = null;

    private static final int MSG_CORNER_VISIBLE = 0x00;
    private static final int MSG_CORNER_INVISIBLE = 0x01;
    private static final int MSG_SUBTITLE_VISIBLE = 0x02;
    private static final int MSG_SUBTITLE_INVISIBLE = 0x03;

    public CornerADPlayer(Activity activity) {
        this.activity = activity;
        initADView();
        handler = new ADPlayerHandler(this);
    }

    private void initADView() {
        corner = (ImageView) activity.findViewById(R.id.iv_corner);
        pause = (ImageView) activity.findViewById(R.id.iv_pause);
        subtitle = (ScrollForeverTextView) activity.findViewById(R.id.tv_subtitle);

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        corner.getLayoutParams().width = metrics.widthPixels / 5;
    }

    // 设置挂角广告
    public void setCornerADView(ArrayList<ADInfo> adInfoes) {
        cornerADInfo = adInfoes.get(0);
        if (cornerADInfo != null) {
            corner.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    OnClickDialog clickDialog = new OnClickDialog(cornerADInfo.getADID(), cornerADInfo.getADLink());
                    clickDialog.showDialog(activity);
                }
            });
            if (cornerADInfo.getADType().equals("image")) {
                ImageLoader imageLoader = VolleyUtil.getImageLoader();
                imageLoader.get(adInfoes.get(0).getADContent(), ImageLoader.getImageListener(corner, R.mipmap.ic_empty, R.mipmap.ic_empty));
                // 开启挂角广告和字幕广告开启线程
                if (myThread == null) {
                    myThread = new MyThread();
                    myThread.start();
                }
            }
        }

    }

    // 设置字幕广告
    public void setSubtitleADView(ArrayList<ADInfo> adInfoes) {
        subtitleADInfo = adInfoes.get(0);
        if (subtitleADInfo != null) {
            if (subtitleADInfo.getADType().equals("text")) {
                subtitle.setText(subtitleADInfo.getSubtitle());
                // 开启挂角广告和字幕广告开启线程
                if (myThread == null) {
                    myThread = new MyThread();
                    myThread.start();
                }
            }
        }

    }

    // 设置暂停广告
    public void setPauseADView(ArrayList<ADInfo> adInfoes) {
        pauseADInfo = adInfoes.get(0);
        if (pauseADInfo != null) {
            if (pauseADInfo.getADType().equals("image")) {
                ImageLoader imageLoader = VolleyUtil.getImageLoader();
                imageLoader.get(adInfoes.get(0).getADContent(), ImageLoader.getImageListener(pause, R.mipmap.ic_empty, R.mipmap.ic_empty));
                pause.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        OnClickDialog clickDialog = new OnClickDialog(subtitleADInfo.getADID(), subtitleADInfo.getADLink());
                        clickDialog.showDialog(activity);
                    }
                });
            }
        }
    }

    // 显示挂角广告
    public void showCorner() {
        corner.setVisibility(View.VISIBLE);
    }

    // 隐藏挂角广告
    public void hideCorner() {
        if (corner.isShown()) {
            corner.setVisibility(View.GONE);
        }
    }

    // 显示字幕广告
    public void showSubtitle() {
        subtitle.setVisibility(View.VISIBLE);
    }

    // 隐藏字幕广告
    public void hideSubtitle() {
        if (subtitle.isShown()) {
            subtitle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPauseShow() {
        // TODO Auto-generated method stub
        if (pauseADInfo != null) {
            pause.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPauseHide() {
        // TODO Auto-generated method stub
        if (pause.isShown()) {
            pause.setVisibility(View.GONE);
        }
    }

    public void restart() {
        startThread = true;
        myThread = new MyThread();
        myThread.start();
    }

    public void pause() {
        startThread = false;
        myThread = null;
        onPauseHide();
    }

    public void stop() {
        startThread = false;
        myThread = null;
    }

    // 定时显示挂角广告的线程
    class MyThread extends Thread {

        public void run() {
            boolean flag = true;
            while (startThread) {
                try {
                    if (flag) {
                        if (cornerADInfo != null) {
                            handler.sendEmptyMessage(MSG_CORNER_VISIBLE);
                            Thread.sleep(3000);
                            handler.sendEmptyMessage(MSG_CORNER_INVISIBLE);
                            long cycleTime = Tools.randomNum(1, 5) * 1000;
                            Thread.sleep(cycleTime);
                        }
                        flag = false;
                    } else {
                        if (subtitleADInfo != null) {
                            handler.sendEmptyMessage(MSG_SUBTITLE_VISIBLE);
                            Thread.sleep(3000);
                            handler.sendEmptyMessage(MSG_SUBTITLE_INVISIBLE);
                            long cycleTime = Tools.randomNum(1, 5) * 1000;
                            Thread.sleep(cycleTime);
                        }
                        flag = true;
                    }

                } catch (InterruptedException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    startThread = false;
                    break;
                }
            }
        }
    }

    public static class ADPlayerHandler extends Handler {

        WeakReference<CornerADPlayer> activityReference;

        public ADPlayerHandler(CornerADPlayer cornerADPlayer) {
            activityReference = new WeakReference<CornerADPlayer>(cornerADPlayer);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            final CornerADPlayer cornerADPlayer = (CornerADPlayer) activityReference.get();
            switch (msg.what) {
                case MSG_CORNER_VISIBLE:
                    cornerADPlayer.showCorner();
                    break;
                case MSG_CORNER_INVISIBLE:
                    cornerADPlayer.hideCorner();
                    break;
                case MSG_SUBTITLE_VISIBLE:
                    cornerADPlayer.showSubtitle();
                    break;
                case MSG_SUBTITLE_INVISIBLE:
                    cornerADPlayer.hideSubtitle();
                    break;
                default:
                    break;
            }
        }
    }

}
