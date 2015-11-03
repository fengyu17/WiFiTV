/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-7-31 上午11:01:40
 */
package com.geeya.wifitv.ui.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.geeya.wifitv.Log;
import com.geeya.wifitv.R;
import com.geeya.wifitv.adapter.ProgramGridViewAda;
import com.geeya.wifitv.base.BaseFragment;
import com.geeya.wifitv.bean.ProgramInfo;
import com.geeya.wifitv.presenter.video.VideoAction;
import com.geeya.wifitv.presenter.video.VideoActionImpl;
import com.geeya.wifitv.ui.activity.ProgramDetailActivity;
import com.geeya.wifitv.ui.interf.IProgramFrag;
import com.github.ksoichiro.android.observablescrollview.ObservableGridView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

/**
 * @author Administrator
 */
public class ProgramCategoryFragment extends BaseFragment implements IProgramFrag {

    private VideoAction videoAction = null;
    private int index; // 请求的页数索引

    private View rootView;
    private SwipeRefreshLayout refreshLayout;
    private ObservableGridView programGridView;
    private ProgramGridViewAda programGridViewAda;
    private boolean isFirstToButtom; // 是否是第一次滚动到底部
    private int lastVisiblePositionY = 0; // 显示的最后一个view的位置的Y坐标

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";

    public static ProgramCategoryFragment getInstance(int position) {
        ProgramCategoryFragment fragment = new ProgramCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_program_category, container, false);
        }
        Activity parentActivity = getActivity();
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        programGridView = (ObservableGridView) rootView.findViewById(R.id.gv_program);
        setDummyDataWithHeader(programGridView, inflater.inflate(R.layout.padding, programGridView, false));
        if (parentActivity instanceof ObservableScrollViewCallbacks) {
            // Scroll to the specified position after layout
            Bundle args = getArguments();
            if (args != null && args.containsKey(ARG_INITIAL_POSITION)) {
                final int initialPosition = args.getInt(ARG_INITIAL_POSITION, 0);
                ScrollUtils.addOnGlobalLayoutListener(programGridView, new Runnable() {
                    @Override
                    public void run() {
                        // scrollTo() doesn't work, should use setSelection()
                        programGridView.setSelection(initialPosition);
                    }
                });
            }
            programGridView.setTouchInterceptionViewGroup((ViewGroup) parentActivity.findViewById(R.id.activity_main_dl));
            programGridView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);
        }

        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            refreshLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        rootView = null;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        videoAction = null;
    }

    @Override
    public void initGridView(final ArrayList<ProgramInfo> programInfoes) {
        // TODO Auto-generated method stub

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                Toast.makeText(mContext, "Pull Down!", Toast.LENGTH_SHORT).show();
                index = 1;
                videoAction.programAction(index, false);
            }
        });
        programGridView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) { // 整个滚动时间结束

                    View v = (View) view.getChildAt(view.getChildCount() - 1);
                    int[] location = new int[2];
                    v.getLocationOnScreen(location); // 获取在整个屏幕的绝对坐标
                    int y = location[1];

                    // 滚动到底部
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {

                        if (isFirstToButtom) {
                            videoAction.programAction(index, false);
                            isFirstToButtom = false;
                            return;
                        } else {
                            if (lastVisiblePositionY != 0) {
                                if (lastVisiblePositionY - y > 600) {
                                    videoAction.programAction(index, false);
                                }
                            }
                        }

                    }

                    lastVisiblePositionY = y;
                }
            }

            @Override
            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }
        });

        programGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                // go to ProgramDetailActivity
                Intent intent = new Intent(getActivity().getBaseContext(), ProgramDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ProgramInfo", programInfoes.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        programGridViewAda = new ProgramGridViewAda(mContext, programInfoes);
        programGridView.setAdapter(programGridViewAda);

        index++;

        TextView tv = new TextView(mContext);
        tv.setGravity(Gravity.CENTER);
        tv.setText(R.string.empty_gridview);
        programGridView.setEmptyView(tv);
    }

    @Override
    public void updateGridView() {
        // TODO Auto-generated method stub
        programGridViewAda.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        isFirstToButtom = true;
        index++;
    }

    @Override
    public int getCurrentPosition() {
        // TODO Auto-generated method stub
        return getArguments().getInt("position");
    }

    @Override
    public void showToast(int msg) {
        // TODO Auto-generated method stub
        if (null != mContext) {
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }
        if (null != refreshLayout && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            refreshLayout.setVisibility(View.INVISIBLE);
        }
    }

    public ProgramCategoryFragment() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            index = 1;
            isFirstToButtom = true;
            videoAction = new VideoActionImpl(this);
            videoAction.programAction(index, true);
        }
    }

}
