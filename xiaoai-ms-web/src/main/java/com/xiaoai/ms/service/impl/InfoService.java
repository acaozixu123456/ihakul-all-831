package com.xiaoai.ms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.entity.Info;
import com.xiaoai.ms.dao.IInfoDao;
import com.xiaoai.ms.service.IInfoService;

/**
 * @author ZERO
 * @Data 2017-7-3 上午10:41:32
 * @Description 
 */
@Service("infoService")
public class InfoService implements IInfoService {

	@Resource
	private IInfoDao infoDao;
	
	@Override
	public List<Info> getAllInfo() {
		List<Info> allInfo = infoDao.getAllInfo();
		return allInfo;
	}

	@Transactional
	@Override
	public void insertInfo(Info info) {
		infoDao.insertInfo(info);
	}

}
