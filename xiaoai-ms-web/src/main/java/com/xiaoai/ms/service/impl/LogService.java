package com.xiaoai.ms.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.entity.XiaoiLogForMyql;
import com.xiaoai.ms.dao.ILogDao;
import com.xiaoai.ms.service.ILogService;
import com.xiaoai.util.XiaoiResult;

/**
 * Created by 曹子谞 on 2017/7/16.
 * proName:ihakul_server_7.12
 */

@Service("logService")
public class LogService implements ILogService {

    @Resource(name = "logDao")
    private ILogDao logDao;

    @Transactional
    @Override
    public XiaoiResult insertLog(XiaoiLogForMyql log) {
        return logDao.insertLog(log);
    }
}
