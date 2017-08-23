package com.xiaoai.ms.dao;

import com.xiaoai.entity.XiaoiLogForMyql;
import com.xiaoai.util.XiaoiResult;

/**日志
 * Created by 曹子谞 on 2017/7/16.
 * proName:ihakul_server_7.12
 */
public interface ILogDao {

    /**
     * 新增操作日志
     * @param log
     * @return
     */
    XiaoiResult insertLog(XiaoiLogForMyql log);
}
