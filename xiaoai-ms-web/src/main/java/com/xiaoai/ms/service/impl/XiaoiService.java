package com.xiaoai.ms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Household;
import com.xiaoai.entity.Room;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.entity.Xiaoilog;
import com.xiaoai.ms.dao.IHouseholdDao;
import com.xiaoai.ms.dao.IRoomDao;
import com.xiaoai.ms.dao.IXiaoiDao;
import com.xiaoai.ms.dao.IXiaoiModeDao;
import com.xiaoai.ms.dao.IXiaoilogDao;
import com.xiaoai.ms.service.IXiaoiService;
import com.xiaoai.util.XATools;
/**
 * 小艾业务操作实现类
 * @author Administrator
 *
 */
@Service("xiaoiService")
public class XiaoiService implements IXiaoiService {
	@Resource(name="xiaoiDao")
	private IXiaoiDao xiaoiDao;
	
	@Resource(name="xiaoilogDao")
	private IXiaoilogDao xiaoilogDao;
	
	@Resource(name = "roomDao")
	private IRoomDao roomDao;
	
	@Resource(name = "houseHoldDao")
	private IHouseholdDao houseHoldDao;
	
	@Resource(name="xiaoiModeDao")
	private IXiaoiModeDao xiaoiModeDao;
	
	@Override
	public List<Xiaoi> selectXiaoiByid(int fid) {
		
		return xiaoiDao.selectXiaoiByid(fid);
	}

