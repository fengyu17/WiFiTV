/**
 * Copyright 2015 GYYM
 *
 * All right reserved
 *
 * Created on 2015-9-14 下午5:36:00
 * 
 */
package com.geeya.wifitv.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.geeya.wifitv.R;

/**
 * @author Administrator
 * 
 */
public class WealthGridViewAda extends BasicAdapter {

	private Context context;
	private int count;

	/**
	 * 
	 */
	public WealthGridViewAda(Context context, int count) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.count = count;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View linearLayout = convertView;
		ViewHolder holder;

		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int itemWidth = dm.widthPixels / 2;

		if (convertView == null) {
			linearLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_wealth_gridview, parent, false);
			linearLayout.setLayoutParams(new GridView.LayoutParams(itemWidth, itemWidth));

			holder = new ViewHolder();
			holder.ivCover = (ImageView) linearLayout.findViewById(R.id.iv_wealth_good_cover);
			holder.tvGoodCoin = (TextView) linearLayout.findViewById(R.id.tv_wealth_good_coin);
			holder.tvGoodTitle = (TextView) linearLayout.findViewById(R.id.tv_wealth_good_title);
			linearLayout.setTag(holder);
		} else {
			holder = (ViewHolder) linearLayout.getTag();
		}

		return linearLayout;
	}

	class ViewHolder {
		ImageView ivCover;
		TextView tvGoodCoin;
		TextView tvGoodTitle;
	}

}
