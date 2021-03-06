package com.xiaoai.ms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Household;
import com.xiaoai.ms.dao.IHouseholdDao;
import com.xiaoai.ms.service.IHouseholdService;


/**
 * 家电业务操作实现类
 * 
 * @author Administrator
 * 
 */
@Service("houseHoldService")
public class HouseholdService implements IHouseholdService {
	@Resource(name = "houseHoldDao")
	private IHouseholdDao houseHoldDao;


	// 条件分页查询
	public List<Household> findHouseHold(int showPage, int effor,
			Household houseHold) {
		StringBuffer sql = new StringBuffer("from Household as house where 1=1");
		if (houseHold.getFamilygroup() != null) {
			sql.append(" and house.familygroup=:familygroup");
		}
		if (houseHold.getBrand() != null) {
			sql.append(" and house.brand=:brand");
		}
		if (houseHold.getEaName() != null) {
			sql.append(" and house.eaName=:eaName");
		}
		String hql = sql.toString();
		return houseHoldDao.findHousehold(showPage, effor, hql, houseHold);
	}

	// 得到总记录数
	public int getCountHouseHold(Household houseHold) {
		StringBuffer sql = new StringBuffer(
				"select count(*) from Household as house where 1=1");
		if (houseHold.getFamilygroup() != null) {
			sql.append(" and house.familygroup=:familygroup");
		}
		if (houseHold.getBrand() != null) {
			sql.append(" and house.brand=:brand");
		}
		if (houseHold.getEaName() != null) {
			sql.append(" and house.eaName=:eaName");
		}
		String hql = sql.toString();

		return houseHoldDao.getCountHouseHold(hql, houseHold);
	}

	@Transactional
	// 删除
	public boolean deleteHousehold(int hid) {
		boolean fals = true;
		try {
			Household houseHold = houseHoldDao.selectHouseholdByid(hid);
			houseHoldDao.deleteHousehold(houseHold);
		} catch (Exception e) {
			fals = false;
			e.printStackTrace();
		}

		return fals;
	}

	// 根据id查询
	public Household selectHouseholdByid(int id) {
		Household houseHold = houseHoldDao.selectHouseholdByid(id);
		return houseHold;
	}

	@Transactional
	// 修改
	public boolean updateHouseholdByid(Familygroup familygroup,Household houseHold) {
		boolean fals = true;
		try {
			houseHoldDao.updateHouseholdByid(houseHold);
		} catch (Exception e) {
			fals = false;
			e.printStackTrace();
		}
		return fals;
	}

	@Transactional
	// 添加
	public boolean insertHousehold(Familygroup familygroup,Household houseHold) {
		boolean fals = true;

		try {
			houseHoldDao.insertHousehold(houseHold);
		} catch (Exception e) {
			fals = false;
			e.printStackTrace();
		}

		return fals;
	}

	// 根据房间id查询家电
	public List<Household> selectHouseholdByroomID(int roomId) {
		List<Household> list = houseHoldDao.selectHouseholdByroomID(roomId);
		return list;
	}

	// 根据家电名字查询家电
	public List<Household> selectHouseholdByeaName(String eaName) {
		return houseHoldDao.selectHouseholdByeaName(eaName);
	}

	@Override
	public List<Household> selectHouseholdByroomIDandGroupId(Integer id,
			Integer groupId) {
		return houseHoldDao.selectHouseholdByroomIDandGroupId(id, groupId);
	}

	@Override
	public List<Household> getRoomByRoomNumber1(Familygroup family,
			String eaNumber) {
		List<Household> households = null;
		households = houseHoldDao.getRoomByRoomNumber1(family, eaNumber);
		return households;
	}

	@Override
	public List<Household> selectHouseholdBygroupId(Integer groupId) {
		return houseHoldDao.selectHouseholdBygroupId(groupId);
	}

	@Transactional
	@Override
	public boolean deleteHousehold(Familygroup familygroup,Household houseHold) {
		boolean flag = true;
		try {
			//执行删除
			houseHoldDao.deleteHousehold(houseHold);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<Household> findAllHouseHold() {
		return	houseHoldDao.findAllHouseHold();
	}

}
