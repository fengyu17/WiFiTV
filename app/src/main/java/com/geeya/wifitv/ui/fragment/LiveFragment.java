package com.geeya.wifitv.ui.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.geeya.wifitv.R;
import com.geeya.wifitv.adapter.LiveListViewAda;
import com.geeya.wifitv.base.BaseFragment;
import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.bean.ChannelInfo;
import com.geeya.wifitv.presenter.video.VideoAction;
import com.geeya.wifitv.presenter.video.VideoActionImpl;
import com.geeya.wifitv.ui.activity.LivePlayActivity;
import com.geeya.wifitv.ui.interf.ILiveFrag;
import com.geeya.wifitv.widget.SlideImageView;

public class LiveFragment extends BaseFragment implements ILiveFrag {

    private View rootView; // 缓存Fragment view

    private SlideImageView slideImageView;
    private ListView liveList;

    private VideoAction videoAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoAction = new VideoActionImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_maintab_live, container, false);
        }

        slideImageView = (SlideImageView) rootView.findViewById(R.id.sv_live_ad);
        liveList = (ListView) rootView.findViewById(R.id.lv_channellist);

        videoAction.liveAction();

        // 缓存的rootView需要判断是否已经被加过parent,如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        slideImageView.startPlay();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        slideImageView.stopPlay();
        super.onStop();
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
    public void initSlideView(ArrayList<ADInfo> adInfoes) {
        slideImageView.init(adInfoes);
        slideImageView.startPlay();
    }

    @Override
    public void initLiveList(final ArrayList<ChannelInfo> channelInfoes) {
        liveList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击跳转
                Intent intent = new Intent(mContext, LivePlayActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("ChannelInfo", channelInfoes);
                mBundle.putInt("Position", position);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        LiveListViewAda liveListViewAda = new LiveListViewAda(channelInfoes, mContext);
        liveList.setAdapter(liveListViewAda);
    }

    @Override
    public void showToast(int msg) {
        if (null != mContext) {
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }
    }

}
