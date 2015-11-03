package com.geeya.wifitv.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geeya.wifitv.R;

public class HelpActivity extends Activity implements OnClickListener{
	
	private FrameLayout flAccount;
	private FrameLayout flAlipay;
	private FrameLayout flVideo;
	private FrameLayout flDownload;
	private FrameLayout flCooperation;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		initTitleBar();
		initView();
		setListener();
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.fl_help_account:
			int[] resId = new int[]{R.string.help_account_detail_lost_q, 
				R.string.help_account_detail_lost_a} ;
			int detailTitle = R.string.help_account;
			bundle.putIntArray("QA", resId);
			bundle.putInt("title", detailTitle);
			intent.setClass(this, HelpDetailActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.fl_help_alipay:
			break;
		case R.id.fl_help_video:
			break;
		case R.id.fl_help_download:
			break;
		case R.id.fl_help_cooperation:
			break;
		default:
			break;
		}
	}
	
	private void initTitleBar(){
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(R.string.user_help);
		LinearLayout ll_back = (LinearLayout)findViewById(R.id.back);
		ll_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void initView(){		
		flAccount = (FrameLayout)findViewById(R.id.fl_help_account);
		flAlipay = (FrameLayout)findViewById(R.id.fl_help_alipay);
		flVideo = (FrameLayout)findViewById(R.id.fl_help_video);
		flDownload = (FrameLayout)findViewById(R.id.fl_help_download);
		flCooperation = (FrameLayout)findViewById(R.id.fl_help_cooperation);
	}
	
	private void setListener(){
		flAccount.setOnClickListener(this);
		flAlipay.setOnClickListener(this);
		flVideo.setOnClickListener(this);
		flDownload.setOnClickListener(this);
		flCooperation.setOnClickListener(this);
	}
	
}
