package com.jbp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Company implements Serializable {
	private String cname;
	private String address;
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	@Override
	public String toString() {
		return "Company [cname=" + cname + ", address=" + address + "]";
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
