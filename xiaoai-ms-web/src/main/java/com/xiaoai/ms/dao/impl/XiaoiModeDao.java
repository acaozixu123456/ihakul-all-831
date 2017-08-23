package com.xiaoai.ms.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.ms.dao.IXiaoiModeDao;
import com.xiaoai.entity.XiaoiMode;

/**
 * @author ZERO
 * @Data 2017-6-22 下午6:25:06
 * @Description 
 */
@Repository("xiaoiModeDao")
public class XiaoiModeDao implements IXiaoiModeDao {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insertMode(XiaoiMode xiaoiMode) {
		hibernateTemplate.flush();
		hibernateTemplate.save(xiaoiMode);
	}
	@Override
	public void deleteMode(XiaoiMode xiaoiMode) {
		hibernateTemplate.delete(xiaoiMode);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<XiaoiMode> findModeById(int id,int groupNumber) {
		List<XiaoiMode> list = hibernateTemplate.find("from XiaoiMode where groupNumber = ? and mode = ?",groupNumber,id);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<XiaoiMode> findAllModeByGroupNum(int groupNumber) {
		List<XiaoiMode> list = hibernateTemplate.find("from XiaoiMode where groupNumber = ?",groupNumber);
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<XiaoiMode> findById(long id) {
		List<XiaoiMode> list = hibernateTemplate.find("from XiaoiMode where id = ?",id);
		return list;
	}
	@Override
	public void deleteModeByGroupNuberAndEaNumber(String eaNumber,
			Integer groupNumber) {
		String sql = "delete from xiaoimode where eaNumber=? and groupNumber=?";
		jdbcTemplate.update(sql, eaNumber,groupNumber);
		//清除hibernate中的缓存
		hibernateTemplate.flush();
	}

}
