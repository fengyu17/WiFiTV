/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-8-6 下午4:22:41
 * 
 */
package com.geeya.wifitv.player;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.utils.VolleyUtil;
import com.geeya.wifitv.widget.OnClickDialog;

/**
 * @author Administrator
 * 
 */
public class StartADPlayer implements OnPreparedListener, OnCompletionListener, OnBufferingUpdateListener, OnVideoSizeChangedListener, SurfaceHolder.Callback {

	private Context context;
	private Activity activity;
	private ArrayList<ADInfo> adInfoes;
	private ADInfo adInfo; // 单个广告

	private MediaPlayer mediaPlayer;
	private SurfaceView surfaceView; // 视频广告播放控件
	private SurfaceHolder surfaceHolder;

	private ImageView imageView; // 图片广告播放控件

	private FrameLayout frameLayout; // 广告倒计时控件
	private TextView tvTimeCount; // 倒计时文本
	private boolean isContinue = true; // 是否结束更新倒计时
	private boolean isUpdate = false; // 是否倒计时更新
	private boolean isFirstBuffer = false; // 判断缓冲区缓冲是否是首次缓冲

	private String adContent = null; // 广告地址
	private int adCountDuration = 0; // 广告持续时间
	private boolean isVideoSizeKnown = false;
	private boolean isVideoReadyToBePlayed = false;

	private int index = 0; // 当前广告索引，第几个。
	private int length = 0; // 广告总个数

	private OnADCompletionListener onADCompletionListener; // 广告结束监听
	private MyHandler myHandler = new MyHandler(this);

	public static final int MSG_HIDE_VIDEO = 1;
	public static final int MSG_HIDE_IMAGE = 2;
	public static final int MSG_UPDATE_TIME = 3;

	public StartADPlayer(Context context, Activity activity, ArrayList<ADInfo> adInfoes) {
		this.context = context;
		this.activity = activity;
		this.adInfoes = adInfoes;
		this.length = adInfoes.size();
	}

	public void setOnADCompletionListener(OnADCompletionListener onADCompletionListener) {
		this.onADCompletionListener = onADCompletionListener;
	}

	// 开始广告播放
	public void startADPalyer() {
		frameLayout = (FrameLayout) activity.findViewById(R.id.fl_timecount);
		tvTimeCount = (TextView) activity.findViewById(R.id.tv_timecount);
		adCountDuration = countADDuration();
		frameLayout.setVisibility(View.VISIBLE);
		new Thread(new MyThread()).start();
		if (adInfoes == null) {
			return;
		} else {
			analyseADInfo(0);
		}

	}

	// 结束广告播放
	public void stopADPlayer() {
		releaseMediaPlayer();
		isContinue = false;
	}

	/**
	 * 计算开播广告所有的时长
	 * 
	 * @return
	 * 
	 *         Created by Administrator Created on 2015-9-8 下午3:33:55
	 */
	private int countADDuration() {
		int count = 0;
		for (int i = 0; i < adInfoes.size(); i++) {
			count += Integer.parseInt(adInfoes.get(i).getADDuration());
		}
		return --count;
	}

	/**
	 * 分析广告，边分析边播放
	 * 
	 * Created by Administrator Created on 2015-8-7 上午11:23:24
	 */
	public void analyseADInfo(int index) {
		if (index < length) {
			adInfo = adInfoes.get(index);
			adContent = adInfo.getADContent();
			String type = adInfo.getADType();
			if (type.equals("video")) {
				// 开始视频广告的播放
				isUpdate = true;
				startVideoAD();
			} else if (type.equals("image")) {
				// 开始图片广告的播放
				int adDuration = Integer.parseInt(adInfo.getADDuration());
				startImageAD(adDuration);
			} else {
				// 出错
				return;
			}
		} else {
			// 结束广告播放
			if (surfaceView != null) {
				surfaceView.setVisibility(View.GONE);
			}
			if (imageView != null) {
				imageView.setVisibility(View.GONE);
			}
			if (frameLayout.isShown()) {
				frameLayout.setVisibility(View.GONE);
			}
			isContinue = false;
			onADCompletionListener.onADCompletion();
		}
	}

