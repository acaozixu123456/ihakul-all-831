package com.xiaoai.ms.vo;

import java.io.Serializable;


public class FamilyVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 地址信息
	 */
	private String address;
	
	/**
	 * 数量
	 */
	private Integer count;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "FamilyVO [address=" + address + ", count=" + count + "]";
	}

	
	
	
}
