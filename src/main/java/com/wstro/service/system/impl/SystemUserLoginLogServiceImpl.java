package com.wstro.service.system.impl;

import com.wstro.dao.system.SystemUserLoginLogMapper;
import com.wstro.entity.system.SystemUserLoginLog;
import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.service.system.ISystemUserLoginLogService;
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