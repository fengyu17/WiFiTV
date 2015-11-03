package com.geeya.wifitv.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.geeya.wifitv.bean.ConfigInfo;

public class Tools {

	/**
	 * 产生一个在startNum和betweenNum区间的随机数
	 * 
	 * @return
	 * 
	 *         Created by Administrator Created on 2015-5-15 下午4:16:25
	 */
	public static int randomNum(int startNum, int betweenNum) {
		Random random = new Random();
		return random.nextInt(betweenNum) + startNum;
	}

	/**
	 * 按照一定的格式生成当前时间
	 * 
	 * @return
	 * 
	 *         Created by Administrator Created on 2015-8-24 下午3:07:21
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}

	public static Bitmap decodeHalfBitmapFromResource(Resources res, int resId) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		options.inSampleSize = 2;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * 计算图片的压缩比例
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * 
	 *         Created by Administrator Created on 2015-8-31 下午1:29:45
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// 原图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampelSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比例
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽高比例中最小值作为最终inSampleSize的值
			inSampelSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampelSize;
	}

	/**
	 * 读取配置
	 * 
	 * @param file
	 * @return
	 * 
	 *         Created by Administrator Created on 2015-9-6 上午9:04:43
	 */
	public static ConfigInfo loadConfig(Context context) {
		File configDir = context.getDir("config", Context.MODE_PRIVATE);
		File file = new File(configDir.toString() + "//config.properties");
		Properties properties = new Properties();
		try {
			if (!file.exists()) {
				file.createNewFile();
				return null;
			}
			FileInputStream in = new FileInputStream(file);
			properties.load(in);
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		ConfigInfo configInfo = new ConfigInfo(context);
		configInfo.setAutoCheckWifi(Boolean.parseBoolean((String) properties.get("AutoCheckWifi")));
		configInfo.setAcceptNotice(Boolean.parseBoolean((String) properties.get("AcceptNotice")));
		configInfo.setAutoUpdate(Boolean.parseBoolean((String) properties.get("AutoUpdate")));
		configInfo.setDownloadPath((String) properties.get("DownloadPath"));

		return configInfo;
	}

	/**
	 * 保存配置
	 * 
	 * @param file
	 * @param properties
	 * @return
	 * 
	 *         Created by Administrator Created on 2015-9-6 上午9:05:37
	 */
	public static boolean saveConfig(Context context, ConfigInfo configInfo) {

		Properties properties = new Properties();
		properties.put("AutoCheckWifi", String.valueOf(configInfo.getAutoCheckWifi()));
		properties.put("AcceptNotice", String.valueOf(configInfo.getAcceptNotice()));
		properties.put("AutoUpdate", String.valueOf(configInfo.getAutoUpdate()));
		properties.put("DownloadPath", configInfo.getDownloadPath());

		File configDir = context.getDir("config", Context.MODE_PRIVATE);
		File file = new File(configDir.toString(), "config.properties");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			properties.store(out, null);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
