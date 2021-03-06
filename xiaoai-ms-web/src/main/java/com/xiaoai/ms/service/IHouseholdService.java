package com.xiaoai.ms.service;

import java.util.List;



import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Household;

/**
 * 家电业务操作接口
 * @author Administrator
 *
 */
public interface IHouseholdService {
	/**
	 * 条件分页查询
	 * @param showPage 每页显示最大记录数
	 * @param effor  每页开始记录数
	 * @param houseHold 家电对象
	 * @return 返回查询的家电对象集合
	 */
	public List<Household> findHouseHold(int showPage, int effor, Household houseHold);
	/**
	 * 得到总记录数
	 * @param houseHold 家电对象
	 * @return 查询出的总记录数
	 */
	public int getCountHouseHold(Household houseHold);
	
	/**
	 * 删除
	 * @param houseHold 家电对象
	 * @return 删除成功(true)与失败(false)的判断标志位
	 */
	public boolean deleteHousehold(int hid);
	/**
	 * 根据id查询
	 * @param id 家电id
	 * @return 家电对象
	 */
	public Household selectHouseholdByid(int id);
	
	/**
	 * 修改
	 * @param houseHold 家电对象
	 * @return 修改成功(true)与失败(false)的判断标志位 
	 */
	public boolean updateHouseholdByid(Familygroup familygroup,Household houseHold);
	
	/**
	 * 添加
	 * @param houseHold 家电对象
	 * @return 添加成功(true)与失败(false)的判断标志位
	 */
	public boolean insertHousehold(Familygroup familygroup,Household houseHold);
	/**
	 * 根据房间号查询家电
	 * @param roomId 房间id
	 * @return 查询出的家电对象集合
	 */
	public List<Household> selectHouseholdByroomID(int roomId) ;
	/**
	 * 根据家电名字查询
	 * @param eaName 家电名字
	 * @return 查询出的家电对象集合
	 */
	public List<Household> selectHouseholdByeaName(String eaName);
	/**
	 * 根据家庭组ID和房间ID查询
	 * @param groupId,roomId
	 * @return
	 */
	public List<Household> selectHouseholdByroomIDandGroupId(Integer id,
                                                             Integer groupId);
	
	/**
	 * 根据家庭组和eaNumber来查找电器
	 * @param family
	 * @param string
	 * @return
	 */
	public List<Household> getRoomByRoomNumber1(Familygroup family,
                                                String string);
	/**
	 * 根据家庭组编号查询
	 * @param groupId
	 * @return
	 */
	public List<Household> selectHouseholdBygroupId(Integer groupId);
	/**
	 * 删除家庭组
	 * @param houseHold
	 * @return
	 */
	public boolean deleteHousehold(Familygroup familygroup,Household houseHold);
	/**
	 * 查询所有家电，用于echart图表绘制
	 * @return
	 */
	public List<Household> findAllHouseHold();
}
