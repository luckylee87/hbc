package com.dsg.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsg.entity.system.QueryUser;
import com.dsg.entity.system.SystemUser;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：ISystemUserService
 * 类描述：SystemUser 表业务逻辑层接口
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
public interface ISystemUserService extends IService<SystemUser> {

    /**
     * 创建新的管理员
     *
     * @param systemUser 管理员信息
     * @param roleIds    角色ID列表
     */
    void insertSystemUser(SystemUser systemUser, String[] roleIds);

    /**
     * 根据用户名查找管理员
     *
     * @param loginName 登录名
     * @return SystemUser
     */
    SystemUser selectByLoginName(String loginName);

    /**
     * 查找所有管理员数量
     *
     * @return Integer null返回0
     */
    Integer selectAllSystemUserNumber();

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
    List<SystemUser> selectSysUserByRoleId(Integer roleId);

    /**
     * 查找用户名是否存在
     *
     * @param loginName 登录名
     * @return true存在/false不存在
     */
    boolean checkLoginName(String loginName);

    /**
     * 更新用户登录日志
     *
     * @param accountId       用户ID
     * @param ipAddr          当前IP地址
     * @param browser         用户游览器型号
     * @param operatingSystem 用户系统型号
     */
    void updateLogByLoginName(String accountId, String ipAddr, String browser, String operatingSystem);

    /**
     * 更新用户状态
     *
     * @param accountId 用户ID
     * @param status    用户状态
     */
    void updateUserStatus(String accountId, Integer status);

    /**
     * 更新用户信息通过管理员
     *
     * @param systemUser 管理员信息
     * @param roleIds    角色ID列表
     */
    void updateUserInfoBySystem(SystemUser systemUser, String[] roleIds);

    /**
     * 更新个人信息
     *
     * @param systemUser 管理员信息
     */
    void updateUserInfo(SystemUser systemUser);

    /**
     * 更新用户密码
     *
     * @param accountId     用户ID
     * @param loginPassword 用户密码
     */
    void updateUserPws(String accountId, String loginPassword);

    /**
     * 删除用户，同时删除角色记录、登录日志
     *
     * @param accountId
     */
    void deleteSysUser(String accountId);
}