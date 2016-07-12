package com.example.delivery_First;
/**
 * Gsonbean,返回给客户端的数据
 */
import java.sql.Timestamp;


public class MessageGson {

	private Long ID;
	private String Phone;
	private String Merchant;
	private String Address;
	private Timestamp SendTime;
	private Integer State;
	private String Region;
	private String Area;

	public MessageGson() {
		this.SendTime = new Timestamp(System.currentTimeMillis());
	}

	public Long getID() {
		return this.ID;
	}

	public void setID(Long iD) {
		this.ID = iD;
	}


	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		this.Phone = phone;
	}
	public String getMerchant() {
		return Merchant;
	}

	public void setMerchant(String merchant) {
		Merchant = merchant;
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

	public Integer getState() {
		return State;
	}

	public void setState(Integer state) {
		State = state;
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

	@Override
	public String toString() {
		return "MessageGson [Address=" + Address + ", ID=" + ID + ", Merchant="
				+ Merchant + ", SendTime=" + SendTime + ", Phone=" + Phone
				+ "]";
	}

}
