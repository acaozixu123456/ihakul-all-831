package com.xiaoai.ms.service;


import java.util.List;

import com.xiaoai.entity.Manufacturer;
import com.xiaoai.entity.Xiaoimac;
import com.xiaoai.util.XiaoiResult;

/**
 * 小iMAC
 * @author Administrator
 *
 */
public interface IXiaoiMacService {

	/**
	 * 
	 * @param manufacturer 厂商编号
	 * @return
	 */
	Xiaoimac insertXiaoiMac(int manufacturer,Integer count,String BYTE1,String BYTE7);

	/**
	 * 查询当前厂商下的终端数量
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
	 * 得到mac地址总记录数
	 * @return
	 */
	int getXiaoiMacCount();

	/**
	 * 获取所有mac地址
	 * @param showPage 
	 * @param offset 
	 * @return
	 */
	List<Xiaoimac> getAll(int offset, int showPage);
}
