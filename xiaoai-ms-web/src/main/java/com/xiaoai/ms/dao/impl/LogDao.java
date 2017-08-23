package com.xiaoai.ms.dao.impl;

import com.xiaoai.ms.dao.ILogDao;
import com.xiaoai.entity.XiaoiLogForMyql;
import com.xiaoai.util.XiaoiResult;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by 曹子谞 on 2017/7/16.
 * proName:ihakul_server_7.12
 */
@Repository("logDao")
public class LogDao implements ILogDao{

    @Resource
    private HibernateTemplate template;
    @Override
    public XiaoiResult insertLog(XiaoiLogForMyql log) {
        template.save(log);
        return XiaoiResult.ok("新增操作日志成功");
    }
}
