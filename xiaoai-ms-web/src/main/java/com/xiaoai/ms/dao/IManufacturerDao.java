package com.xiaoai.ms.dao;

import java.util.List;

import com.xiaoai.entity.Manufacturer;

/**
 * 厂商
 * @author Administrator
 *
 */
public interface IManufacturerDao {

	List<Manufacturer> selectByName(String name);

	/**
	 * 查询所有厂商
	 * @return
	 */
	List<Manufacturer> getAll();
}
