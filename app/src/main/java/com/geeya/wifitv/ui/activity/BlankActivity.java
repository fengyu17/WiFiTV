/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-9-25 上午10:01:28
 */
package com.geeya.wifitv.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.geeya.wifitv.R;
import com.geeya.wifitv.base.BaseActivity;

/**
 * @author Administrator
 *
 */
public class BlankActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_blank);
        LinearLayout llBack = (LinearLayout) findViewById(R.id.back);
        llBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

}
