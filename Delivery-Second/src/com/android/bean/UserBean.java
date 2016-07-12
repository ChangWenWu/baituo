package com.android.bean;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserBean implements User {

	private long Account;
	private String Name;
	private String Password;
	private String phone;
	private String Region;
	private String Area;
	private String Address;
	private String CardId;
	private String stuCard_ID;
	private byte[] id_img;
	private byte[] stuCard_img;
	private Integer income;
	private Integer integral;
	private Date regdate;
	private Set<Message> message = new HashSet<Message>();

	public long getAccount() {
		return this.Account;
	}

	public void setAccount(long account) {
		this.Account = account;
	}

	public String getName() {
		return this.Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getPassword() {
		return this.Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegion() {
		return this.Region;
	}

	public void setRegion(String region) {
		this.Region = region;
	}

	public String getArea() {
		return this.Area;
	}

	public void setArea(String area) {
		this.Area = area;
	}

	public String getAddress() {
		return this.Address;
	}

	public void setAddress(String address) {
		this.Address = address;
	}
	
	public String getCardId() {
		return CardId;
	}

	public void setCardId(String cardId) {
		CardId = cardId;
	}

	public String getStuCard_ID() {
		return this.stuCard_ID;
	}

	public void setStuCard_ID(String stuCardID) {
		this.stuCard_ID = stuCardID;
	}

	public byte[] getId_img() {
		return this.id_img;
	}

	public void setId_img(byte[] idImg) {
		this.id_img = idImg;
	}

	public byte[] getStuCard_img() {
		return this.stuCard_img;
	}

	public void setStuCard_img(byte[] stuCardImg) {
		this.stuCard_img = stuCardImg;
	}

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}



	public Set<Message> getMessage() {
		return this.message;
	}

	public void setMessage(Set<Message> message) {
		this.message = message;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public UserBean() {
		this.income = 0;
		this.integral = 0;
		this.regdate = new Date();
	}

	public UserBean(String phone, String region, String area, String address) {

		this.phone = phone;
		Region = region;
		Area = area;
		Address = address;

	}

	public UserBean(String name, String password) {
		super();
		Name = name;
		Password = password;
	}

	public UserBean(String name2) {
		// TODO Auto-generated constructor stub
	}

	public UserBean(String name2, String password2, String phone2,
			String region2, String area2, String address2, String id2,
			byte[] idImg, String stuCardID, byte[] stuCardImg) {
		// TODO Auto-generated constructor stub
		super();
		Name = name2;
		Password = password2;
		this.phone = phone2;
		Region = region2;
		Area = area2;
		Address = address2;
		CardId = id2;
		stuCard_ID = stuCardID;
		id_img = idImg;
		stuCard_img = stuCardImg;

	}

	@Override
	public String toString() {
		return "UserBean [Account=" + Account + ", Name=" + Name
				+ ", Password=" + Password + ", phone=" + phone + ", Region="
				+ Region + ", Area=" + Area + ", Address=" + Address + ", Id="
				+ CardId + ", stuCard_ID=" + stuCard_ID + ", id_img="
				+ Arrays.toString(id_img) + ", stuCard_img="
				+ Arrays.toString(stuCard_img) + ", income=" + income
				+ ", integral=" + integral + ", regdate=" + regdate
				+ ", message=" + message + "]";
	}
	
}
