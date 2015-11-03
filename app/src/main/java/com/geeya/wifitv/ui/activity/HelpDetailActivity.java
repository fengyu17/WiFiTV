package com.geeya.wifitv.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.geeya.wifitv.R;
import com.geeya.wifitv.utils.DisplayUtils;

public class HelpDetailActivity extends Activity{
	
	private LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_detail);
		initView();
		addView();
		
	}
	
	private void initView(){
		layout = (LinearLayout)findViewById(R.id.ll_help_detail_layout);
		TextView tvTilte = (TextView)findViewById(R.id.title);
		tvTilte.setText(R.string.user_help);
		LinearLayout llBack = (LinearLayout)findViewById(R.id.back);
		llBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		int titleId = getIntent().getExtras().getInt("title");
		TextView tvDetailTitle=(TextView)findViewById(R.id.help_detail_title);
		tvDetailTitle.setText(titleId);
	}
	
	private void addView(){
		int[] resid = getIntent().getExtras().getIntArray("QA");
		int length = resid.length / 2;
		for(int i = 0 ; i < length; i ++){
			LayoutParams questionParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			questionParams.leftMargin=DisplayUtils.dip2px(this, 9);
			questionParams.topMargin=DisplayUtils.dip2px(this, 10);
			TextView tvQuestion=new TextView(this);
			tvQuestion.setText(resid[i*2]);
			tvQuestion.setTextSize(16);
			tvQuestion.setTextColor(getResources().getColor(R.color.black));
			tvQuestion.setLayoutParams(questionParams);
			LayoutParams answerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			answerParams.leftMargin=DisplayUtils.dip2px(this, 9);
			answerParams.topMargin=DisplayUtils.dip2px(this, 5);
			TextView tvAnswer=new TextView(this);
		    tvAnswer.setText(resid[i*2 + 1]);
		    tvAnswer.setTextSize(16);
		    tvAnswer.setLayoutParams(answerParams);
		    layout.addView(tvQuestion);
		    layout.addView(tvAnswer);
		}
	}
}
