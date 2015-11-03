package com.geeya.wifitv.widget;

import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class MediaControlBar extends MediaController {

	private Context context;
	private VideoView videoView;
	private OnPauseListener onPauseListener;

	/**
	 * @param context
	 */
	public MediaControlBar(Context context, VideoView videoView) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.videoView = videoView;
	}

	public void setOnPauseListener(OnPauseListener onPauseListener) {
		this.onPauseListener = onPauseListener;
	}

	@Override
	protected View makeControllerView() {
		// TODO Auto-generated method stub
		return ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getResources().getIdentifier("videoview_mediacontroller", "layout", context.getPackageName()), this);
	}

	@Override
	public void doPauseResume() {
		super.doPauseResume();
		if (videoView.isPlaying()) {
			onPauseListener.onPauseHide();
		} else {
			onPauseListener.onPauseShow();
		}
	}

	/**
	 * 暂停监听
	 * 
	 * @author Administrator
	 */
	public interface OnPauseListener {
		
		// 暂停显示
		public void onPauseShow();

		// 暂停隐藏
		public void onPauseHide();
	}

}
