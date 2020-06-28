package com.dsg.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsg.entity.system.SystemUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemUserRoleMapper
 * 类描述：SystemUserRole 表数据访问层接口
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 */
@Repository
public interface SystemUserRoleMapper extends BaseMapper<SystemUserRole> {

    /**
     * 通过账号ID查找角色列表
     *
     * @param accountId 账号Id
     * @return List<SystemUserRole>
     */
    List<SystemUserRole> selectRoleListByAccountId(@Param("userId") String userId);

    /**
     * 插入用户角色记录
     *
     * @param systemUserRoles 角色列表
     */
    void insertUserRoles(@Param("systemUserRoles") List<SystemUserRole> systemUserRoles);
}