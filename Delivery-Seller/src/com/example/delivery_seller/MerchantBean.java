package com.example.delivery_seller;

import java.util.HashSet;
import java.util.Set;

public class MerchantBean implements Merchant {
	private long Account;
	private String name;
	private String Phone;

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

	public MerchantBean() {
	}

}
