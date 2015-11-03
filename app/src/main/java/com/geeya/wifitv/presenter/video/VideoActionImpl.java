/**
 * Copyright 2015 GYYM
 * <p/>
 * All right reserved
 * <p/>
 * Created on 2015-7-22 上午11:33:09
 */
package com.geeya.wifitv.presenter.video;


import java.util.ArrayList;

import android.util.Log;

import com.geeya.wifitv.AppConfig;
import com.geeya.wifitv.R;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.api.advertisement.AdApi;
import com.geeya.wifitv.api.advertisement.AdApiImpl;
import com.geeya.wifitv.api.video.VideoApi;
import com.geeya.wifitv.api.video.VideoApiImpl;
import com.geeya.wifitv.bean.ADInfo;
import com.geeya.wifitv.bean.AreaInfo;
import com.geeya.wifitv.bean.ChannelInfo;
import com.geeya.wifitv.bean.ProgramDetail;
import com.geeya.wifitv.bean.ProgramInfo;
import com.geeya.wifitv.presenter.player.PlayerActionCallbackListener;
import com.geeya.wifitv.ui.interf.ILiveFrag;
import com.geeya.wifitv.ui.interf.IProgramDetail;
import com.geeya.wifitv.ui.interf.IProgramFrag;

/**
 * @author Administrator
 *
 */
public class VideoActionImpl implements VideoAction {

    public ADInfo liveListAD;

    private ILiveFrag iLiveFrag;
    private IProgramFrag iProgramFrag;
    private IProgramDetail iProgramDetail;
    private VideoApi videoApi;
    private AdApi adApi;

    private ArrayList<ProgramInfo> programList;

    public VideoActionImpl(ILiveFrag iLiveFrag) {
        this.iLiveFrag = iLiveFrag;
        this.videoApi = new VideoApiImpl();
        this.adApi = new AdApiImpl();
    }

    public VideoActionImpl(IProgramFrag iProgramFrag) {
        this.iProgramFrag = iProgramFrag;
        this.videoApi = new VideoApiImpl();
    }

    public VideoActionImpl(IProgramDetail iProgramDetail) {
        this.iProgramDetail = iProgramDetail;
        this.videoApi = new VideoApiImpl();
    }

    @Override
    public void liveAction() {
        // TODO Auto-generated method stub
        // 请求直播列表广告
        AreaInfo areaInfo = WiFiTVApplication.getInstance().getAreaInfo();
        String areaID = areaInfo.getAreaID();

        // 请求直播列表广告
        adApi.getAD(new PlayerActionCallbackListener<ArrayList<ADInfo>>() {

            @Override
            public void onSuccess(ArrayList<ADInfo> adInfoes) {
                // TODO Auto-generated method stub
                if (null != iLiveFrag) {
                    iLiveFrag.initSlideView(adInfoes);
                } else {
                    Log.w("VideoActionImpl", "call back is null, stop");
                }
            }

            @Override
            public void onFailure(int message) {
                // TODO Auto-generated method stub
                if (null != iLiveFrag) {
                    iLiveFrag.showToast(message);
                } else {
                    Log.w("VideoActionImpl", "call back is null, stop");
                }

            }

        }, AppConfig.ADTYPE_LIVELIST, null, areaID, null);

        // 请求直播列表
        videoApi.getChannelInfo(new VideoActionCallbackListener<ArrayList<ChannelInfo>>() {

            @Override
            public void onSuccess(ArrayList<ChannelInfo> channelInfoes) {
                // TODO Auto-generated method stub
                if (null != iLiveFrag) {
                    iLiveFrag.initLiveList(channelInfoes);
                } else {
                    Log.w("VideoActionImpl", "call back is null, stop");
                }

            }

            @Override
            public void onFailure(int message) {
                // TODO Auto-generated method stub
                if (null != iLiveFrag) {
                    iLiveFrag.showToast(message);
                } else {
                    Log.w("VideoActionImpl", "call back is null, stop");
                }
            }

        });
    }


    @Override
    public void programAction(int index, final boolean isInit) {
        // TODO Auto-generated method stub
        // 获取AreaID
        AreaInfo areaInfo = WiFiTVApplication.getInstance().getAreaInfo();
        String areaID = areaInfo.getAreaID();

        // 获取点播类型
        int position = iProgramFrag.getCurrentPosition();
        String programType = AppConfig.PROGRAM_TYPE[position];

        // 开始请求
        if (index == 1) {
            // 请求第一页数据
            videoApi.getProgramInfo(new VideoActionCallbackListener<ArrayList<ProgramInfo>>() {

                @Override
                public void onSuccess(ArrayList<ProgramInfo> programInfoes) {
                    // TODO Auto-generated method stub
                    if (null != iProgramFrag) {
                        if (isInit) {
                            programList = programInfoes;
                            iProgramFrag.initGridView(programList);
                        } else {
                            if (null != programList) {
                                programList.clear();
                                programList.addAll(programInfoes);
                                iProgramFrag.updateGridView();
                            } else {
                                programList = programInfoes;
                                iProgramFrag.initGridView(programList);
                            }
                        }
                    } else {
                        Log.w("VideoActionImpl", "call back is null, stop");
                    }

                }

                @Override
                public void onFailure(int message) {
                    // TODO Auto-generated method stub
                    if (null != iProgramFrag) {
                        iProgramFrag.showToast(message);
                    } else {
                        Log.w("VideoActionImpl", "call back is null, stop");
                    }

                }

            }, areaID, programType, null, index, AppConfig.PROGRAM_PERNUM);

        } else {
            // 请求其他页数据
            videoApi.getProgramInfo(new VideoActionCallbackListener<ArrayList<ProgramInfo>>() {

                @Override
                public void onSuccess(ArrayList<ProgramInfo> programInfoes) {
                    // TODO Auto-generated method stub
                    if (null != iProgramFrag) {
                        if (programInfoes != null) {
                            programList.addAll(programInfoes);
                            iProgramFrag.updateGridView();
                        }
                    } else {
                        Log.w("VideoActionImpl", "call back is null, stop");
                    }

                }

                @Override
                public void onFailure(int message) {
                    // TODO Auto-generated method stub
                    if (null != iProgramFrag) {
                        iProgramFrag.showToast(message);
                    } else {
                        Log.w("VideoActionImpl", "call back is null, stop");
                    }
                }
            }, areaID, programType, null, index, AppConfig.PROGRAM_PERNUM);
        }

    }


    @Override
    public void programDetailAction(ProgramInfo programInfo) {
        // TODO Auto-generated method stub
        String programID = programInfo.getProgramID();
        if (null == programID) {
            iProgramDetail.showToast(R.string.net_exception);
        } else {
            videoApi.getProgramDetail(new VideoActionCallbackListener<ProgramDetail>() {

                @Override
                public void onSuccess(ProgramDetail programDetail) {
                    // TODO Auto-generated method stub
                    if (null != iProgramDetail) {
                        iProgramDetail.updateView(programDetail);
                    } else {
                        Log.w("VideoActionImpl", "call back is null, stop");
                    }

                }

                @Override
                public void onFailure(int message) {
                    // TODO Auto-generated method stub
                    if (null != iProgramDetail) {
                        iProgramDetail.showToast(message);
                    } else {
                        Log.w("VideoActionImpl", "call back is null, stop");
                    }

                }
            }, programID);
        }
    }

}
