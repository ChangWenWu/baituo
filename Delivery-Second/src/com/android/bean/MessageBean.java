package com.android.bean;

import java.sql.Timestamp;


public class MessageBean implements Message {

	private Long ID;
	private Integer State = Integer.valueOf(0);
	private String phone;
	private String Region;
	private String Area ="ok";
	private String Address;
	private Integer conment;
	private Timestamp SendTime = new Timestamp(System.currentTimeMillis());
	private Timestamp FirstGet_Time;
	private Timestamp FirstPut_Time;
	private Timestamp SecondGet_Time;
	private Timestamp SecondPut_Time;
	private Merchant merchant;
	private User user;


	public MessageBean() {
		this.State = Integer.valueOf(0);
		this.SendTime = new Timestamp(System.currentTimeMillis());
		this.user = new UserBean();
	}

	public Long getID() {
		return this.ID;
	}

	public void setID(Long iD) {
		this.ID = iD;
	}

	public Integer getState() {
		return this.State;
	}

	public void setState(Integer state) {
		this.State = state;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public Timestamp getSendTime() {
		return this.SendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.SendTime = sendTime;
	}

	public Merchant getMerchant() {
		return this.merchant;
	}
    
	public Integer getConment() {
		return conment;
	}

	public void setConment(Integer conment) {
		this.conment = conment;
	}

	public Timestamp getFirstGet_Time() {
		return FirstGet_Time;
	}

	public void setFirstGet_Time(Timestamp firstGetTime) {
		FirstGet_Time = firstGetTime;
	}

	public Timestamp getFirstPut_Time() {
		return FirstPut_Time;
	}

	public void setFirstPut_Time(Timestamp firstPutTime) {
		FirstPut_Time = firstPutTime;
	}

	public Timestamp getSecondGet_Time() {
		return SecondGet_Time;
	}

	public void setSecondGet_Time(Timestamp secondGetTime) {
		SecondGet_Time = secondGetTime;
	}

	public Timestamp getSecondPut_Time() {
		return SecondPut_Time;
	}

	public void setSecondPut_Time(Timestamp secondPutTime) {
		SecondPut_Time = secondPutTime;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public MessageBean(String phone, String region, String area, String address) {
		this.phone = phone;
		Region = region;
		Area = area;
		Address = address;
	}

	@Override
	public String toString() {
		return "MessageBean [Address=" + Address + ", Area=" + Area
				+ ", Region=" + Region + ", phone=" + phone + "]";
	}
	
}
