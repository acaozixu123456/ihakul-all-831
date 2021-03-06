package com.xiaoai.ms.dao;

import java.util.List;

import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;

/**
 * 家庭组dao层接口
 * @author Administrator
 *
 */
public interface IFamilygroupDao {
	/**
	 * 
	 * @param hql 查询条件
	 * @param offset 开始记录数
	 * @param length 一次查询记录数
	 * @return
	 */
	public List<Familygroup> queryFamilygroup(String haql, int offset, int length, Familygroup familygroup);

	/**
	 * 得到总记录数
	 * 
	 * @return 
	 */
	public int getAllRowCount(String hql, Familygroup familygroup);
	
	/**
	 * 添加
	 * @param family
	 * @return
	 */
	public void insertFamilygroup(Familygroup family);
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteFamilygroup(Familygroup family);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Familygroup getFamilygroupByid(int id);
	/**
	 * 根据编号查询
	 * @param groupNumber
	 * @return
	 */
	public Familygroup getFamilygroupByNumber(int groupNumber);
	/**
	 * 根据名字查询
	 * @param groupName
	 * @return
	 */
	public List<Familygroup> getFamilygroupByName(String groupName);
	/**
	 * 修改
	 */
	public void updateFamily(Familygroup familygroup);
	
	/**
	 * 根据时间段查询
	 * @return
	 */
	public List<Familygroup> selectFamilygroupBytimes(String beginTime, String lastTime);
	/**
	 * 根据用户查询其所关联的家庭组
	 * @param users
	 * @return
	 */
	public List<Familygroup> selectFamilygroupByusers(Users users);

	/**
	 * 根据家庭组查询其所关联的用户
	 * @param Familygroup
	 * @return
	 */
	public List<Users> selectusersByFamilygroup(Familygroup fa);

	/**
	 * 获得该家庭组所有信息（级联）
	 * @return
	 */
	public Familygroup getFamilygroupByNumberNow(int groupNumber);

	/**
	 * 修改家庭组地址
	 * @param familygroup
	 */
	public void updateFamilyAddress(Familygroup familygroup);

	/**
	 * 获得所有家庭组
	 * @return
	 */
	public List<Familygroup> getAllFamily();
}
