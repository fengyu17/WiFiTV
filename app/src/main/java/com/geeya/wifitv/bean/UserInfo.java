package com.geeya.wifitv.bean;

public class UserInfo extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userEmail = null;
	private String userTel = null;
	private String userPassword = null;
	private String userName = "游客";
	private String macAddress;
	private String createTime = null;
	private int userIdentify = 1;// 用户身份，未注册1，注册0
	private String userId;
	private String userFace;

	public UserInfo() {

	}

	/**
	 * @param userEmail
	 *            邮箱地址
	 * @param userTel
	 *            联系电话
	 * @param userPassword
	 *            密码
	 * @param userName
	 *            用户名
	 * @param macAddress
	 *            MAC地址
	 * @param createTime
	 *            创建时间
	 * @param userIdentify
	 *            用户身份，未注册1，注册0
	 * @param userId
	 *            用户ID
	 */
	public UserInfo(String userEmail, String userTel, String userPassword,
			String userName, String macAddress, String createTime,
			int userIdentify, String userId, String userFace) {
		// TODO Auto-generated constructor stub
		this.userEmail = userEmail;
		this.userTel = userTel;
		this.userPassword = userPassword;
		this.userName = userName;
		this.macAddress = macAddress;
		this.createTime = createTime;
		this.userIdentify = userIdentify;
		this.userId = userId;
		this.userFace = userFace;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setUserIdentify(int identify) {
		this.userIdentify = identify;
	}

	public int getUserIdentify() {
		return userIdentify;
	}

	public void setUserId(String id) {
		this.userId = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserFace(String userFace) {
		this.userFace = userFace;
	}

	public String getUserFace() {
		return userFace;
	}
}
