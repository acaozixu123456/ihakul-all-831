package com.xiaoai.bigData.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaoai.bigData.dao.IXiaoiDateDao;
import com.xiaoai.bigData.service.IXiaoiDataForMongoDbService;
import com.xiaoai.entity.ErrorObj;
import com.xiaoai.entity.XiaoiDateForMongDb;
import com.xiaoai.util.Page;

/**
 * @author ZERO
 * @Data 2017-8-1 下午1:46:53
 * @Description 
 */
@Service("xiaoiDataForMongoDbService")
public class XiaoiDataForMongoDbService implements IXiaoiDataForMongoDbService {

	@Resource(name="xiaoiDateDao")
	private IXiaoiDateDao xiaoiDateDao;
	
	@Override
	public List<XiaoiDateForMongDb> findAll() {
		return xiaoiDateDao.findAll();
	}

	@Override
	public void insertUser(XiaoiDateForMongDb data) {
		xiaoiDateDao.insertUser(data);
	}

	@Override
	public void removeUser(String userName) {

	}

	@Override
	public void updateUser(XiaoiDateForMongDb data) {

	}

	@Override
	public List<XiaoiDateForMongDb> findForRequery(String userName) {
		return null;
	}

	@Override
	public List findByPage(Page page) {
		return xiaoiDateDao.findByPage(page);
	}

	@Override
	public List findByPage(Page page, Map map) {
		return xiaoiDateDao.findByPage(page,map);
	}
	
	@Override
	public long getCount() {
		return xiaoiDateDao.getCount();
	}

	@Override
	public void insertErrorObj(ErrorObj errorObj) {
		xiaoiDateDao.insertErrorObg(errorObj);
	}


}
