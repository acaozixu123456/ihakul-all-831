package com.xiaoai.ms.dao;

import com.xiaoai.entity.Suggest;

/**
 * @author ZERO
 * @Data 2017-7-12 下午4:29:10
 * @Description 反馈dao
 */
public interface ISuggestDao {

	/**
	 * 新增反馈信息
	 * @param suggest
	 */
	void insertSuggest(Suggest suggest);
}
