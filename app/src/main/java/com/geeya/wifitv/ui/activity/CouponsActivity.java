package com.geeya.wifitv.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geeya.wifitv.R;
import com.geeya.wifitv.adapter.CouponsViewPagerAda;
import com.geeya.wifitv.widget.PagerSlidingCouponsTabStrip;

public class CouponsActivity extends FragmentActivity {

    private PagerSlidingCouponsTabStrip tab;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_coupons);
        initView();
    }

    private void initView() {
        tab = (PagerSlidingCouponsTabStrip) findViewById(R.id.coupons_tab);
        pager = (ViewPager) findViewById(R.id.coupons_pager);
        CouponsViewPagerAda adapter = new CouponsViewPagerAda(getSupportFragmentManager());
        pager.setAdapter(adapter);
        //tab.setTabWidth(360);
        tab.setViewPager(pager);
        LinearLayout llBack = (LinearLayout) findViewById(R.id.back);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("卡券");
        llBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
