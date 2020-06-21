package com.wstro.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wstro.entity.system.SystemRole;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：ISystemRoleService
 * 类描述：SystemRole 表业务逻辑层接口
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
public interface ISystemRoleService extends IService<SystemRole> {

    /**
     * 查询角色列表
     *
     * @return List<SystemRole>
     */
    List<SystemRole> selectRoleList();

    /**
     * 查询角色列表及数量
     *
     * @return List<SystemRole>
     */
    List<SystemRole> selectRoleAndNumber();

}