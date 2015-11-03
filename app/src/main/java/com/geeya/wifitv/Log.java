package com.geeya.wifitv;

/**
 * Log打印信息类
 * Created by Administrator on 2015/10/26.
 */
public class Log {

    public final static String TAG = "WiFiTV";

    /**
     * 信息
     */
    public static void i(String msg) {
        android.util.Log.i(TAG, msg);
    }

    /**
     * 调试
     */
    public static void d(String msg) {
        android.util.Log.d(TAG, msg);
    }

    /**
     * 错误
     */
    public static void e(String msg) {
        android.util.Log.e(TAG, msg);
    }

    /**
     * 警告
     */
    public static void w(String msg) {
        android.util.Log.w(TAG, msg);
    }
}
