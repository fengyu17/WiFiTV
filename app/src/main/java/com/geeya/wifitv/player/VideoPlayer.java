/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-7-21 下午2:23:35
 */
package com.geeya.wifitv.player;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.VideoView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.ChannelInfo;
import com.geeya.wifitv.bean.ProgramSource;
import com.geeya.wifitv.ui.activity.LivePlayActivity;
import com.geeya.wifitv.ui.activity.ProgramPlayActivity;
import com.geeya.wifitv.widget.MediaControlBar;
import com.geeya.wifitv.widget.MediaControlBar.OnPauseListener;

/**
 * @author Administrator
 */
public class VideoPlayer implements OnInfoListener, OnBufferingUpdateListener, OnCompletionListener, OnTouchListener {

    private Context context;
    private Activity activity;
    private VideoPlayerHandler handler;

    private int index;
    private ArrayList<ProgramSource> programSources = null; // 点播列表
    private ArrayList<ChannelInfo> channelInfoes = null; // 直播列表

    private VideoView videoView; // vitamio提供的videoview
    private RelativeLayout mediaTitleBar;
    private ImageView back;
    private TextView title, change;
    private ListView list;
    private ProgressBar progressBar;
    private TextView downloadRateView, loadRateView;
    private MediaControlBar mediaControlBar = null;
    private GestureDetector mGestureDetector;
    private View volumeBrightnessLayout;
    private ImageView operationBg, operationPercent;
    private AudioManager audioManager;
    // 当前缩放模式
    private int mLayout = VideoView.VIDEO_LAYOUT_ZOOM;
    // 最大声音
    private int maxVolume;
    // 当前声音
    private int volume = -1;
    // 当前亮度
    private float brightness = -1f;

    private static final int MSG_VIEW_GONE = 0x00;
    private static final int MSG_TITLE_VISIBLE = 0x01;
    private static final int MSG_TITLE_INVISIBLE = 0x02;
    private static final int MSG_LIST_VISIBLE = 0x03;
    private static final int MSG_LIST_INVISIBLE = 0x04;

