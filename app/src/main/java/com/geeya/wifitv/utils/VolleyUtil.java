/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-21 上午9:47:50
 *
 */
package com.geeya.wifitv.utils;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.api.AppApiResponse;

/**
 * @author Administrator
 *
 */
public final class VolleyUtil {

	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;

	private VolleyUtil() {
	}

	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);

		int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		int cacheSize = 1024 * 1024 * memClass / 8;
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache(cacheSize));
	}

	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	public static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}

	public static void volleyGet(final String url, final AppApiResponse<JSONObject> response) {



		if (WiFiTVApplication.getInstance().getNetworkCheck().checkNetWorkState()) {
			JsonObjectRequest req = new JsonObjectRequest(Method.GET, url, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject result) {
					response.callback(result);
				}
			},new Response.ErrorListener(){
				@Override
				public void onErrorResponse(VolleyError error){
					response.callback(null);
				}
			});

			try {
				VolleyUtil.getRequestQueue().add(req);
			} catch (IllegalStateException e) {
			}
		} else {
			Cache.Entry entry = mRequestQueue.getCache().get(url);

			if (entry == null) {
				response.callback(null);
			} else {
				byte[] data = entry.data;
				String cache = new String(data);
				try {
					JSONObject jsonObject = new JSONObject(cache);
					response.callback(jsonObject);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.callback(null);
				}

			}
		}
	}

	public static void volleyPost(final String url, final HashMap<String, String> hashMap, final AppApiResponse<String> response) {

		//Log.i("VolleyUtil-POST", "URL : " + url);

		StringRequest req = new StringRequest(Method.POST, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String str) {
				// TODO Auto-generated method stub
				//Log.i("VolleyUtil-POST", str);
				response.callback(str);
			}

		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError volleyError) {
				// TODO Auto-generated method stub
				//Log.e("Volley-Post", "Volley-Error : " + volleyError.getMessage());
				response.callback(null);
			}

		}) {
			@Override
			protected HashMap<String, String> getParams() {
				return hashMap;
			}
		};

		try {
			VolleyUtil.getRequestQueue().add(req);
		} catch (IllegalStateException e) {
			// TODO: handle exception
			Log.e("VolleyUtil", e.getMessage());
		}

	}

}
