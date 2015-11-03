package com.geeya.wifitv.base;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.geeya.wifitv.R;
import com.geeya.wifitv.WiFiTVApplication;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    public Context mContext;
    public WiFiTVApplication app;

    private static final int NUM_OF_ITEMS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        app = (WiFiTVApplication) getApplication();
    }

    public static ArrayList<String> getDummyData() {
        return getDummyData(NUM_OF_ITEMS);
    }
    public static ArrayList<String> getDummyData(int num) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            items.add("Item " + i);
        }
        return items;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
