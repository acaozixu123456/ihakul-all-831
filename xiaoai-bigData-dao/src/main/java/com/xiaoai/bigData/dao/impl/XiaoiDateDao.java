package com.xiaoai.bigData.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.xiaoai.bigData.dao.IXiaoiDateDao;
import com.xiaoai.entity.ErrorObj;
import com.xiaoai.entity.XiaoiDateForMongDb;
import com.xiaoai.util.FastJsonUtils;
import com.xiaoai.util.Page;

/**
 * @author ZERO
 * @Data 2017-8-1 下午12:23:04
 * @Description 
 */
@Repository("xiaoiDateDao")
public class XiaoiDateDao implements IXiaoiDateDao {

	@Resource(name="mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<XiaoiDateForMongDb> findAll() {
		DBCursor find = mongoTemplate.getCollection("hakudata").find(new BasicDBObject());
		
		XiaoiDateForMongDb bean = null;
		String json = null;
		BasicDBObject db = null;
		List<XiaoiDateForMongDb> arrayList = new ArrayList<>();
		for (Iterator iterator = find.iterator(); iterator.hasNext();) {
			db = (BasicDBObject) iterator.next();
			json = db.toJson();
			bean = FastJsonUtils.toBean(json, XiaoiDateForMongDb.class);
			arrayList.add(bean);
		}
		return arrayList;
	}

	@Override
	public void insertUser(XiaoiDateForMongDb data) {
		DBObject object = new BasicDBObject();  
        object.put("invokeMethod", data.getInvokeMethod());  
        object.put("invokeGroupNumber",String.valueOf(data.getInvokeGroupNumber())); 
        object.put("invokeXiaoNumber", data.getInvokeXiaoNumber());
        object.put("invokeUserName", data.getInvokeUserName());
        object.put("invokeParams",data.getInvokeParams());
        object.put("invokeTime", data.getInvokeTime());
        object.put("invokeOutParams", data.getInvokeOutParams());
        object.put("invokeObjNumber", data.getInvokeObjNumber());
		
        mongoTemplate.insert(object, "hakudata");
	}


	@Override
	public List<XiaoiDateForMongDb> findForRequery(String userName) {
		
		return null;
	}

	@Override
	public void removeUser(String userName) {
		
	}

	@Override
	public void updateUser(XiaoiDateForMongDb user) {
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findByPage(Page page) {
	    
	    DBCursor dbCursor = mongoTemplate
	    		.getCollection("hakudata")
	    		.find(new BasicDBObject())
	    		.skip(page.getOfferset())
	    		.limit(page.getShowPage());
	    ArrayList<XiaoiDateForMongDb> arrayList = new ArrayList<>();
	    BasicDBObject db = null;
	    XiaoiDateForMongDb bean = null;
	    for (Iterator iterator = dbCursor.iterator(); iterator.hasNext();) {
	    	db = (BasicDBObject) iterator.next();
			bean = FastJsonUtils.toBean(db.toJson(), XiaoiDateForMongDb.class);
			arrayList.add(bean);
		}
		return arrayList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findByPage(Page page,Map map) {
	    DBCursor dbCursor = mongoTemplate
	    		.getCollection("hakudata")
	    		.find(new BasicDBObject(map))
	    		.skip(page.getOfferset())
	    		.limit(page.getShowPage());
	    ArrayList<XiaoiDateForMongDb> arrayList = new ArrayList<>();
	    BasicDBObject db = null;
	    XiaoiDateForMongDb bean = null;
	    for (Iterator iterator = dbCursor.iterator(); iterator.hasNext();) {
	    	db = (BasicDBObject) iterator.next();
			bean = FastJsonUtils.toBean(db.toJson(), XiaoiDateForMongDb.class);
			arrayList.add(bean);
		}
		return arrayList;
	}
	
	
	@Override
	public long getCount() {
		return mongoTemplate.count(null,XiaoiDateForMongDb.class, "hakudata");
	}
	
	@Override
	public void insertErrorObg(ErrorObj errorObj){
		DBObject object = new BasicDBObject();  
        object.put("creattime", errorObj.getCreattime());  
        object.put("packageName",errorObj.getPackageName()); 
        object.put("alias", errorObj.getAlias());
        object.put("version", errorObj.getVersion());
        object.put("errormsg",errorObj.getErrormsg());
        object.put("envir", errorObj.getEnvir());
		
        mongoTemplate.insert(object, "hakuerror");
	}

}
