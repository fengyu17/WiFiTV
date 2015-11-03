/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-9-14 上午9:57:22
 */
package com.geeya.wifitv.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geeya.wifitv.R;
import com.geeya.wifitv.adapter.WealthGridViewAda;
import com.geeya.wifitv.base.BaseActivity;
import com.geeya.wifitv.widget.ScrollGridView;

/**
 * @author Administrator
 *
 */
public class WealthActivity extends BaseActivity {

    private TextView tv_MyCoin;
    private HorizontalScrollView hsv_MyConversion;
    private LinearLayout ll_MyConversion;
    private ScrollGridView sg_Goods;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_wealth);
        initView();
        Toast toast = Toast.makeText(mContext, R.string.net_exception, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void initView() {
        LinearLayout llBack = (LinearLayout) findViewById(R.id.back);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.wealth);
        llBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_MyCoin = (TextView) findViewById(R.id.tv_wealth_my_coin);
        hsv_MyConversion = (HorizontalScrollView) findViewById(R.id.hsv_wealth_my_conversion);
        ll_MyConversion = (LinearLayout) findViewById(R.id.ll_wealth_conversion);
        sg_Goods = (ScrollGridView) findViewById(R.id.sg_wealth_goods);

        tv_MyCoin.setText("积分 : ");

        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        hsv_MyConversion.getLayoutParams().height = metrics.widthPixels / 3;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout itemConversion0 = (LinearLayout) inflater.inflate(R.layout.item_wealth_gridview, null);
        itemConversion0.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 3, metrics.widthPixels / 3));
        LinearLayout itemConversion1 = (LinearLayout) inflater.inflate(R.layout.item_wealth_gridview, null);
        itemConversion1.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 3, metrics.widthPixels / 3));
        LinearLayout itemConversion2 = (LinearLayout) inflater.inflate(R.layout.item_wealth_gridview, null);
        itemConversion2.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 3, metrics.widthPixels / 3));
        LinearLayout itemConversion3 = (LinearLayout) inflater.inflate(R.layout.item_wealth_gridview, null);
        itemConversion3.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 3, metrics.widthPixels / 3));

        ll_MyConversion.addView(itemConversion0);
        ll_MyConversion.addView(itemConversion1);
        ll_MyConversion.addView(itemConversion2);
        ll_MyConversion.addView(itemConversion3);

        WealthGridViewAda wealthGridViewAda = new WealthGridViewAda(mContext, 10);
        sg_Goods.setAdapter(wealthGridViewAda);

    }

}
