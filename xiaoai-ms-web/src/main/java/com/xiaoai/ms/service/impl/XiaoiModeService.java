package com.xiaoai.ms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.entity.XiaoiMode;
import com.xiaoai.ms.dao.IXiaoiModeDao;
import com.xiaoai.ms.service.IXiaoiModeService;

/**
 * @author ZERO
 * @Data 2017-6-22 下午6:30:26
 * @Description 
 */
@Service("xiaoiModeService")

public class XiaoiModeService implements IXiaoiModeService {

	@Resource(name="xiaoiModeDao")
	private IXiaoiModeDao xiaoiModeDao;
	@Override
	@Transactional
	public boolean insertMode(XiaoiMode xiaoiMode) {
		/*boolean flag = true;
		try {*/
			xiaoiModeDao.insertMode(xiaoiMode);
		/*} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;*/
			return true;
	}
	
	@Override
	@Transactional
	public boolean deleteMode(XiaoiMode xiaoiMode) {
		boolean flag = true;
		try {
			xiaoiModeDao.deleteMode(xiaoiMode);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public List<XiaoiMode> findModeById(int id,int groupNumber) {
		return xiaoiModeDao.findModeById(id,groupNumber);
	}

	@Override
	public List<XiaoiMode> findModeByGroupNum(int groupNumber) {
		 return xiaoiModeDao.findAllModeByGroupNum(groupNumber);
	}

	@Override
	public List<XiaoiMode> findById(long id) {
		return xiaoiModeDao.findById(id);
	}

	/*@Override
	public List<XiaoiMode> findByModeId(Integer integer) {
		
		return xiaoiModeDao.findModeById(integer);
	}*/

}
