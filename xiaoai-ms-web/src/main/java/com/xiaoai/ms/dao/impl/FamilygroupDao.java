package com.xiaoai.ms.dao.impl;





import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.ms.dao.IFamilygroupDao;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
import com.xiaoai.util.XATools;
/**
 * 家庭组dao实现方法
 * @author Administrator
 *
 */
@Repository("familyDao")
public class FamilygroupDao implements IFamilygroupDao {
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings({ "unchecked", "unused" })  
	public List<Familygroup> queryFamilygroup(String hql, int offset,int length,Familygroup familygroup) {
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Transaction trans=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setFirstResult(offset);
        query.setMaxResults(length);
        query.setProperties(familygroup);
        List<Familygroup> list=query.list();      
		return list; 
	}

	
	/**
	 * 得到总记录数
	 */
	@SuppressWarnings("unused")
	public int getAllRowCount(String hql,Familygroup familygroup) {
		
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Transaction trans=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setProperties(familygroup);
		int  count=((Long)query.uniqueResult()).intValue();
		return count;	
		
	}

	/**添加
	 * 
	 */
	public void insertFamilygroup(Familygroup family) {
		
			hibernateTemplate.save(family);
		
	}
	/**
	 * 修改
	 */
	public void updateFamily(Familygroup familygroup) {
		hibernateTemplate.update(familygroup);
		
	}
     
	/**
	 * 删除
	 */
	public void deleteFamilygroup(Familygroup family) {
		//解决一个session中有两个familygroup
		//hibernateTemplate.merge(Familygroup.class);
		hibernateTemplate.delete(family);
		//org.hibernate.classic.Session currentSession = hibernateTemplate.getSessionFactory().getCurrentSession();
		//currentSession.createQuery("delete Familygroup where groupId=:groupId").setInteger("groupId", family.getGroupId()).executeUpdate();
		
		//hibernateTemplate.deleteAll(entities)
	}

	/**
	 * 根据id查询
	 */
	public Familygroup getFamilygroupByid(int id) {
		return hibernateTemplate.get(Familygroup.class, id);
	}

	/**
	 * 根据编号查询
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Familygroup getFamilygroupByNumber(int groupNumber) {
		String hql="from Familygroup where groupNumber=?";
		 List<Familygroup> fa=  hibernateTemplate.find(hql,groupNumber);
		 if(!XATools.isNull(fa)){
			 Familygroup familygroup = fa.get(0);
			 /*
			 System.out.println("-----------------"+familygroup.getUsers());
			 System.out.println(familygroup.getFamilyUsers());
			 System.out.println(familygroup.getHouseholds());
			 System.out.println(familygroup.getRooms());
			 System.out.println(familygroup.getUsers()+""+familygroup.getXiaois());*/
			 return familygroup;
		 }
		return  null;
	}

	

	//根据家庭组名字查询
	@SuppressWarnings("unchecked")
	public List<Familygroup> getFamilygroupByName(String groupName) {
		String hql="from Familygroup where groupName=?";
		return hibernateTemplate.find(hql,groupName);
	}

	//根据时间段查询
	@SuppressWarnings("unchecked")
	public List<Familygroup> selectFamilygroupBytimes(String beginTime,
			String lastTime) {
		String sql="select * from familygroup where creationTime >=? and creationTime<=?";
		List<Familygroup> list = hibernateTemplate.find(sql,beginTime,lastTime);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Familygroup> selectFamilygroupByusers(Users users) {
		String sql="select family from Familygroup as family join family.users as user where user=?";
		List<Familygroup> list=hibernateTemplate.find(sql,users);
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Users> selectusersByFamilygroup(Familygroup fa) {
		String sql="select user from Users as user join user.familygroup as fa where fa=?";
		List<Users> list = hibernateTemplate.find(sql,fa);
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Familygroup getFamilygroupByNumberNow(int groupNumber) {
		String hql="from Familygroup where groupNumber=?";
		List<Familygroup> fa = hibernateTemplate.find(hql,groupNumber);
		 if(!XATools.isNull(fa)){
			 Familygroup familygroup = fa.get(0);
			 return familygroup;
		 }
		return null;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void updateFamilyAddress(final Familygroup familygroup) {
		final String sql = "update familygroup f set f.state =:state ,f.city =:city ,f.district =:district where f.groupNumber =:groupNumber";
		hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery createSQLQuery = session.createSQLQuery(sql);
				createSQLQuery.setParameter("state", familygroup.getState());
				createSQLQuery.setParameter("city", familygroup.getCity());
				createSQLQuery.setParameter("district", familygroup.getDistrict());
				createSQLQuery.setParameter("groupNumber", familygroup.getGroupNumber());
				createSQLQuery.executeUpdate();
				return null;
			}
		});
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Familygroup> getAllFamily() {
		String hql = "from Familygroup where 1=1";
		return hibernateTemplate.find(hql);
	}
	
	
}