	private void startVideoAD() {
		surfaceView = (SurfaceView) activity.findViewById(R.id.sv_start);
		surfaceView.setVisibility(View.VISIBLE);
		surfaceView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				OnClickDialog clickDialog = new OnClickDialog(adInfo.getADID(), adInfo.getADLink());
				clickDialog.showDialog(context);
			}
		});
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setFormat(PixelFormat.RGBA_8888);
	}

	private void startImageAD(int adDuration) {
		imageView = (ImageView) activity.findViewById(R.id.iv_start);
		imageView.setVisibility(View.VISIBLE);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				OnClickDialog clickDialog = new OnClickDialog(adInfo.getADID(), adInfo.getADLink());
				clickDialog.showDialog(context);
			}
		});
		ImageLoader imageLoader = VolleyUtil.getImageLoader();
		imageLoader.get(adContent, ImageLoader.getImageListener(imageView, R.mipmap.ic_empty, R.mipmap.ic_empty));
		isUpdate = true;
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				myHandler.obtainMessage(MSG_HIDE_IMAGE).sendToTarget();
				isUpdate = false;
			}

		};
		Timer timer = new Timer();
		timer.schedule(task, adDuration * 1000);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		doCleanUp();
		try {

			if (adContent == null) {
				return;
			}

			mediaPlayer = new MediaPlayer(context);
			mediaPlayer.setDataSource(adContent);
			mediaPlayer.setDisplay(surfaceHolder);
			mediaPlayer.prepareAsync();
			mediaPlayer.setBufferSize(512 * 1024);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
			mediaPlayer.setOnBufferingUpdateListener(this);
			activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub
		if (width == 0 || height == 0) {
			return;
		}
		isVideoSizeKnown = true;
		if (isVideoSizeKnown && isVideoReadyToBePlayed) {
			startVideoPlayback();
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		isVideoReadyToBePlayed = true;
		if (isVideoReadyToBePlayed && isVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		releaseMediaPlayer();
		myHandler.obtainMessage(MSG_HIDE_VIDEO).sendToTarget();
		isUpdate = false;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
//		if (percent == 0) {
//			isUpdate = false;
//			isFirstBuffer = true;
//		} else {
//			if (isFirstBuffer) {
//				isUpdate = true;
//				isFirstBuffer = false;
//			}
//		}
	}

	private void doCleanUp() {
		isVideoReadyToBePlayed = false;
		isVideoSizeKnown = false;
	}

	private void startVideoPlayback() {
		mediaPlayer.start();
	}

	public void releaseMediaPlayer() {
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		doCleanUp();
	}

	public int getIndex() {
		return ++index;
	}

	static class MyHandler extends Handler {

		WeakReference<StartADPlayer> weakReference;

		MyHandler(StartADPlayer adPlayer) {
			weakReference = new WeakReference<StartADPlayer>(adPlayer);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			final StartADPlayer adPlayer = weakReference.get();
			if (adPlayer == null) {
				return;
			}
			switch (msg.what) {
			case MSG_HIDE_VIDEO:
				adPlayer.surfaceView.setVisibility(View.GONE);
				adPlayer.analyseADInfo(adPlayer.getIndex());
				break;
			case MSG_HIDE_IMAGE:
				adPlayer.imageView.setVisibility(View.GONE);
				adPlayer.analyseADInfo(adPlayer.getIndex());
				break;
			case MSG_UPDATE_TIME:
				if(adPlayer.mediaPlayer != null && adPlayer.mediaPlayer.isPlaying()){
					adPlayer.adCountDuration = (int)((adPlayer.mediaPlayer.getDuration() - adPlayer.mediaPlayer.getCurrentPosition())/1000 + 0.5);
					adPlayer.tvTimeCount.setText(adPlayer.adCountDuration+"");
				}else if (adPlayer.mediaPlayer == null) {
					adPlayer.tvTimeCount.setText(adPlayer.adCountDuration +"");
				}
				break;
			default:
				break;
			}
		}

	}

	private class MyThread implements Runnable {

		@Override
		public void run() {
			while (isContinue) {

				try {
					if (isUpdate) {
						if (myHandler.hasMessages(StartADPlayer.MSG_UPDATE_TIME)) {
							myHandler.removeMessages(StartADPlayer.MSG_UPDATE_TIME);
						}
						myHandler.sendEmptyMessage(StartADPlayer.MSG_UPDATE_TIME);
						if ((--adCountDuration) <= 0) {
							isUpdate = false;
						}
					}
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

	}

	public interface OnADCompletionListener {
		public void onADCompletion();
	}

}
