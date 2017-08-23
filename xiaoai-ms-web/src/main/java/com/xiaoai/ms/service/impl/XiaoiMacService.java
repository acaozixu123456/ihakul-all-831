package com.xiaoai.ms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.entity.Manufacturer;
import com.xiaoai.entity.Xiaoimac;
import com.xiaoai.ms.dao.IXiaoiMacDao;
import com.xiaoai.ms.service.IXiaoiMacService;
import com.xiaoai.util.Base64Utils;

@Service("xiaoiMacService")
public class XiaoiMacService implements IXiaoiMacService{

	@Resource(name="xiaoiMacDao")
	private IXiaoiMacDao xiaoiMacDao;
	
	@Transactional
	@Override
	public Xiaoimac insertXiaoiMac(int manufacturer,Integer count,String BYTE1,String BYTE7) {
		
		int type = 0x00; 					// 目标设备类型 		-BYTE0
		int commChannel = Integer.parseInt(BYTE1); 			// 设备支持的通信通道 	-BYTE1
		int code_of_GC_device = count;	// 设备厂商代码 		-BYTE4 -> BYTE6
		int version = Integer.parseInt(BYTE7); 				// 智能网关的特定类型 	-BYTE7

		// Step1
		byte uuid_bytes[] = new byte[8];
		uuid_bytes[0] = (byte) type;
		uuid_bytes[1] = (byte) commChannel;
		uuid_bytes[2] = (byte) ((manufacturer >> 8) & 0xff);
		uuid_bytes[3] = (byte) (manufacturer & 0xff);
		uuid_bytes[4] = (byte) ((code_of_GC_device >> 16) & 0xff);
		uuid_bytes[5] = (byte) ((code_of_GC_device >> 8) & 0xff);
		uuid_bytes[6] = (byte) (code_of_GC_device & 0xff);
		uuid_bytes[7] = (byte) version;

		// Step2 print
		String UUID_str = Base64Utils.toHexString(uuid_bytes);


		Xiaoimac xiaoimac = new Xiaoimac();
		xiaoimac.setMac(UUID_str);
		xiaoimac.setCreattime(new Date());
		xiaoimac.setManufacturer(manufacturer);
		xiaoimac.setState(0);
		xiaoiMacDao.insertXiaoiMac(xiaoimac);
		return xiaoimac;
	}

	@Override
	public Integer selectAmount(Manufacturer manu) {
		return xiaoiMacDao.selectAmount(manu);
	}

	@Transactional
	@Override
	public void updateUrl(Xiaoimac xiaoimac) {
		xiaoiMacDao.updateUrl(xiaoimac);
	}

	@Override
	public int getXiaoiMacCount() {
		return xiaoiMacDao.getXiaoiMacCount();
	}

	@Override
	public List<Xiaoimac> getAll(int offset, int showPage) {
		return xiaoiMacDao.getAll(offset,showPage);
	}

}
