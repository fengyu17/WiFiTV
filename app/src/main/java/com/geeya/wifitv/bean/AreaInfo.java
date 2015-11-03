package com.geeya.wifitv.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class AreaInfo extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String netElementIP;
	private String areaID = "0";
	private String area = "中国";
	private String gps;
	private String areaDetail;
	
	public AreaInfo() {
	}
	
	public AreaInfo(JSONObject jsonObject) throws JSONException {
		this.netElementIP = jsonObject.getString("netElementIP");
		this.areaID = jsonObject.getInt("areaID") + "";
		this.area = jsonObject.getString("area");
		this.gps = jsonObject.getString("gps");
		this.areaDetail = jsonObject.getString("areaDetail");
	}

	public AreaInfo(String netElementIP, String areaID, String area, String gps,
			String areaDetail) {
		this.netElementIP = netElementIP;
		this.areaID = areaID;
		this.area = area;
		this.gps = gps;
		this.areaDetail = areaDetail;
	}

	public void setNetElementIP(String netElementIP) {
		this.netElementIP = netElementIP;
	}

	public String getNetElementIP() {
		return "http://" + netElementIP + ":8080";
	}

	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}

	public String getAreaID() {
		return areaID;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea() {
		return area;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getGps() {
		return gps;
	}

	public void setAreaDetail(String areaDetail) {
		this.areaDetail = areaDetail;
	}

	public String getAreaDetail() {
		return areaDetail;
	}
	
	public String toJSONString() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("netElementIP", netElementIP);
			jsonObject.put("areaID", areaID);
			jsonObject.put("area", area);
			jsonObject.put("gps", gps);
			jsonObject.put("areaDetail", areaDetail);
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jsonObject.toString();
	}

}
