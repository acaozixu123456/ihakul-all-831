package com.xiaoai.ms.dao;

import java.util.List;

import com.xiaoai.entity.Manufacturer;
import com.xiaoai.entity.Xiaoimac;

/**
 * 小iMAC
 * @author Administrator
 *
 */
public interface IXiaoiMacDao {

	/**
	 * 新增一个mac地址
	 * @param xiaoimac
	 * @return
	 */
	void insertXiaoiMac(Xiaoimac xiaoimac);

	/**
	 * 当前厂商下的终端数量
	 * @param manu
	 * @return
	 */
	Integer selectAmount(Manufacturer manu);

	/**
	 * 更新url下载地址
	 * @param xiaoimac
	 */
	void updateUrl(Xiaoimac xiaoimac);

	/**
	 * 得到总记录数
	 * @return
	 */
	int getXiaoiMacCount();

	/**
	 * 得到所有的MAC地址
	 * @param showPage 
	 * @param offset 
	 * @return
	 */
	List<Xiaoimac> getAll(int offset, int showPage);
}
