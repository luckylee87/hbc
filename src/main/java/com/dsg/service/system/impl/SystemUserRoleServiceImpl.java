package com.dsg.service.system.impl;

import com.dsg.dao.system.SystemUserRoleMapper;
import com.dsg.entity.system.SystemUserRole;
import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.service.system.ISystemUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemUserRoleServiceImpl
 * 类描述：SystemUserRole 表业务逻辑层接口 实现类
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
@Service("systemUserRoleService")
public class SystemUserRoleServiceImpl extends BaseServiceImpl<SystemUserRoleMapper, SystemUserRole> implements ISystemUserRoleService {

    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    @Override
    public List<SystemUserRole> selectRoleListByAccountId(String userId) {
        return systemUserRoleMapper.selectRoleListByAccountId(userId);
    }


}