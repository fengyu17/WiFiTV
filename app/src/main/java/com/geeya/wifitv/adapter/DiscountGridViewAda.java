package com.geeya.wifitv.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.geeya.wifitv.R;

public class DiscountGridViewAda extends BasicAdapter {

	private int count;
	private Context context;

	public DiscountGridViewAda(Context context, int count) {
		this.context = context;
		this.count = count;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View frameLayout = convertView;
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int itemWidth = dm.widthPixels / 2;
		int itemHeight = dm.heightPixels / 3;
		if (frameLayout == null) {
			frameLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_discount_gridview, parent, false);
			frameLayout.setLayoutParams(new GridView.LayoutParams(itemWidth, itemHeight));
		}
		return frameLayout;
	}

}
