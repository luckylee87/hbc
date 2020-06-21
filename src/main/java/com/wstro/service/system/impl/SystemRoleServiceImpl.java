package com.wstro.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wstro.dao.system.SystemRoleMapper;
import com.wstro.dao.system.SystemUserRoleMapper;
import com.wstro.entity.system.SystemRole;
import com.wstro.entity.system.SystemUserRole;
import com.wstro.service.base.impl.BaseServiceImpl;
import com.wstro.service.system.ISystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemRoleServiceImpl
 * 类描述：SystemRole 表业务逻辑层接口实现类
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午11:40:57
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午11:40:57
 */
@Service("systemRoleService")
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRoleMapper, SystemRole> implements ISystemRoleService {

    @Autowired
    private SystemRoleMapper systemRoleMapper;
    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    /**
     * 查询角色列表
     *
     * @return List<SystemRole>
     */
    @Override
    public List<SystemRole> selectRoleList() {
        return systemRoleMapper.selectAllRole();
    }

    /**
     * 查询角色列表及数量
     *
     * @return List<SystemRole>
     */
    @Override
    public List<SystemRole> selectRoleAndNumber() {
        List<SystemRole> systemRoles = systemRoleMapper.selectAllRole();
        SystemUserRole systemUserRole = new SystemUserRole();
        for (int i = 0; i < systemRoles.size(); i++) {
            systemUserRole.setRoleId(systemRoles.get(i).getId());
            //int number = systemUserRoleMapper.selectCount(systemUserRole);
            int number = systemUserRoleMapper.selectCount(new QueryWrapper<SystemUserRole>(systemUserRole));
            systemRoles.get(i).setNumber(number);
        }
        return systemRoles;
    }
}