/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-8-27 下午6:02:21
 */
package com.geeya.wifitv.widget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.android.volley.toolbox.ImageLoader;
import com.geeya.wifitv.R;
import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.utils.VolleyUtil;

/**
 * @author Administrator
 *
 */
public class SlideImageView extends FrameLayout {

    private Context context;

    private int timeInterval = 0; // 图片显示时间，默认为0

    private ArrayList<ADInfo> adInfoes; // 直播广告列表
    private ArrayList<ImageView> imageViewList; // 广告图片列表

    private ViewPager viewPager;
    private ScheduledExecutorService scheduledExecutorService; // 延迟执行服务

    private ImageLoader imageLoader; // 图片加载

    private ViewPagerHandler handler;

    public SlideImageView(Context context) {
        this(context, null);
    }

    public SlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        imageLoader = VolleyUtil.getImageLoader();
    }

    public void init(ArrayList<ADInfo> adInfoes) {
        this.adInfoes = adInfoes;
        initData();
        initUI();
    }

    private void initData() {
        timeInterval = Integer.parseInt(adInfoes.get(0).getADDuration());
        imageViewList = new ArrayList<ImageView>();
    }

    @SuppressWarnings("deprecation")
    private void initUI() {
        if (adInfoes == null || adInfoes.size() == 0) {
            return;
        }

        for (int i = 0; i < adInfoes.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setTag(adInfoes.get(i).getADContent());
            imageView.setScaleType(ScaleType.FIT_XY);
            imageViewList.add(imageView);
        }

        LayoutInflater.from(context).inflate(R.layout.item_live_slideimageview, this, true);
        viewPager = (ViewPager) findViewById(R.id.vp_ad_image);
        viewPager.setFocusable(true);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());

        handler = new ViewPagerHandler(viewPager, imageViewList.size());
    }

    public void startPlay() {
        if (timeInterval != 0) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), timeInterval, timeInterval, TimeUnit.SECONDS);
        }
    }

    public void stopPlay() {
        if (null != scheduledExecutorService) {
            scheduledExecutorService.shutdown();
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            // TODO Auto-generated method stub
            ImageView imageView = imageViewList.get(position);

            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    OnClickDialog clickDialog = new OnClickDialog(adInfoes.get(position).getADID(), adInfoes.get(position).getADLink());
                    clickDialog.showDialog(context);
                }
            });

            imageLoader.get(imageView.getTag() + "", ImageLoader.getImageListener(imageView, R.mipmap.ic_error, R.mipmap.ic_error));

            ((ViewPager) container).addView(imageViewList.get(position));

            return imageViewList.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub
            return view == object;
        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            ((ViewPager) container).removeView(imageViewList.get(position));
        }

    }

    private class MyPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // TODO Auto-generated method stub
        }
    }

    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    public static class ViewPagerHandler extends Handler {

        WeakReference<ViewPager> reference;

        private int currentItem = 0;
        private int size;

        public ViewPagerHandler(ViewPager viewPager, int size) {
            reference = new WeakReference<ViewPager>(viewPager);
            this.size = size;
        }

        @Override
        public void handleMessage(Message msg) {
            final ViewPager viewPager = (ViewPager) reference.get();
            currentItem %= size;
            currentItem++;
            viewPager.setCurrentItem(currentItem);
        }

    }

}
