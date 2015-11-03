package com.geeya.wifitv.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geeya.wifitv.R;

public class FeedbackActivity extends Activity implements OnClickListener{

	private EditText etAdvice;
	private Button btnSend;
	private TextView tvCount;
	private LinearLayout llBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		initView();
	}
	
	private void initView(){
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(R.string.user_feedback);
		llBack = (LinearLayout)findViewById(R.id.back);
		btnSend = (Button)findViewById(R.id.feedback_send);
		tvCount = (TextView)findViewById(R.id.feedback_count);
		etAdvice = (EditText)findViewById(R.id.feedback_advice);
		llBack.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		etAdvice.addTextChangedListener(textWatcher);
	}
	
	private TextWatcher textWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			int length = etAdvice.getText().toString().length();
			String str = length + "" + "/150";
			tvCount.setText(str);
			if( length < 1 || length > 150){
				btnSend.setEnabled(false);					
			}else {
				if(!btnSend.isEnabled())
					btnSend.setEnabled(true);
			}
		}
	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.feedback_send:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		etAdvice.removeTextChangedListener(textWatcher);
	}
}
