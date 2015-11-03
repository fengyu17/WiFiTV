package com.geeya.wifitv.adapter;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geeya.wifitv.R;


public class HomeListViewAda extends BasicAdapter {
    private LayoutInflater layoutInflater;
    private List<ScanResult> list;
    private Context context;
    private Viewholder holder;

    public HomeListViewAda(Context context, List<ScanResult> list) {
        this.context = context;
        this.list = list;
    }

    public static class Viewholder {
        public ImageView icon;
        public RelativeLayout layout;
        public TextView name;
        public TextView state;
        public ImageView area;
        public TextView recommendation;
    }

    public void setdata(List<ScanResult> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ScanResult wifiList = list.get(position);
        int recommendationWifi = wifiList.SSID.indexOf("g");
        View view = convertView;
        if (null == convertView) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_home_list, parent, false);
            holder = new Viewholder();
            holder.layout = (RelativeLayout) view.findViewById(R.id.rl_home_list_wifi);
            holder.name = (TextView) view.findViewById(R.id.tv_home_list_wifi_name);
            holder.area = (ImageView) view.findViewById(R.id.iv_home_list_wifi_area);
            holder.recommendation = (TextView) view.findViewById(R.id.tv_home_list_wifi_recommendation);
            holder.state = (TextView) view.findViewById(R.id.tv_home_list_wifi_state);
            holder.icon = (ImageView) view.findViewById(R.id.iv_home_list_wifi_icon);
            view.setTag(holder);
        } else {
            holder = (Viewholder) view.getTag();
        }
        holder.name.setText(wifiList.SSID);
        String cipherType = wifiList.capabilities;
        if (!TextUtils.isEmpty(cipherType))
            cipherType = getCipherType(context, cipherType);
        holder.state.setText(cipherType);
        if ((0 == position) && (0 == recommendationWifi)) {
            holder.layout.setBackgroundColor(0xffffcccc);
            holder.area.setVisibility(View.VISIBLE);
            holder.recommendation.setVisibility(View.VISIBLE);
        } else {
            holder.layout.setBackgroundResource(R.drawable.item_home_list_select);   //setBackgroundDrawable((context.getResources().getDrawable(R.drawable.item_home_list_select)));         // setBackgroundResource(context.getResources().get);;
            holder.area.setVisibility(View.GONE);
            holder.recommendation.setVisibility(View.GONE);
        }
        calculateLevel(wifiList.level);
        if (Math.abs(wifiList.level) > 100) {
            if (cipherType.equals(R.string.home_listview_ada_nocipher))
                holder.icon.setImageResource(R.mipmap.home_list_db_0_passwd);
            else
                holder.icon.setImageResource(R.mipmap.home_list_db_0_nopasswd);
        } else if (Math.abs(wifiList.level) > 85) {
            if (cipherType.equals(R.string.home_listview_ada_nocipher))
                holder.icon.setImageResource(R.mipmap.home_list_db_1_nopasswd);
            else
                holder.icon.setImageResource(R.mipmap.home_list_db_1_passwd);
        } else if (Math.abs(wifiList.level) > 75) {
            if (cipherType.equals(R.string.home_listview_ada_nocipher))
                holder.icon.setImageResource(R.mipmap.home_list_db_2_nopasswd);
            else
                holder.icon.setImageResource(R.mipmap.home_list_db_2_passwd);
        } else if (Math.abs(wifiList.level) > 65) {
            if (cipherType.equals(R.string.home_listview_ada_nocipher))
                holder.icon.setImageResource(R.mipmap.home_list_db_3_nopasswd);
            else
                holder.icon.setImageResource(R.mipmap.home_list_db_3_passwd);
        } else {
            if (cipherType.equals(R.string.home_listview_ada_nocipher))
                holder.icon.setImageResource(R.mipmap.home_list_db_4_nopasswd);
            else
                holder.icon.setImageResource(R.mipmap.home_list_db_4_passwd);
        }
        return view;
    }

    private String getCipherType(Context context, String cipherType) {
        if (cipherType.contains("WPA") || cipherType.contains("wpa"))
            return context.getResources().getString(R.string.home_listview_ada_wpacipher);
        else if (cipherType.contains("WEP") || cipherType.contains("wep"))
            return context.getResources().getString(R.string.home_listview_ada_wepcipher);
        else if (cipherType.contains("EAP") || cipherType.contains("eap"))
            return context.getResources().getString(R.string.home_listview_ada_eapcipher);
        else
            return context.getResources().getString(R.string.home_listview_ada_nocipher);
    }

    private int calculateLevel(int level) {
        int result = WifiManager.calculateSignalLevel(level, 5);
        return result;
    }


}
