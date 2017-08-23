package com.xiaoai.ms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaoai.entity.Privilege;
import com.xiaoai.ms.dao.IPrivilegeDao;
import com.xiaoai.ms.service.IPrivilegeService;
@Service("privilegeService")
public class PrivilegeService implements IPrivilegeService {
	@Resource(name="privilegeDao")
	private IPrivilegeDao privilegeDao;
	public List<Privilege> findPrivilegeByid(int id) {
		
		return privilegeDao.findPrivilegeByid(id) ;
	}

}
