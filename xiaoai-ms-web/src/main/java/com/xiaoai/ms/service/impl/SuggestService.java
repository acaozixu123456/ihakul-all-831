package com.xiaoai.ms.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.entity.Suggest;
import com.xiaoai.ms.dao.ISuggestDao;
import com.xiaoai.ms.service.ISuggestService;

/**
 * @author ZERO
 * @Data 2017-7-12 下午4:28:54
 * @Description 反馈信息service 
 */

@Service("suggestService")
public class SuggestService implements ISuggestService {

	@Resource(name="suggestDao")
	private ISuggestDao suggestDao;
	
	@Override
	@Transactional
	public void insertSuggest(Suggest suggest) {
		suggestDao.insertSuggest(suggest);
	}

}
