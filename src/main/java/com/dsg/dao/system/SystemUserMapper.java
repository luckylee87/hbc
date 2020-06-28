package com.dsg.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsg.entity.system.QueryUser;
import com.dsg.entity.system.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemUserMapper
 * 类描述：SystemUser 表数据访问层接口
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
@Repository
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 根据查找条件查找所有管理员
     *
     * @param queryUser 查询用户实体类
     * @return List<SystemUser>
     */
    List<SystemUser> selectAllSystemUser(QueryUser queryUser);

    /**
     * 根据角色ID查找管理员
     *
     * @param roleId 角色ID
     * @return List<SystemUser>
     */
    List<SystemUser> selectSysUserByRoleId(@Param("roleId") Integer roleId);

    /**
     * 更新用户信息
     *
     * @param systemUser 用户信息
     */
    void updateUserInfo(SystemUser systemUser);

}