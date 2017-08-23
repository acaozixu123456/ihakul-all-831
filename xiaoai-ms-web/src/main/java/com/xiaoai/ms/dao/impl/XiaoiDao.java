package com.xiaoai.ms.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.ms.dao.IXiaoiDao;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Room;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.util.XATools;
/**
 * 终端小艾操作实现类
 * @author Administrator
 *
 */
@Repository("xiaoiDao")
public class XiaoiDao implements IXiaoiDao {
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	//根据家庭组id查询
	@SuppressWarnings("unchecked")
	public List<Xiaoi> selectXiaoiByid(int fid) {
		String hql="from Xiaoi where groupId=? and state in (0,1)";
		return hibernateTemplate.find(hql,fid);
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(Xiaoi xiaoi) {
		hibernateTemplate.delete(xiaoi);

	}

	/**
	 * 根据编号查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Xiaoi selectXiaoiByNumber(String xiaoNumber) {
		String hql="from Xiaoi where xiaoNumber=? and state in (0,1)";
		List<Xiaoi> list=hibernateTemplate.find(hql,xiaoNumber);
		if(!XATools.isNull(list)){ 
			return list.get(0);
		}		
		return null;
	}

	
	
	@Override
	public void updateXiaoi(Xiaoi xiaoi) {	
		hibernateTemplate.update(xiaoi);
	}

	/**
	 * 按条件查询
	 */
	@SuppressWarnings({ "unchecked", "unused" })  
	public List<Xiaoi> findAll(String hql, int offset, int showPage,
			Xiaoi xiao) {
//		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Transaction trans=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(showPage);
		query.setProperties(xiao);
		List<Xiaoi> list=query.list();
		return list;
	}

	/**
	 * 得到总记录数
	 */
	public int getXiaoiCount(Xiaoi xiao,String hql) {
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query=session.createQuery(hql);
		query.setProperties(xiao);
		int count=((Long)query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 根据id查询
	 */
	public Xiaoi getXiaoiByid(int xid) {
		Xiaoi xiao=hibernateTemplate.get(Xiaoi.class, xid);
		return xiao;
	}

	/**
	 * 添加
	 */
	public void insertXiaoi(Xiaoi xiaoi) {
		hibernateTemplate.flush();
		hibernateTemplate.save(xiaoi);
	}

	//根据房间id查询
	@SuppressWarnings("unchecked")
	public List<Xiaoi> selectXiaoiByroomId(int roomId) {
		String hql="from Xiaoi where roomId=? and state in (0,1)";
		List<Xiaoi> list=hibernateTemplate.find(hql,roomId);
		return list;
	}
   
	
	
	
	//根据ip查询
	@SuppressWarnings("unchecked")
	public List<Xiaoi> selectXiaoiByIp(String ip) {
		String hql="from Xiaoi where xiaoiIp=? and state in (0,1)";
		List<Xiaoi> list=hibernateTemplate.find(hql,ip);
		return list;
	}

	
	@SuppressWarnings("unchecked")
	public List<Xiaoi> selectXiaoiByroom(Room room) {
		String hql="select xiaoai from Xiaoi as xiaoai join xiaoai.room as rooms where rooms=? and state in (0,1)";
		List<Xiaoi> list=hibernateTemplate.find(hql,room);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Xiaoi> selectXiaoiByFa(Familygroup fa) {
		String hql="from Xiaoi where state=1 and familygroup=?";
		return hibernateTemplate.find(hql,fa);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Xiaoi selectXiaoiByNumberAll(String xiaoNumber) {
		String hql="from Xiaoi where xiaoNumber=?";
		List<Xiaoi> list=hibernateTemplate.find(hql,xiaoNumber);
		if(!XATools.isNull(list)){ 
			return list.get(0);
		}		
		return null;
	}

	@Override
	public void setAllXiaoiOffLine() {
		String sql = "update xiaoi set state=0 where state=1";
		jdbcTemplate.update(sql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void batchUpdateXiaoiStateToOffLine(final List values) {
		String sql = "update xiaoi set state=0 where xiaoNumber =?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				String xiaoNumber = (String) values.get(index);
				ps.setString(1, xiaoNumber);
			}
			
			@Override
			public int getBatchSize() {
				//指定本批次的大小
				return values.size();
			}
		});
	}

	@Override
	public Xiaoi selectXiaoiAllByNumber(String xiaoiNumber) {
		String hql="from Xiaoi where xiaoNumber=?";
		List<Xiaoi> list=hibernateTemplate.find(hql,xiaoiNumber);
		if(!XATools.isNull(list)){ 
			return list.get(0);
		}		
		return null;
	}	

}
