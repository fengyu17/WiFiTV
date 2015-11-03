package com.geeya.wifitv.ui.activity;

import io.vov.vitamio.LibsChecker;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.geeya.wifitv.R;
import com.geeya.wifitv.base.BaseActivity;
import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.bean.ChannelInfo;
import com.geeya.wifitv.player.CornerADPlayer;
import com.geeya.wifitv.player.StartADPlayer;
import com.geeya.wifitv.player.VideoPlayer;
import com.geeya.wifitv.presenter.player.PlayerAction;
import com.geeya.wifitv.presenter.player.PlayerActionImpl;
import com.geeya.wifitv.ui.interf.ILivePlay;

public class LivePlayActivity extends BaseActivity implements ILivePlay {

    private PlayerAction playerAction;

    private VideoPlayer livePlayer = null;
    private GestureDetector gestureDetector = null;
    private long currentPosition;

    private StartADPlayer startADPlayer;
    private CornerADPlayer cornerADPlayer;

    private ArrayList<ChannelInfo> channelInfoes; // 节目信息
    private int position; // 当前播放的节目

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }

        setContentView(R.layout.player);
        Bundle bundle = getIntent().getExtras();
        channelInfoes = (ArrayList<ChannelInfo>) bundle.getSerializable("ChannelInfo");
        position = bundle.getInt("Position");

        playerAction = new PlayerActionImpl(this);
        playerAction.livePlayerAction();

    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (startADPlayer != null) {
            startADPlayer.stopADPlayer();
        }
        if (livePlayer != null) {
            livePlayer.stop();
            livePlayer = null;
            cornerADPlayer.stop();
        }
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (gestureDetector != null) {
            if (gestureDetector.onTouchEvent(event)) {
                return true;
            }

            // 处理手势结束
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    livePlayer.endGesture();
                    break;
                default:
                    break;
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void initLivePlay() {
        // TODO Auto-generated method stub
        livePlayer = new VideoPlayer(mContext, this);
        livePlayer.setChannelSources(channelInfoes, position);
        gestureDetector = livePlayer.getGesture();
        livePlayer.start();
        cornerADPlayer = new CornerADPlayer(this);
    }

    @Override
    public void initStartAD(ArrayList<ADInfo> adInfoes) {
        // TODO Auto-generated method stub
        currentPosition = livePlayer.pause();
        cornerADPlayer.pause();
        startADPlayer = new StartADPlayer(LivePlayActivity.this, this, adInfoes);
        startADPlayer.startADPalyer();
        startADPlayer.setOnADCompletionListener(new StartADPlayer.OnADCompletionListener() {
            @Override
            public void onADCompletion() {
                // TODO Auto-generated method stub
                if (livePlayer != null) {
                    livePlayer.restart(currentPosition);
                    cornerADPlayer.restart();
                }
                startADPlayer = null;
            }
        });
    }

    @Override
    public void initCornerAD(ArrayList<ADInfo> adInfoes) {
        // TODO Auto-generated method stub
        cornerADPlayer.setCornerADView(adInfoes);
    }

    @Override
    public void initSubtitleAD(ArrayList<ADInfo> adInfoes) {
        // TODO Auto-generated method stub
        cornerADPlayer.setSubtitleADView(adInfoes);
    }


}
