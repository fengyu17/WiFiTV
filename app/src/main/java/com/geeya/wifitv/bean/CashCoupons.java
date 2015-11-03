package com.geeya.wifitv.bean;


public class CashCoupons extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int bgColor = 0;
	private String describle = null;
	private String number = null;
	private boolean available = true;
	
	public CashCoupons(int bgColor, String describle, String number, boolean available){
		this.bgColor = bgColor;
		this.describle = describle;
		this.number = number;
		this.available = available;
	}
	
	public void setBgColor(int bgColor){
		this.bgColor = bgColor;
	}
	
	public void setDescrible(String describle){
		this.describle = describle;
	}
	
	public void setNumber(String number){
		this.number = number;
	}
	
	public void setAvailable(boolean available){
		this.available = available;
	}
	
	public int getBgColor(){
		return bgColor;
	}
	
	public String getDescrible(){
		return describle;
	}
	
	public String getNumber(){
		return number;
	}
	
	public boolean getAvailable(){
		return available;
	}

}
