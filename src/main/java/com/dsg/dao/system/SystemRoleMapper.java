package com.dsg.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsg.entity.system.SystemRole;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemRoleMapper
 * 类描述：SystemRole 表数据访问层接口
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
public interface SystemRoleMapper extends BaseMapper<SystemRole> {

    /**
     * 查找所有角色
     *
     * @return
     */
    List<SystemRole> selectAllRole();

    /**
     * 通过角色ID删除角色权限关联
     *
     * @param roleId
     */
    void deleteRoleFunctionByRoleId(int roleId);

    /**
     * 创建角色权限关联
     *
     * @param value
     */
    void createRoleFunction(String value);

    /**
     * 获取角色对应的权限ID
     *
     * @param roleId
     * @return
     */
    List<String> queryRoleFunctionIdByRoleId(String roleId);


}