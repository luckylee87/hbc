package com.dsg.service.system.impl;

import com.dsg.dao.system.SystemUserLoginLogMapper;
import com.dsg.entity.system.SystemUserLoginLog;
import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.service.system.ISystemUserLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemUserLoginLogServiceImpl
 * 类描述：SystemUserLoginLog 表业务逻辑层接口实现类
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午11:40:57
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午11:40:57
 */
@Service("systemUserLoginLogService")
public class SystemUserLoginLogServiceImpl extends BaseServiceImpl<SystemUserLoginLogMapper, SystemUserLoginLog> implements ISystemUserLoginLogService {

    @Autowired
    private SystemUserLoginLogMapper systemUserLoginLogMapper;

    @Override
    public List<SystemUserLoginLog> selectUserLoginLog(String userId) {
        return systemUserLoginLogMapper.selectUserLoginLog(userId);
    }


}