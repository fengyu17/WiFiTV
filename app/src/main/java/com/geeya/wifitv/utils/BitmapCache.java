/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-7-21 上午10:00:05
 * 
 */
package com.geeya.wifitv.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * @author Administrator
 * 
 */
public class BitmapCache implements ImageCache {

	private LruCache<String, Bitmap> mCache;
	
	public BitmapCache(int cacheSize) {
		mCache = new LruCache<String, Bitmap>(cacheSize) {
			
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// TODO Auto-generated method stub
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
	}

	@Override
	public Bitmap getBitmap(String url) {
		// TODO Auto-generated method stub
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		// TODO Auto-generated method stub
		mCache.put(url, bitmap);
	}


}
