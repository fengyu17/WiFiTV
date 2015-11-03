package com.geeya.wifitv.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.ChannelInfo;
import com.geeya.wifitv.utils.VolleyUtil;

public class LiveListViewAda extends BasicAdapter {

	private Context context;
	private ArrayList<ChannelInfo> channelInfoes;

	private LayoutInflater layoutInflater;

	public LiveListViewAda(ArrayList<ChannelInfo> channelInfoes, Context context) {
		this.channelInfoes = channelInfoes;
		this.context = context;
	}

	private class ViewHolder {
		public ImageView channelIcon;
		public TextView channelName;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return channelInfoes.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.item_live_list, parent, false);
			holder = new ViewHolder();
			holder.channelIcon = (ImageView) view.findViewById(R.id.iv_live_list_icon);
			holder.channelName = (TextView) view.findViewById(R.id.tv_live_list_name);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.channelName.setText(channelInfoes.get(position).getChannelName());
		ImageLoader imageLoader = VolleyUtil.getImageLoader();
		imageLoader.get(channelInfoes.get(position).getChannelSC(), ImageLoader.getImageListener(holder.channelIcon, R.mipmap.ic_error, R.mipmap.ic_error));
		return view;
	}

}
