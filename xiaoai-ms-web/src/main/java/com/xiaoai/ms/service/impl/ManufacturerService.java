package com.xiaoai.ms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaoai.entity.Manufacturer;
import com.xiaoai.ms.dao.IManufacturerDao;
import com.xiaoai.ms.service.IManufacturerService;

@Service("manufacturerService")
public class ManufacturerService implements IManufacturerService {

	@Resource(name="manufacturerDao")
	private IManufacturerDao manufacturerDao;
	
	@Override
	public Manufacturer selectByName(String name) {
		List<Manufacturer> selectByName = manufacturerDao.selectByName(name);
		if(selectByName!=null&&selectByName.size()>0){
			Manufacturer manufacturer = selectByName.get(0);
			return manufacturer;
		}
		return null;
	}

	@Override
	public List<Manufacturer> getAll() {
       return manufacturerDao.getAll();
	}

}
