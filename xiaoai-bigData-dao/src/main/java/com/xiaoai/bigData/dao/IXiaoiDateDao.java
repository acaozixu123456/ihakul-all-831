package com.xiaoai.bigData.dao;

import java.util.List;
import java.util.Map;

import com.xiaoai.entity.ErrorObj;
import com.xiaoai.entity.XiaoiDateForMongDb;
import com.xiaoai.util.Page;


/**
 * @author ZERO
 * @Data 2017-8-1 上午11:57:41
 * @Description 小艾大数据dao
 */
public interface IXiaoiDateDao {

	/** 
     * 查询数据 
     *  
     * @author ZERO
     * @Title: findAll 
     * @param @return 
     * @date May 13, 2016 3:07:39 PM 
     * @throws 
     */  
    public List<XiaoiDateForMongDb> findAll();  
  
    /** 
     * 新增数据 
     *  
     * @author ZERO
     * @return void 
     * @Data 2017-8-1 上午11:57:41
     * @throws 
     */  
    public void insertUser(XiaoiDateForMongDb user);  
  
    /** 
     * 删除数据 
     *  
     * @author ZERO
     * @Title: removeUser 
     * @param @param userName 
     * @return void 
     * @Data 2017-8-1 上午11:57:41
     * @throws 
     */  
    public void removeUser(String userName);  
  
    /** 
     * 修改数据 
     *  
     * @author ZERO
     * @return void 
     * @Data 2017-8-1 上午11:57:41
     * @throws 
     */  
    public void updateUser(XiaoiDateForMongDb user);  
  
    /** 
     * 按条件查询 
     *  
     * @author ZERO
     * @Title: findForRequery 
     * @param 
     * @return void 
     * @Data 2017-8-1 上午11:57:41
     * @throws 
     */  
    public List<XiaoiDateForMongDb> findForRequery(String userName);

    /**
     * 分页查询
     * @param page
     * @return
     */
	@SuppressWarnings("rawtypes")
	public List findByPage(Page page);

	/**
     * 分页条件查询
     * @param page
     * @return
     */
	@SuppressWarnings("rawtypes")
	public List findByPage(Page page,Map map);
	/**
	 * 得到总记录数
	 * @return
	 */
	public long getCount();
	
	void insertErrorObg(ErrorObj errorObj);
}
