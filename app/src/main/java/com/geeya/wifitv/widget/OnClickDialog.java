/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-8-24 下午3:24:55
 * 
 */
package com.geeya.wifitv.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import com.geeya.wifitv.service.AlarmServiceRunnable;

/**
 * @author Administrator
 * 
 */
public class OnClickDialog {

	private String ADID;
	private String ADLink;

	public OnClickDialog(String ADID, String ADLink) {
		// TODO Auto-generated constructor stub
		this.ADID = ADID;
		this.ADLink = ADLink;
	}

	public void showDialog(final Context context) {
		if (context != null) {
			Dialog alertDialog = new AlertDialog.Builder(context).setTitle("亲，点击打开更有惊喜！").setPositiveButton("打开", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					// 用户点击行为数据
					Uri uri = Uri.parse(ADLink);
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					context.startActivity(intent);
					// 开始计时
					AlarmServiceRunnable alarmServiceRunnable = new AlarmServiceRunnable(ADID, context);
					alarmServiceRunnable.run();
				}
			}).setNegativeButton("离开", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			}).create();
			alertDialog.show();
		}
	}
}
