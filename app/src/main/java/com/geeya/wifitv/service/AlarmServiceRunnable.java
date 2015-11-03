package com.geeya.wifitv.service;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmServiceRunnable implements Runnable {

	private Context context;
	private String ADID;

	public AlarmServiceRunnable(String ADID, Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.ADID = ADID;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Intent alarmIntent = new Intent(context, AlarmReceiver.class);
		alarmIntent.setAction("action");
		alarmIntent.putExtra("ADID", ADID);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 5);
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
	}
}
