/**
 * Copyright 2015 GYYM
 * 
 * All right reserved
 * 
 * Created on 2015-7-16 下午7:58:25
 * 
 */
package com.geeya.wifitv.api.video;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.geeya.wifitv.AppConfig;
import com.geeya.wifitv.R;
import com.geeya.wifitv.WiFiTVApplication;
import com.geeya.wifitv.bean.ChannelInfo;
import com.geeya.wifitv.bean.ProgramDetail;
import com.geeya.wifitv.bean.ProgramInfo;
import com.geeya.wifitv.bean.ProgramSource;
import com.geeya.wifitv.presenter.video.VideoActionCallbackListener;
import com.geeya.wifitv.utils.VolleyUtil;

/**
 * @author Administrator
 * 
 */
public class VideoApiImpl implements VideoApi {

	private String netElementIP;

	public VideoApiImpl() {
		netElementIP = WiFiTVApplication.getInstance().getAreaInfo().getNetElementIP();
	}

	@Override
	public void getProgramInfo(final VideoActionCallbackListener<ArrayList<ProgramInfo>> listener, String areaID, String programType, String key, int page, int numberpage) {
		// TODO Auto-generated method stub
		if (netElementIP == null) {
			return;
		}
		
		StringBuilder stringBuilder = new StringBuilder(netElementIP);
		stringBuilder.append(AppConfig.API_PROGRAMINFO).append("?");
		if (areaID != null) {
			stringBuilder.append("areaId=").append(areaID);
		}
		if (programType != null) {
			if (programType.equals("search")) {
				stringBuilder.append("&programType=").append(programType).append("&key=").append(key);
			} else {
				stringBuilder.append("&programType=").append(programType);
			}
		}
		if (page > 0) {
			stringBuilder.append("&page=").append(page);
		}
		if (numberpage != 10) {
			stringBuilder.append("&numberpage=").append(numberpage);
		}
		VolleyUtil.volleyGet(stringBuilder.toString(), new VideoApiResponse<JSONObject>() {
			@Override
			public void callback(JSONObject jsonObject) {
				if (jsonObject != null) {
					programInfoes = parseProgramInfoJson(jsonObject);
					if (programInfoes != null) {
						if (programInfoes.size() > 0) {
							listener.onSuccess(programInfoes);
						} else {
							listener.onFailure(R.string.empty_programinfo);
						}
						
					} else {
						listener.onFailure(R.string.app_exception);
					}
				} else {
					listener.onFailure(R.string.net_exception);
				}
			}
		});
	}

	@Override
	public ArrayList<ProgramInfo> parseProgramInfoJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		ArrayList<ProgramInfo> list = new ArrayList<ProgramInfo>();
		try {
			boolean flag = jsonObject.getBoolean("flag");
			if (flag) {
				JSONObject content = jsonObject.getJSONObject("content");
				JSONArray sources = content.getJSONArray("sources");
				if (sources.length() > 0) {
					for (int i = 0; i < sources.length(); i++) {
						JSONObject jsonObject2 = sources.getJSONObject(i);
						String coverImg = netElementIP + jsonObject2.getString("coverImg");
						String name = jsonObject2.getString("name");
						String programID = jsonObject2.getString("programID");
						ProgramInfo programInfo = new ProgramInfo(coverImg, name, programID);
						list.add(programInfo);
					}
				}
				return list;
			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void getProgramDetail(final VideoActionCallbackListener<ProgramDetail> listener, String programID) {
		// TODO Auto-generated method stub
		if (netElementIP == null) {
			return;
		}
		StringBuilder stringBuilder = new StringBuilder(netElementIP);
		stringBuilder.append(AppConfig.API_PROGRAMDETAIL).append("?programID=").append(programID);
		VolleyUtil.volleyGet(stringBuilder.toString(), new VideoApiResponse<JSONObject>() {
			@Override
			public void callback(JSONObject jsonObject) {
				if (jsonObject != null) {
					programDetail = parseProgramDetailJson(jsonObject);
					if (programDetail != null) {
						listener.onSuccess(programDetail);
					} else {
						listener.onFailure(R.string.app_exception);
					}
				} else {
					listener.onFailure(R.string.net_exception);
				}
			}
		});

	}

	@Override
	public ProgramDetail parseProgramDetailJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		ProgramDetail programDetail = new ProgramDetail();
		try {
			boolean flag = jsonObject.getBoolean("flag");
			if (flag) {
				JSONObject content = jsonObject.getJSONObject("content");
				programDetail.setDirector(content.getString("director"));
				programDetail.setDescrib(content.getString("discrib"));
				programDetail.setLeadingActor(content.getString("leadingActor"));
				JSONArray sources = content.getJSONArray("sources");
				if (sources.length() > 0) {
					for (int i = 0; i < sources.length(); i++) {
						JSONObject jsonObject2 = sources.getJSONObject(i);
						int episodeNum = jsonObject2.getInt("episodeNum");
						String sourceName = jsonObject2.getString("sourceName");
						String url = netElementIP + jsonObject2.getString("url");
						ProgramSource programSource = new ProgramSource(episodeNum, sourceName, url);
						programDetail.add(programSource);
					}
					return programDetail;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void getChannelInfo(final VideoActionCallbackListener<ArrayList<ChannelInfo>> listener) {
		// TODO Auto-generated method stub
		if (netElementIP == null) {
			return;
		}
		StringBuilder stringBuilder = new StringBuilder(netElementIP).append(AppConfig.API_CHANNELINFO);
		VolleyUtil.volleyGet(stringBuilder.toString(), new VideoApiResponse<JSONObject>() {
			@Override
			public void callback(JSONObject jsonObject) {
				if (jsonObject != null) {
					channelInfoes = parseChannelInfoJson(jsonObject);
					if (channelInfoes != null) {
						listener.onSuccess(channelInfoes);
					} else {
						listener.onFailure(R.string.app_exception);
					}
				} else {
					listener.onFailure(R.string.net_exception);
				}
			}
		});
	}

	@Override
	public ArrayList<ChannelInfo> parseChannelInfoJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		ArrayList<ChannelInfo> list = new ArrayList<ChannelInfo>();
		try {
			JSONArray jsonArray = jsonObject.getJSONArray("content");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				String channelID = jsonObject2.getString("ChannelID");
				String channelName = jsonObject2.getString("ChannelName");
				String channelNum = jsonObject2.getString("ChannelNum");
				String channelSC = netElementIP + jsonObject2.getString("ChannelSC");
				String mediaAddr = jsonObject2.getString("Mediaaddr");
				ChannelInfo channelInfo = new ChannelInfo(channelID, channelName, channelNum, channelSC, mediaAddr);
				list.add(channelInfo);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
