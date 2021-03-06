package com.xiaoai.ms.dao;

import java.util.List;

import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;

/**
 * 用户家庭组关系对照表管理类
 * @author Administrator
 *
 */
public interface IFamilyUserDao {
	/**
	 * 根据用户家庭组关系对照表id查询
	 * @param id  用户家庭组关系对照表id
	 * @return
	 */
	public FamilyUser selectFamilyUserByid(int id);
	
	/**
	 * 按条件查询户家庭组关系对照表
	 * @param fu
	 * @return
	 */
	public List<FamilyUser> selectFamilyUser(FamilyUser fu, String hql);
	
	/**
	 * 修改户家庭组关系对照表
	 * @param fu
	 */
	public void updateFamilyUser(FamilyUser fu);
	/**
	 * 添加户家庭组关系对照表
	 * @param fu
	 */
	public void insertFamilyUser(FamilyUser fu);
	/**
	 * 删除户家庭组关系对照表
	 * @param fu
	 */
	public void deleteFamilyUser(FamilyUser fu);
	
	/**
	 * 根据用户id查询
	 * @param userId
	 */
	public List<FamilyUser> selectFamilyUserByuserid(int userId);
	/**
	 * 根据家庭组id查询
	 * @param groupId
	 * @return
	 */
	public List<FamilyUser> selectFamilyUserBygroupid(int groupId);
   
	
	/**
	 * 根据家庭组和用户查询
	 * @param Users Familygroup
	 * @return
	 */
	public  List<FamilyUser> selectFamilyUserByGNU(Users users, Familygroup familygroup);
}
