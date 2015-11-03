/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-8-5 上午11:24:15
 */
package com.geeya.wifitv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.geeya.wifitv.R;

/**
 * @author Administrator
 *
 */
public class ProgramDetailGridViewAda extends BasicAdapter {

    private Context context;
    private int numb;

    public ProgramDetailGridViewAda(int numb, Context context) {
        this.numb = numb;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return numb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        FrameLayout frameLayout;
        if (convertView == null) {
            frameLayout = (FrameLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_programdetail_gridview, parent, false);
        } else {
            frameLayout = (FrameLayout) convertView;
        }

        TextView textView = (TextView) frameLayout.findViewById(R.id.tv_program_episode);

        if (1 == numb) {
            textView.setText("播放");
        } else {
            textView.setText(position + 1 + "");
        }

        return frameLayout;
    }

}
