package com.xiaoai.ms.service;

import java.util.List;

import com.xiaoai.entity.Manufacturer;

/**
 * 厂商
 * @author Administrator
 *
 */
public interface IManufacturerService {

	Manufacturer selectByName(String name);

	/**
	 * 查询所有厂商
	 * @return
	 */
	List<Manufacturer> getAll();
}
