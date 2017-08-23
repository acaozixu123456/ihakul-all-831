package com.xiaoai.ms.vo;

import java.io.Serializable;

public class UserVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 数量
	 */
	private Integer count;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "UserVO [sex=" + sex + ", count=" + count + "]";
	}

	
}
