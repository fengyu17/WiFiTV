package com.geeya.wifitv.base;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.geeya.wifitv.R;
import com.geeya.wifitv.WiFiTVApplication;
import com.github.ksoichiro.android.observablescrollview.ObservableGridView;

import java.util.ArrayList;

public class BaseFragment extends Fragment {

    public WiFiTVApplication app;
    public Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        app = (WiFiTVApplication) getActivity().getApplication();
        mContext = getActivity().getApplicationContext();
    }

    public static ArrayList<String> getDummyData() {
        return BaseActivity.getDummyData();
    }
    protected void setDummyDataWithHeader(ObservableGridView gridView, View headerView) {
        gridView.addHeaderView(headerView);
        setDummyData(gridView);
    }

    protected void setDummyData(GridView gridView) {
        gridView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.item_program_gridview, getDummyData()));
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        app = null;
        mContext = null;
    }

}
