package com.example.delivery_First;


public class First_SendBean implements First_Send {
	private long Id;
	private String Password;
	private String Phone;
	private String Area="";

	public long getId() {
		return this.Id;
	}

	public void setId(long id) {
		this.Id = id;
	}

	public String getPassword() {
		return this.Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public String getPhone() {
		return this.Phone;
	}

	public void setPhone(String phone) {
		this.Phone = phone;

	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}
	
}

