package com.android.bean;

import java.util.HashSet;
import java.util.Set;

public class MerchantBean implements Merchant {
	private long Account;
	private String name;
	private String Phone;
	private String Password;
	private Set<Message> message = new HashSet<Message>();

	public long getAccount() {
		return this.Account;
	}

	public void setAccount(long account) {
		this.Account = account;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.Phone;
	}

	public void setPhone(String phone) {
		this.Phone = phone;
	}

	public String getPassword() {
		return this.Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public Set<Message> getMessage() {
		return this.message;
	}

	public void setMessage(Set<Message> message) {
		this.message = message;
	}

	public MerchantBean(String name) {
		this.Password = name;
	}

	public MerchantBean() {
	}
}
