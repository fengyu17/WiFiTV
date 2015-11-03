package com.geeya.wifitv.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.geeya.wifitv.MainTab;
import com.geeya.wifitv.R;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.base.BaseActivity;
import com.geeya.wifitv.bean.ApkInfo;
import com.geeya.wifitv.presenter.AppAction;
import com.geeya.wifitv.presenter.AppActionImpl;
import com.geeya.wifitv.ui.fragment.GameFragment;
import com.geeya.wifitv.ui.fragment.ProgramFragment;
import com.geeya.wifitv.ui.interf.IUpdate;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class MainTabActivity extends BaseActivity implements IUpdate, ObservableScrollViewCallbacks {

    private FragmentTabHost mTabHost;
    private AppAction actionUpdate;
    private View mHeaderView;
    private Toolbar mToolbar;
    private MainTab[] tabs;

    private int mBaseTranslationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintab);
        actionUpdate = new AppActionImpl(this);
        initView();
        if (app.getConfigInfo().getAutoUpdate()) {
            actionUpdate.update();
        }
    }

    private void initView() {

        mTabHost = (FragmentTabHost) findViewById(R.id.activity_main_th);
        mTabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.activity_main_fl);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }

        tabs = MainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabSpec tabSpec = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext()).inflate(R.layout.indicator_maintab, null);
            ImageView icon = (ImageView) indicator.findViewById(R.id.iv_icon);
            icon.setImageResource(tabs[i].getResIcon());
            TextView title = (TextView) indicator.findViewById(R.id.tv_title);
            title.setText(tabs[i].getResName());
            tabSpec.setIndicator(indicator);
            mTabHost.addTab(tabSpec, tabs[i].getClz(), null);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.maintab_background_selector);
        }

        mTabHost.setCurrentTab(2);
        mHeaderView = findViewById(R.id.activity_main_header);
        ViewCompat.setElevation(mHeaderView, getResources().getDimension(R.dimen.toolbar_elevation));
        //设置ToolBar
        mToolbar = (Toolbar) findViewById(R.id.activity_main_tb);
        setSupportActionBar(mToolbar);
        //设置抽屉DrawerLayout
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_dl);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();//初始化状态
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //设置导航栏NavigationView的点击事件
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.activity_main_nv);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_game:
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fl, new GameFragment()).commit();
                        mToolbar.setTitle("视游乐");
                        mTabHost.setVisibility(View.GONE);
                        break;
                    case R.id.item_app:
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fl, new ProgramFragment()).commit();
                        mToolbar.setTitle("应用乐");
                        mTabHost.setVisibility(View.GONE);
                        break;
                    case R.id.item_settings:
                        mToolbar.setTitle("设置");
                        mTabHost.setVisibility(View.GONE);
                        Intent intent = new Intent();
                        intent.setClass(MainTabActivity.this, SettingActivity.class);
                        MainTabActivity.this.startActivity(intent);
                        break;
                    case R.id.item_feedback:
                        mToolbar.setTitle("意见反馈");
                        mTabHost.setVisibility(View.GONE);
                        Intent intent1 = new Intent();
                        intent1.setClass(MainTabActivity.this, FeedbackActivity.class);
                        MainTabActivity.this.startActivity(intent1);
                        break;
                    case R.id.item_help:
                        mToolbar.setTitle("帮助");
                        mTabHost.setVisibility(View.GONE);
                        Intent intent2 = new Intent();
                        intent2.setClass(MainTabActivity.this, HelpActivity.class);
                        MainTabActivity.this.startActivity(intent2);
                        break;
                    case R.id.item_about:
                        mToolbar.setTitle("关于");
                        mTabHost.setVisibility(View.GONE);
                        Intent intent3 = new Intent();
                        intent3.setClass(MainTabActivity.this, AboutActivity.class);
                        MainTabActivity.this.startActivity(intent3);
                        break;
                }
                menuItem.setChecked(true);//点击了把它设为选中状态
                mDrawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });

    }

    public void onDestroy() {
        super.onDestroy();
        mTabHost.clearAllTabs();
    }

    @Override
    public void createDialog(final ApkInfo apkInfo) {
        // TODO Auto-generated method stub
        if (mContext != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainTabActivity.this);
            builder.setTitle(getResources().getString(R.string.checkupdate));
            builder.setMessage(apkInfo.getDescrible());
            builder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    String url = WiFiTVApplication.getInstance().getAreaInfo().getNetElementIP();
                    if (url != null)
                        url += apkInfo.getUpdateUrl();
                    actionUpdate.download(url);
                }
            });
            builder.setNegativeButton(R.string.update_after, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void showToast(String message) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (dragging) {
            int toolbarHeight = mToolbar.getHeight();
            float currentHeaderTranslationY = ViewHelper.getTranslationY(mHeaderView);
            if (firstScroll) {
                if (-toolbarHeight < currentHeaderTranslationY) {
                    mBaseTranslationY = scrollY;
                }
            }
            float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY), -toolbarHeight, 0);
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewHelper.setTranslationY(mHeaderView, headerTranslationY);
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        mBaseTranslationY = 0;

        Fragment fragment = getCurrentFragment();
        if (fragment == null) {
            return;
        }
        View view = fragment.getView();
        if (view == null) {
            return;
        }
        adjustToolbar(scrollState, view);
    }

    private void adjustToolbar(ScrollState scrollState, View view) {
        int toolbarHeight = mToolbar.getHeight();
        final Scrollable scrollView = (Scrollable) view.findViewById(R.id.scroll);
        if (scrollView == null) {
            return;
        }
        int scrollY = scrollView.getCurrentScrollY();
        if (scrollState == ScrollState.DOWN) {
            showToolbar();
        } else if (scrollState == ScrollState.UP) {
            if (toolbarHeight <= scrollY) {
                hideToolbar();
            } else {
                showToolbar();
            }
        } else {
            // Even if onScrollChanged occurs without scrollY changing, toolbar should be adjusted
            if (toolbarIsShown() || toolbarIsHidden()) {
                // Toolbar is completely moved, so just keep its state
                // and propagate it to other pages
                propagateToolbarState(toolbarIsShown());
            } else {
                // Toolbar is moving but doesn't know which to move:
                // you can change this to hideToolbar()
                showToolbar();
            }
        }
    }

    private Fragment getCurrentFragment() {
        return new ProgramFragment();
    }

    private void propagateToolbarState(boolean isShown) {
        int toolbarHeight = mToolbar.getHeight();

    }

    private void propagateToolbarState(boolean isShown, View view, int toolbarHeight) {
        Scrollable scrollView = (Scrollable) view.findViewById(R.id.scroll);
        if (scrollView == null) {
            return;
        }
        if (isShown) {
            // Scroll up
            if (0 < scrollView.getCurrentScrollY()) {
                scrollView.scrollVerticallyTo(0);
            }
        } else {
            // Scroll down (to hide padding)
            if (scrollView.getCurrentScrollY() < toolbarHeight) {
                scrollView.scrollVerticallyTo(toolbarHeight);
            }
        }
    }

    private boolean toolbarIsShown() {
        return ViewHelper.getTranslationY(mHeaderView) == 0;
    }

    private boolean toolbarIsHidden() {
        return ViewHelper.getTranslationY(mHeaderView) == -mToolbar.getHeight();
    }

    private void showToolbar() {
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        if (headerTranslationY != 0) {
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewPropertyAnimator.animate(mHeaderView).translationY(0).setDuration(200).start();
        }
        propagateToolbarState(true);
    }

    private void hideToolbar() {
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        int toolbarHeight = mToolbar.getHeight();
        if (headerTranslationY != -toolbarHeight) {
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewPropertyAnimator.animate(mHeaderView).translationY(-toolbarHeight).setDuration(200).start();
        }
        propagateToolbarState(false);
    }
}