	/**
	 * 删除终端
	 */
	@Transactional
	public boolean delete(Xiaoi xiaoi) {
		boolean fals=true;
		int xid=xiaoi.getXid();
		String xiaoNumber=xiaoi.getXiaoNumber();
		try{
			List<Xiaoilog> xiaoilogList=xiaoilogDao.selectXiaoilogByxid(xid);
			if(!XATools.isNull(xiaoilogList)){
				for (Xiaoilog xiaoilog : xiaoilogList) {
				   xiaoilogDao.deleteXiaoilogByid(xiaoilog);
				}
			 }
			  List<Room> roomlist=roomDao.getRoomByCreateor(xiaoNumber);
			  if(!XATools.isNull(roomlist)){
				  for(Room room:roomlist){
					  //先删除电器
					  int id=room.getId();
						try {
							/*XiaoiMode xiaoiMode = new XiaoiMode();*/
							List<Household> houseHoldList=houseHoldDao.selectHouseholdByroomID(id);
							if(!XATools.isNull(houseHoldList)){
								for (Household household : houseHoldList) {
									houseHoldDao.deleteHousehold(household);//删除该房间下关联的家电信息
									//删除电器对应的情景模式
									xiaoiModeDao.deleteModeByGroupNuberAndEaNumber(household.getEaNumber(),household.getFamilygroup().getGroupNumber());
								}
							}
						
							roomDao.deleteRoom(room);
						} catch (Exception e) {
							fals=false;
							e.printStackTrace();
						}
				  }
			  }
			
			//xiaoiDao.delete(xiaoi);  //假删除小艾
		}catch(Exception e){
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}
  
	/** 
	 * 更换终端
	 */
	@Transactional
	@Override
	public boolean change(String xiaoNumber, String newxiaoNumber) {
		boolean fals=true;
		try{
			  List<Room> roomlist=roomDao.getRoomByCreateor(xiaoNumber); //查询旧终端所创建的房间
			  if(!XATools.isNull(roomlist)){
				  for(Room room:roomlist){ 
					  room.setCreator(newxiaoNumber);               //将旧终端编号替换为新终端的编号
					  if(!XATools.isNull(room.getRobot())){        //如果该房间的默认编号是旧终端编号，则改为新终端编号
						  room.setRobot(newxiaoNumber); 
					  }
					  roomDao.updateRoom(room);
				  }
			  }
			
		}catch(Exception e){
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}
	
	
	/**
	 * 根据小艾编号查询
	 */
	public Xiaoi selectXiaoiByNumber(String  xiaoNumber) {
		return xiaoiDao.selectXiaoiByNumber(xiaoNumber);
		
	}

	/**
	 * 修改
	 */
	@Transactional
	public boolean updateXiaoi(Xiaoi xiaoi) {
		boolean fals=true;
		try {
			xiaoi.setActivationTime(XATools.getNowTime());
			xiaoiDao.updateXiaoi(xiaoi);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
			
		}
		return fals;
	}

	/**
	 * 条件分页查询
	 */
	public List<Xiaoi> findAll(int offset, int showPage, Xiaoi xiao) {
		StringBuilder sql=new StringBuilder("from Xiaoi as x where 1=1");
		if(xiao.getXiaoNumber() !=null){
			sql.append(" and x.xiaoNumber=:xiaoNumber");
		}
		
		if(xiao.getFamilygroup() !=null){
			
			sql.append(" and x.familygroup=:familygroup");
		}
		if(xiao.getXiaoIp() !=null ){
			sql.append(" and x.xiaoIp=:xiaoIp");
		}
		List<Xiaoi> list=xiaoiDao.findAll(sql.toString(), offset, showPage, xiao);
		
		return list;
	}

	/**
	 * 得到总记录数
	 */
	@SuppressWarnings("unused")
	public int getXiaoiCount(Xiaoi xiao) {
		StringBuilder sql=new StringBuilder("select count(*) from Xiaoi as x where 1=1");
		if(xiao.getXiaoNumber() !=null){
			sql.append(" and x.xiaoNumber=:xiaoNumber");
		}
		if(xiao.getFamilygroup() !=null){
			int groupid=xiao.getFamilygroup().getGroupId();
			sql.append(" and x.familygroup=:familygroup");
		}
		
		int count=(int)xiaoiDao.getXiaoiCount(xiao, sql.toString());
		return count;
	}

	/**
	 * 根据id查询
	 */
	public Xiaoi getXiaoiByid(int xid) {
		return xiaoiDao.getXiaoiByid(xid);
	}

	/**
	 * 添加
	 */
	@Transactional
	public boolean insertXiaoi(Xiaoi xiaoi) {
		boolean fals=true;
		try {
			xiaoi.setActivationTime(XATools.getNowTime());
			xiaoiDao.insertXiaoi(xiaoi);
		} catch (Exception e) {
			fals=false;
		}
		
		return fals;
	}

	//根据房间号id查询
	public List<Xiaoi> selectXiaoiByroomId(int roomId) {
		List<Xiaoi> list=xiaoiDao.selectXiaoiByroomId(roomId);
		return list;
	}

	//根据IP查询
	public List<Xiaoi> selectXiaoiByIp(String ip) {
		List<Xiaoi> list=xiaoiDao.selectXiaoiByIp(ip);
		return list;
	}

	@Override
	public Xiaoi selectXiaoiByFa(Familygroup fa) {
		List<Xiaoi> list=xiaoiDao.selectXiaoiByFa(fa); //查询所有在线的终端
		if(!XATools.isNull(list)){ //若存在在线的终端，默认取第一个
			return list.get(0);
		}
		return null;
	}

	@Override
	public Xiaoi selectXiaoiByNumberAll(String xiaoNumber) {
		return xiaoiDao.selectXiaoiByNumberAll(xiaoNumber);
	}

	@Transactional
	@Override
	public void cleanInfo(Xiaoi xiaoi) {
		xiaoi.setFamilygroup(null);
		xiaoi.setXname(null);
		xiaoi.setMode(0);
		xiaoi.setState(3);
		xiaoi.setXiaoIp(null);
		xiaoi.setXiaoType(0);
		//更新
		updateXiaoi(xiaoi);
	}

	@Override
	public List<Xiaoi> selectXiaoiByFaAll(Familygroup family) {
		//redis缓存
		
		return xiaoiDao.selectXiaoiByFa(family);
	}

	@Transactional
	@Override
	public void setAllXiaoiOffLine() {
		xiaoiDao.setAllXiaoiOffLine();
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	@Override
	public void batchUpdateXiaoiStateToOffLine(List values) {
		xiaoiDao.batchUpdateXiaoiStateToOffLine(values);
	}

	@Override
	public Xiaoi selectXiaoiAllByNumber(String xiaoiNumber) {
		return 	xiaoiDao.selectXiaoiAllByNumber(xiaoiNumber);
	}

	

}
