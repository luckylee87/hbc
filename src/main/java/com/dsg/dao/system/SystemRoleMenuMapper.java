package com.dsg.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsg.entity.system.SystemRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemRoleMenuMapper
 * 类描述：SystemRoleMenu 表数据访问层接口
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
public interface SystemRoleMenuMapper extends BaseMapper<SystemRoleMenu> {


    /**
     * 通过角色ID查找权限列表
     *
     * @param roleIdList 角色ID列表
     * @return List<SystemRoleMenu>
     */
    List<SystemRoleMenu> selectMenuListByRoleId(@Param("roleIdList") List<String> roleIdList);

}