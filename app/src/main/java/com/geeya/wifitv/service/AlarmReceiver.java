package com.geeya.wifitv.service;


import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String ADID = intent.getStringExtra("ADID");
        // TODO Auto-generated method stub
        if (intent.getAction().equals("action")) {
            if (judgeTopActivity(context)) {
            }
        }
    }

    /**
     * 判断顶层activity是不是享看APP，如果是，返回false，如果不是，返回true
     *
     * @param context
     * @return Created by Administrator
     * Created on 2015-8-24 下午7:59:29
     */
    private boolean judgeTopActivity(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        String topActivity;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            topActivity = getTopActivity(activityManager);
        } else {
            topActivity = getTopActivityCompat(activityManager);
        }
        if (topActivity != null) {
            int start = topActivity.indexOf("/");
            String str = topActivity.substring(start + 1);
            if (str.substring(0, 16).equals("com.geeya.wifitv")) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }


    }

    private String getTopActivity(ActivityManager activityManager) {
        List<RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                String topActivity = processInfo.processName;
                return topActivity;
            }
        }
        return null;
    }

    private String getTopActivityCompat(ActivityManager activityManager) {

        @SuppressWarnings("deprecation")
        List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            return (runningTaskInfos.get(0).topActivity).toString();

        } else {
            return null;
        }
    }

}

