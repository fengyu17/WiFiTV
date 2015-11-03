package com.geeya.wifitv.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geeya.wifitv.R;
import com.geeya.wifitv.adapter.ProgramViewPagerAda;
import com.geeya.wifitv.base.BaseFragment;
import com.geeya.wifitv.widget.PagerSlidingTabStrip;

public class ProgramFragment extends BaseFragment {

    private View rootView; // 缓存Fragment view

    private PagerSlidingTabStrip tab;
    private ViewPager pager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_maintab_program, container, false);
        }

        tab = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
        pager = (ViewPager) rootView.findViewById(R.id.pager);

        initWidget();

        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        rootView = null;
    }

    public void initWidget() {

        ProgramViewPagerAda viewPagerAda = new ProgramViewPagerAda(getChildFragmentManager());
        pager.setAdapter(viewPagerAda);
        tab.setViewPager(pager);
    }

}
