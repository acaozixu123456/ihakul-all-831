package com.xiaoai.ms.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.ms.dao.IManufacturerDao;
import com.xiaoai.entity.Manufacturer;

@Repository("manufacturerDao")
public class ManufacturerDao implements IManufacturerDao {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Manufacturer> selectByName(String name) {
		String hql = "from Manufacturer where name =?";
		List<Manufacturer> find = hibernateTemplate.find(hql,name);
		return find;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Manufacturer> getAll() {
		String hql = "from Manufacturer";
		return hibernateTemplate.find(hql);
	}

}
