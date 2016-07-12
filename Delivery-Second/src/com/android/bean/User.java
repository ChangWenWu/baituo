package com.android.bean;

import java.util.Set;

public interface User {

	public abstract long getAccount();

	public abstract void setAccount(long account);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getPassword();

	public abstract void setPassword(String password);

	public abstract String getPhone();

	public abstract void setPhone(String phone);

	public abstract String getRegion();

	public abstract void setRegion(String region);

	public abstract String getArea();

	public abstract void setArea(String area);

	public abstract String getAddress();

	public abstract void setAddress(String address);

	public String getCardId();

	public void setCardId(String cardId);

	public abstract String getStuCard_ID();

	public abstract void setStuCard_ID(String stuCardID);

	public abstract byte[] getId_img();

	public abstract void setId_img(byte[] idImg);

	public abstract byte[] getStuCard_img();

	public abstract void setStuCard_img(byte[] stuCardImg);

	public abstract Set<Message> getMessage();

	public abstract void setMessage(Set<Message> message);

}