    /**
     * @param context
     */
    public VideoPlayer(Context context, Activity activity) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.activity = activity;
        handler = new VideoPlayerHandler(this);
        initVideoView();
    }

    private void initVideoView() {

        videoView = (VideoView) activity.findViewById(R.id.vv_player);
        progressBar = (ProgressBar) activity.findViewById(R.id.probar);
        downloadRateView = (TextView) activity.findViewById(R.id.tv_downloadrate);
        loadRateView = (TextView) activity.findViewById(R.id.tv_loadrate);

        initTitleBar(); // 初始化标题啦

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        volumeBrightnessLayout = activity.findViewById(R.id.operation_volume_brightness);
        operationBg = (ImageView) volumeBrightnessLayout.findViewById(R.id.operation_bg);
        operationPercent = (ImageView) volumeBrightnessLayout.findViewById(R.id.operation_percent);


        videoView.requestFocus();
        videoView.setOnInfoListener(this);
        videoView.setOnBufferingUpdateListener(this);
        videoView.setOnCompletionListener(this);
        videoView.setVideoLayout(mLayout, 0);
        mGestureDetector = new GestureDetector(activity, new MyGestureListener());

    }

    private void initTitleBar() {
        mediaTitleBar = (RelativeLayout) activity.findViewById(R.id.rl_titlebar);
        list = (ListView) activity.findViewById(R.id.lv_list);
        back = (ImageView) mediaTitleBar.findViewById(R.id.iv_back);
        title = (TextView) mediaTitleBar.findViewById(R.id.tv_name);
        change = (TextView) mediaTitleBar.findViewById(R.id.tv_change);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                activity.finish();
            }
        });
    }

    public void setProgramSources(ArrayList<ProgramSource> sources, final String programID, int index) {
        this.programSources = sources;
        this.index = index;
        title.setText(programSources.get(index).getSourceName() + "  第 " + (index + 1) + " 集");
        change.setText("剧集");
        if (programSources.size() != 1) {
            change.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    // 显示列表
                    handler.sendEmptyMessage(MSG_LIST_VISIBLE);
                    delayHideView();
                }
            });
        }

        String[] strs = new String[programSources.size()];
        for (int i = 0; i < programSources.size(); i++) {
            strs[i] = "第 " + (i + 1) + " 集";
        }
        list.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_expandable_list_item_1, strs));
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // 跳转动作
                Intent intent = new Intent(activity, ProgramPlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ProgramSource", programSources);
                bundle.putInt("Position", position);
                bundle.putString("ProgramID", programID);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        list.setOnTouchListener(this); // 为点播列表设置点击监听，用于实现延迟消失功能
    }

    public void setChannelSources(ArrayList<ChannelInfo> channelList, int index) {
        this.channelInfoes = channelList;
        this.index = index;
        title.setText("" + channelInfoes.get(index).getChannelName());
        change.setText("换台");
        if (channelInfoes.size() != 1) {
            change.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    // 显示列表
                    handler.sendEmptyMessage(MSG_LIST_VISIBLE);
                    delayHideView();
                }
            });
        }

        String[] strs = new String[channelInfoes.size()];
        for (int i = 0; i < channelInfoes.size(); i++) {
            strs[i] = channelInfoes.get(i).getChannelName();
        }
        list.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_expandable_list_item_1, strs));
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // 跳转动作
                Intent intent = new Intent(activity, LivePlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ChannelInfo", channelInfoes);
                bundle.putInt("Position", position);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
        list.setOnTouchListener(this); // 为点播列表设置点击监听，用于实现延迟消失功能
    }

    public void setMediaControllerBar(OnPauseListener onPauseListener) {
        mediaControlBar = new MediaControlBar(context, videoView);
        mediaControlBar.setOnPauseListener(onPauseListener);
        videoView.setMediaController(mediaControlBar);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        delayHideView();
        return false;
    }

    public void start() {
        if (programSources != null) {
            videoView.setVideoPath(programSources.get(index).getUrl());
        } else {
            videoView.setVideoPath(channelInfoes.get(index).getMediaAddr());
        }
        videoView.start();
        if (mediaControlBar != null) {
            mediaControlBar.hide();
        }
    }

    public long pause() {
        long position = videoView.getCurrentPosition();
        videoView.pause();
        videoView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);
        downloadRateView.setVisibility(View.GONE);
        loadRateView.setVisibility(View.GONE);
        return position;
    }

    public void restart(long currentPosition) {
        if (mediaControlBar != null) {
            mediaControlBar.hide();
        }
        videoView.setVisibility(View.VISIBLE);
        videoView.resume();
        videoView.seekTo(currentPosition);
        if (videoView.isPlaying()) {
            progressBar.setVisibility(View.VISIBLE);
            downloadRateView.setVisibility(View.VISIBLE);
            loadRateView.setVisibility(View.VISIBLE);
        }
    }

    public void stop() {
        videoView.stopPlayback();
    }

    // 显示标题栏
    public void showTitle() {
        mediaTitleBar.setVisibility(View.VISIBLE);
        if (mediaControlBar != null) {
            mediaControlBar.show();
        }
    }

    // 隐藏标题栏
    public void hideTitle() {
        mediaTitleBar.setVisibility(View.GONE);
        if (mediaControlBar != null) {
            mediaControlBar.hide();
        }
    }

    // 显示选集列表
    public void showList() {
        list.setVisibility(View.VISIBLE);
    }

    // 隐藏选集列表
    public void hideList() {
        list.setVisibility(View.GONE);
    }

    // 隐藏声音亮度控制模块
    public void hideView() {
        volumeBrightnessLayout.setVisibility(View.GONE);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // TODO Auto-generated method stub
        activity.finish();
        activity = null;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        // TODO Auto-generated method stub
        loadRateView.setText(percent + "%");
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        // TODO Auto-generated method stub
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    progressBar.setVisibility(View.VISIBLE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                videoView.start();
                progressBar.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText(" " + extra + "kb/s" + " ");
                break;
            default:
                break;
        }
        return false;
    }

    public void delayHideView() {
        handler.removeMessages(MSG_LIST_INVISIBLE);
        handler.sendEmptyMessage(MSG_LIST_VISIBLE);
        handler.sendEmptyMessageDelayed(MSG_LIST_INVISIBLE, 3000);
        handler.removeMessages(MSG_TITLE_INVISIBLE);
        handler.sendEmptyMessageDelayed(MSG_TITLE_INVISIBLE, 3000);
    }

    // 获取当前手势
    public GestureDetector getGesture() {
        return mGestureDetector;
    }

    // 手势结束调用
    public void endGesture() {
        volume = -1;
        brightness = -1f;

        if (list.isShown()) {
            handler.removeMessages(MSG_LIST_INVISIBLE);
            handler.sendEmptyMessageDelayed(MSG_LIST_INVISIBLE, 3000);
        }
        handler.removeMessages(MSG_TITLE_INVISIBLE);
        handler.sendEmptyMessageDelayed(MSG_TITLE_INVISIBLE, 3000);
        handler.removeMessages(MSG_VIEW_GONE);
        handler.sendEmptyMessageDelayed(MSG_VIEW_GONE, 100);
    }

    private class MyGestureListener extends SimpleOnGestureListener {

        // 按下
        @Override
        public boolean onDown(MotionEvent e) {
            // TODO Auto-generated method stub
            handler.sendEmptyMessage(MSG_TITLE_VISIBLE);
            return super.onDown(e);
        }

        // 双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // TODO Auto-generated method stub
            if (mediaControlBar != null) {
                if (mLayout == VideoView.VIDEO_LAYOUT_ZOOM) {
                    mLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
                } else {
                    ++mLayout;
                }
                if (videoView != null) {
                    videoView.setVideoLayout(mLayout, 0);
                }
            }
            return true;
        }

        // 滑动
        @SuppressLint("NewApi")
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // TODO Auto-generated method stub
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();
            Display disp = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point size = new Point();
            disp.getSize(size);
            int windowWidth = size.x;
            int windowHeight = size.y;

            if (mOldX > windowWidth * 4.0 / 5)// 右边滑动
                onVolumeSlide((mOldY - y) / windowHeight);
            else if (mOldX < windowWidth / 5.0)// 左边滑动
                onBrightnessSlide((mOldY - y) / windowHeight);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        if (volume == -1) {
            volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (volume < 0)
                volume = 0;

            // 显示
            operationBg.setImageResource(R.mipmap.video_volumn_bg);
            volumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        int index = (int) (percent * maxVolume) + volume;
        if (index > maxVolume)
            index = maxVolume;
        else if (index < 0)
            index = 0;

        // 变更声音
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

        // 变更进度条
        ViewGroup.LayoutParams lp = operationPercent.getLayoutParams();
        lp.width = volumeBrightnessLayout.findViewById(R.id.operation_full).getLayoutParams().width * index / maxVolume;
        operationPercent.setLayoutParams(lp);
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (brightness < 0) {
            brightness = activity.getWindow().getAttributes().screenBrightness;
            if (brightness <= 0.00f)
                brightness = 0.50f;
            if (brightness < 0.01f)
                brightness = 0.01f;

            // 显示
            operationBg.setImageResource(R.mipmap.video_brightness_bg);
            volumeBrightnessLayout.setVisibility(View.VISIBLE);
        }
        WindowManager.LayoutParams lpa = activity.getWindow().getAttributes();
        lpa.screenBrightness = brightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        activity.getWindow().setAttributes(lpa);

        ViewGroup.LayoutParams lp = operationPercent.getLayoutParams();
        lp.width = (int) (volumeBrightnessLayout.findViewById(R.id.operation_full).getLayoutParams().width * lpa.screenBrightness);
        operationPercent.setLayoutParams(lp);
    }

    public static class VideoPlayerHandler extends Handler {

        WeakReference<VideoPlayer> activityReference;

        public VideoPlayerHandler(VideoPlayer videoPlayer) {
            activityReference = new WeakReference<VideoPlayer>(videoPlayer);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            final VideoPlayer videoPlayer = (VideoPlayer) activityReference.get();
            switch (msg.what) {
                case MSG_VIEW_GONE:
                    videoPlayer.hideView();
                    break;
                case MSG_TITLE_VISIBLE:
                    videoPlayer.showTitle();
                    break;
                case MSG_TITLE_INVISIBLE:
                    videoPlayer.hideTitle();
                    break;
                case MSG_LIST_VISIBLE:
                    videoPlayer.showList();
                    break;
                case MSG_LIST_INVISIBLE:
                    videoPlayer.hideList();
                    break;
                default:
                    break;
            }
        }

    }


}
