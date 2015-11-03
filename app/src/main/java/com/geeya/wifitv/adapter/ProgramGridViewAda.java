package com.geeya.wifitv.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.ProgramInfo;
import com.geeya.wifitv.utils.VolleyUtil;

public class ProgramGridViewAda extends BasicAdapter {

	private Context context;
	private ArrayList<ProgramInfo> programInfoes;

	public ProgramGridViewAda(Context context, ArrayList<ProgramInfo> programInfoes) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.programInfoes = programInfoes;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return programInfoes.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View frameLayout = convertView;
		ViewHolder holder;
		
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int windowWidth = dm.widthPixels;
		int itemWidth = windowWidth / 3;
		int itemHeight = (4 * itemWidth) / 3;
		
		if (convertView == null) {
			frameLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_program_gridview, parent, false);
			frameLayout.setLayoutParams(new GridView.LayoutParams(itemWidth, itemHeight));

			holder = new ViewHolder();
			holder.imageView = (ImageView) frameLayout.findViewById(R.id.video_image);
			holder.textView = (TextView) frameLayout.findViewById(R.id.video_name);
			frameLayout.setTag(holder);
		} else {
			holder = (ViewHolder) frameLayout.getTag();
		}

		// 请求网络数据加载封面图片
		ImageLoader imageLoader = VolleyUtil.getImageLoader();
		imageLoader.get(programInfoes.get(position).getCoverImg(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_empty, R.mipmap.ic_empty), 200, 300);

		holder.textView.setText(programInfoes.get(position).getName());

		return frameLayout;
	}

	class ViewHolder {
		ImageView imageView;
		TextView textView;
	}

}
