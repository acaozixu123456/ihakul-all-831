package com.xiaoai.ms.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.ms.dao.ISuggestDao;
import com.xiaoai.entity.Suggest;

/**
 * @author ZERO
 * @Data 2017-7-12 下午4:29:25
 * @Description 
 */
@Repository("suggestDao")
public class SuggestDao implements ISuggestDao{

	@Resource
	private HibernateTemplate hibernateTemplate;
	@Override
	public void insertSuggest(Suggest suggest) {
		hibernateTemplate.save(suggest);
	}

	
}
