package com.xiaoai.ms.dao.impl;

import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.ms.dao.IVersionDao;
import com.xiaoai.entity.Versions;
/**
 * 版本持久化操作实现类
 * @author Administrator
 *
 */
@Repository("versionDao")
public class VersionDao implements IVersionDao {
	
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	//条件分页查询
	@SuppressWarnings("unchecked")
	public List<Versions> findAllVersion(String hql, int showPage,
			int beginNumber, Versions version) {
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
	//	Transaction trans=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setMaxResults(showPage);
		query.setFirstResult(beginNumber);
		query.setProperties(version);
		List<Versions> list=query.list();
		
		return list;
	}

	//得到总记录数
	public int getCountVersion(String hql, Versions version) {
		
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
	//	Transaction trans=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setProperties(version);
		int  count=((Long)query.uniqueResult()).intValue();
		return count;
	}

	//添加
	public void insertVersion(Versions version) {
		hibernateTemplate.flush();
		hibernateTemplate.save(version);
	}

	//根据id查询
	public Versions selectVersionByid(int id) {
		
		return hibernateTemplate.get(Versions.class, id);
	}

	//修改
	public void updateVersion(Versions version) {
		hibernateTemplate.update(version);
		
	}

	//删除
	public void deleteVrtsion(Versions version) {
		hibernateTemplate.delete(version);
		
	}

	
	@SuppressWarnings("unchecked")
	public List<Versions> selectVersionByverNumber(Versions versions) {
		String hql="from Versions as v  where  v.versionNumber=?";
		List<Versions> list=hibernateTemplate.find(hql,versions);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Versions> selectVersionByvr(Versions versions) {
		String hql="from Versions as v  where  v.versionNumber=? and v.versionPackage=?";
		List<Versions> list=hibernateTemplate.find(hql,versions.getVersionNumber(),versions.getVersionPackage());
		return list;
	}
	
	 
	@SuppressWarnings("unchecked")
	@Override
	public List<Versions> selectVersion() {
		
		return hibernateTemplate.find("from Versions");
	}
 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Versions selectMaxVersions(String versionPackage) {
		String hql = "from Versions vs where vs.versionPackage=?";
		List<Versions> vs = hibernateTemplate.find(hql,versionPackage);

		TreeMap treeMap = new TreeMap();
		for (Versions versions : vs) {
			Integer versionNumber = versions.getVersionNumber();
			treeMap.put(versionNumber, versions);
		}
		Entry lastEntry = treeMap.lastEntry();
		if(lastEntry!=null){
			Object value = lastEntry.getValue();
			if(value!=null&&value instanceof Versions){
				Versions reVersions = (Versions) value;
				return reVersions;
			}
		}
		return null;
	}
	
	
}
