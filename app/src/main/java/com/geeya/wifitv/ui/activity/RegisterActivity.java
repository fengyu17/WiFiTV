package com.geeya.wifitv.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geeya.wifitv.R;
import com.geeya.wifitv.base.BaseActivity;
import com.geeya.wifitv.presenter.user.UserAction;
import com.geeya.wifitv.presenter.user.UserActionImpl;
import com.geeya.wifitv.ui.interf.IRegister;

public class RegisterActivity extends BaseActivity implements IRegister, OnClickListener, OnCheckedChangeListener, OnFocusChangeListener {

	private TextView tvErrInfo;
	private EditText etAccount;
	private EditText etName;
	private EditText etPassword;
	private EditText etRepeatPassword;
	private Button registButton;
	private CheckBox cbAgreement;
	private UserAction actionRegist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		initView();
		actionRegist = new UserActionImpl(this);
	}

	private void regist() {
		this.actionRegist.userRegist();
	}

	private void initView() {
		tvErrInfo = (TextView) findViewById(R.id.user_regist_errorinfo);
		etAccount = (EditText) findViewById(R.id.user_regist_account);
		etName = (EditText) findViewById(R.id.user_regist_name);
		etPassword = (EditText) findViewById(R.id.user_regist_password);
		etRepeatPassword = (EditText) findViewById(R.id.user_regist_repeatpassword);
		registButton = (Button) findViewById(R.id.user_regist_button);
		cbAgreement = (CheckBox) findViewById(R.id.cb_user_regist_agreement);
		cbAgreement.setText(Html.fromHtml("我已阅读并同意" + "<u> " + "《享看用户注册协议》" + "</u>"));
		cbAgreement.setOnCheckedChangeListener(this);
		registButton.setOnClickListener(this);
		etAccount.setOnFocusChangeListener(this);
		etName.setOnFocusChangeListener(this);
		etPassword.setOnFocusChangeListener(this);
		etRepeatPassword.setOnFocusChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_regist_button:
			regist();
			break;
		default:
			break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_regist_account:
			if (hasFocus)
				etAccount.setBackgroundResource(R.drawable.activity_regist_bg_frame_focused);
			else
				etAccount.setBackgroundResource(R.drawable.activity_regist_bg_frame_unfocused);
			break;
		case R.id.user_regist_name:
			if (hasFocus)
				etName.setBackgroundResource(R.drawable.activity_regist_bg_frame_focused);
			else
				etName.setBackgroundResource(R.drawable.activity_regist_bg_frame_unfocused);
			break;
		case R.id.user_regist_password:
			if (hasFocus)
				etPassword.setBackgroundResource(R.drawable.activity_regist_bg_frame_focused);
			else
				etPassword.setBackgroundResource(R.drawable.activity_regist_bg_frame_unfocused);
			break;
		case R.id.user_regist_repeatpassword:
			if (hasFocus)
				etRepeatPassword.setBackgroundResource(R.drawable.activity_regist_bg_frame_focused);
			else
				etRepeatPassword.setBackgroundResource(R.drawable.activity_regist_bg_frame_unfocused);
			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked)
			registButton.setEnabled(true);
		else
			registButton.setEnabled(false);
	}

	@Override
	public String getEtName() {
		return etName.getText().toString();
	}

	@Override
	public String getEtAccount() {
		return etAccount.getText().toString();
	}

	@Override
	public String getEtPassword() {
		return etPassword.getText().toString();
	}

	@Override
	public String getEtRepeatPassword() {
		return etRepeatPassword.getText().toString();
	}

	@Override
	public void showErrorInfo(String message) {
		tvErrInfo.setText(message);
		tvErrInfo.setVisibility(View.VISIBLE);
	}

	@Override
	public void showToast(String message) {
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	public void kill() {
		RegisterActivity.this.finish();
	}

}
