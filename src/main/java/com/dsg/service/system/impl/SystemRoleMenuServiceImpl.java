package com.dsg.service.system.impl;

import com.dsg.dao.system.SystemRoleMenuMapper;
import com.dsg.entity.system.SystemRoleMenu;
import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.service.system.ISystemRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemRoleMenuServiceImpl
 * 类描述：SystemRoleMenu 表业务逻辑层接口实现类
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午11:40:57
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午11:40:57
 */
@Service("systemRoleMenuService")
public class SystemRoleMenuServiceImpl extends BaseServiceImpl<SystemRoleMenuMapper, SystemRoleMenu> implements ISystemRoleMenuService {

    @Autowired
    private SystemRoleMenuMapper systemRoleMenuMapper;

    @Override
    public List<SystemRoleMenu> selectMenuListByRoleId(List<String> roleIdList) {
        return systemRoleMenuMapper.selectMenuListByRoleId(roleIdList);
    }


}