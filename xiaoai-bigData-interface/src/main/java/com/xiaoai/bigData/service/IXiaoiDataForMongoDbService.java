package com.xiaoai.bigData.service;

import java.util.List;
import java.util.Map;

import com.xiaoai.entity.ErrorObj;
import com.xiaoai.entity.XiaoiDateForMongDb;
import com.xiaoai.util.Page;


/**
 * @author ZERO
 * @Data 2017-8-1 下午1:45:37
 * @Description 
 */
public interface IXiaoiDataForMongoDbService {

	/** 
     * 查询数据 
     *  
     * @author ZERO
     * @Title: findAll 
     * @param @return 
     * @date May 13, 2016 3:07:39 PM 
     * @throws 
     */  
    List<XiaoiDateForMongDb> findAll();  
  
    /**
     * 分页查找数据
     * @return
     */
    @SuppressWarnings("rawtypes")
	List findByPage(Page page);
    
    /**
     * 分页条件查找数据
     * @return
     */
    @SuppressWarnings("rawtypes")
	List findByPage(Page page,Map map);
    
    long getCount();
    /** 
     * 新增数据 
     *  
     * @author ZERO
     * @return void 
     * @Data 2017-8-1 上午11:57:41
     * @throws 
     */  
    void insertUser(XiaoiDateForMongDb user);  
  
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
    void removeUser(String userName);  
  
    /** 
     * 修改数据 
     *  
     * @author ZERO
     * @return void 
     * @Data 2017-8-1 上午11:57:41
     * @throws 
     */  
    void updateUser(XiaoiDateForMongDb user);  
  
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
    List<XiaoiDateForMongDb> findForRequery(String userName);
    
    /**
     * 插入一条错误对象
     * @param errorObj
     */
    void insertErrorObj(ErrorObj errorObj);
}
