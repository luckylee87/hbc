package com.wstro.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wstro.entity.system.SystemRoleMenu;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：ISystemRoleMenuService
 * 类描述：SystemRoleMenu 表业务逻辑层接口
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 */
public interface ISystemRoleMenuService extends IService<SystemRoleMenu> {

    /**
     * 通过角色ID查找权限列表
     *
     * @param roleIdList 角色ID列表
     * @return List<SystemRoleMenu>
     */
    List<SystemRoleMenu> selectMenuListByRoleId(List<String> roleIdList);

}