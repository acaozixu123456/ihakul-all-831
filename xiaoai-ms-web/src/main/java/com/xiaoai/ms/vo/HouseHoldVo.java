package com.xiaoai.ms.vo;

import java.io.Serializable;
/**
 * 家电VO
 * @author Administrator
 *
 */
public class HouseHoldVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 家电名称
	 */
	private String householdName;
	
	/**
	 * 个数
	 */
	private Integer count;

	public String getHouseholdName() {
		return householdName;
	}

	public void setHouseholdName(String householdName) {
		this.householdName = householdName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "HouseHoldVo [householdName=" + householdName + ", count=" + count + "]";
	}
	
	
}
