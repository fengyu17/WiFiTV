package com.geeya.wifitv.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.geeya.wifitv.R;
import com.geeya.wifitv.base.BaseActivity;
import com.geeya.wifitv.presenter.AppAction;
import com.geeya.wifitv.presenter.AppActionImpl;
import com.geeya.wifitv.ui.interf.IAddWlan;

public class AddWlan extends BaseActivity implements IAddWlan {

	private final String[] spinnerPadding = { "无", "WEP", "WPA/WPA2 PSK" };
	private EditText etWifiSSID;
	private Spinner spCipherType;
	private EditText etWifiPasswd;
	private int cipherType = 0;
	private ArrayAdapter<String> spinnerAdapter;
	private Button btConfirm;
	private AppAction actionConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addwifi);
		actionConnection = new AppActionImpl(this, this);
		initViewId();
		setSpinnerAdapter();
		setClickListener();
	}

	private void initViewId() {
		etWifiSSID = (EditText) findViewById(R.id.et_addwifi_ssid);
		etWifiPasswd = (EditText) findViewById(R.id.et_addwifi_passwd);
		spCipherType = (Spinner) findViewById(R.id.sp_addwifi_passwd_type);
		btConfirm = (Button) findViewById(R.id.bt_addwifi_connect);
	}

	private void setSpinnerAdapter() {

		spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerPadding);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCipherType.setAdapter(spinnerAdapter);

	}

	/**
	 * set listener when click corresponding controller including button and
	 * spinner
	 */
	private void setClickListener() {

		spCipherType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				cipherType = arg2;
				if (0 != cipherType) {
					etWifiPasswd.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				cipherType = 0;
			}
		});

		btConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				connetct();
			}
		});
	}

	@Override
	public String getSsid() {
		// TODO Auto-generated method stub
		return etWifiSSID.getText().toString();
	}

	@Override
	public String getPassWd() {
		// TODO Auto-generated method stub
		return etWifiPasswd.getText().toString();
	}

	@Override
	public int getcipherType() {
		// TODO Auto-generated method stub
		return cipherType;
	}

	@Override
	public void connetct() {
		// TODO Auto-generated method stub
		this.actionConnection.wifiConnection();
	}

}
