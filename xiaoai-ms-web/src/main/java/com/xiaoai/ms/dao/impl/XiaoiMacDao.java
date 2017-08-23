package com.xiaoai.ms.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.ms.dao.IXiaoiMacDao;
import com.xiaoai.entity.Manufacturer;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.entity.Xiaoimac;

@Repository("xiaoiMacDao")
public class XiaoiMacDao implements IXiaoiMacDao{

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void insertXiaoiMac(Xiaoimac xiaoimac) {
		hibernateTemplate.save(xiaoimac);
	}

	@Override
	public Integer selectAmount(Manufacturer manu) {
		String hql = "from Xiaoimac where manufacturer=?";
		List find = hibernateTemplate.find(hql,manu.getId());
		if(find!=null){
			if(find.size()>0){
				return find.size();
			}else{
				return 0;
			}
		}
		return 0;
	}

	@Override
	public void updateUrl(Xiaoimac xiaoimac) {
		hibernateTemplate.update(xiaoimac);
	}

	@Override
	public int getXiaoiMacCount() {
		String hql = "select count(*) from Xiaoimac where 1=1";
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(hql);
		int count=((Long)query.uniqueResult()).intValue();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Xiaoimac> getAll(int offset, int showPage) {
		String hql = "from Xiaoimac";
		
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(showPage);
		List<Xiaoimac> list=query.list();
		return list;
	}

}
