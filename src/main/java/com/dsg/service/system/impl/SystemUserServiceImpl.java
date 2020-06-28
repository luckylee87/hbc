package com.dsg.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dsg.common.util.MD5Utils;
import com.dsg.common.util.SingletonLoginUtils;
import com.dsg.common.util.UUIDGenerator;
import com.dsg.dao.system.SystemUserLoginLogMapper;
import com.dsg.dao.system.SystemUserMapper;
import com.dsg.dao.system.SystemUserRoleMapper;
import com.dsg.entity.system.QueryUser;
import com.dsg.entity.system.SystemUser;
import com.dsg.entity.system.SystemUserLoginLog;
import com.dsg.entity.system.SystemUserRole;
import com.dsg.service.base.impl.BaseServiceImpl;
import com.dsg.service.system.ISystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemUserServiceImpl
 * 类描述：SystemUser 表业务逻辑层接口实现类
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
@Service("systemUserService")
public class SystemUserServiceImpl extends BaseServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemUserLoginLogMapper systemUserLoginLogMapper;
    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    @Override
    public void insertSystemUser(SystemUser systemUser, String[] roleIds) {
        systemUser.setLoginPassword(MD5Utils.getMD5(systemUser.getLoginPassword()));
        this.save(systemUser);

        if (roleIds != null && roleIds.length > 0) {
            List<SystemUserRole> systemUserRoles = new ArrayList<SystemUserRole>();
            for (int i = 0; i < roleIds.length; i++) {
                SystemUserRole systemUserRole = new SystemUserRole();
                systemUserRole.setUserId(systemUser.getId());
                systemUserRole.setRoleId(roleIds[i]);
                systemUserRoles.add(systemUserRole);
            }
            systemUserRoleMapper.insertUserRoles(systemUserRoles);//插入角色列表
        }
    }

    @Override
//	@Cacheable(value="userCache",key="'systemUser'+#loginName")
    public SystemUser selectByLoginName(String loginName) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("LOGIN_NAME", loginName);
        return systemUserMapper.selectOne(wrapper);
    }

    @Override
    public Integer selectAllSystemUserNumber() {
        return systemUserMapper.selectCount(null);
    }

    @Override
    public List<SystemUser> selectAllSystemUser(QueryUser queryUser) {
        return systemUserMapper.selectAllSystemUser(queryUser);
    }

    @Override
    public List<SystemUser> selectSysUserByRoleId(Integer roleId) {
        return systemUserMapper.selectSysUserByRoleId(roleId);
    }

    @Override
    public boolean checkLoginName(String loginName) {
        SystemUser systemUser = new SystemUser();
        systemUser.setLoginName(loginName);
        //int count = systemUserMapper.selectCount(systemUser);
        int count = systemUserMapper.selectCount(new QueryWrapper<>(systemUser));
        return count > 0;
    }

    @Override
    //@Transactional
    public void updateLogByLoginName(String accountId, String ipAddr, String browser, String operatingSystem) {
        //更新systemUser表用户登录信息
        SystemUser systemUser = new SystemUser();
        systemUser.setId(accountId);
        systemUser.setLastLoginTime(new Date());
        systemUser.setLastLoginIp(ipAddr);
        //出现类型错误，未知原因
        //systemUserMapper.updateById(systemUser);
        //创建用户登录日志
        SystemUserLoginLog systemUserLoginLog = new SystemUserLoginLog();
        systemUserLoginLog.setOid(UUIDGenerator.getUUID());
        systemUserLoginLog.setUserId(accountId);
        systemUserLoginLog.setUserIp(ipAddr);
        systemUserLoginLog.setBrowser(browser);
        systemUserLoginLog.setOperatingSystem(operatingSystem);
        systemUserLoginLog.setLoginTime(new Date());
        systemUserLoginLogMapper.insert(systemUserLoginLog);
    }

    @Override
    public void updateUserStatus(String accountId, Integer status) {
        SystemUser systemUser = new SystemUser();
        systemUser.setId(accountId);
        systemUser.setStatus(status);
        systemUserMapper.updateById(systemUser);
    }

    @Override
    public void updateUserInfoBySystem(SystemUser systemUser, String[] roleIds) {
        systemUserMapper.updateUserInfo(systemUser);//更新用户信息
        //删除SystemUserRole 表记录
        SystemUserRole userRole = new SystemUserRole();
        userRole.setUserId(systemUser.getId());
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("USER_ID", userRole.getUserId());
        systemUserRoleMapper.deleteByMap(columnMap);
        if (roleIds != null && roleIds.length > 0) {
            List<SystemUserRole> systemUserRoles = new ArrayList<SystemUserRole>();
            for (int i = 0; i < roleIds.length; i++) {
                SystemUserRole systemUserRole = new SystemUserRole();
                systemUserRole.setUserId(systemUser.getId());
                systemUserRole.setRoleId(roleIds[i]);
                systemUserRoles.add(systemUserRole);
            }
            systemUserRoleMapper.insertUserRoles(systemUserRoles);//插入角色列表
        }
    }

    @Override
    public void updateUserInfo(SystemUser systemUser) {
        systemUser.setUpdateBy(SingletonLoginUtils.getSystemUserName());
        //systemUserMapper.updateSelectiveById(systemUser);//更新用户信息
        systemUserMapper.updateById(systemUser);
    }

    @Override
    public void updateUserPws(String accountId, String loginPassword) {
        SystemUser systemUser = new SystemUser();
        systemUser.setId(accountId);
        systemUser.setLoginPassword(MD5Utils.getMD5(loginPassword));
        //systemUserMapper.updateSelectiveById(systemUser);
        systemUserMapper.updateById(systemUser);
    }

    @Override
    public void deleteSysUser(String accountId) {
        //删除SystemUser 表信息
        systemUserMapper.deleteById(accountId);
        //删除SystemUserLoginLog 表用户记录
//		SystemUserLoginLog loginLog = new SystemUserLoginLog();
//		loginLog.setUserId(accountId);
//		systemUserLoginLogMapper.deleteSelective(loginLog);
//		systemUserLoginLogMapper.removeById(loginLog);
        QueryWrapper entityWrapper = new QueryWrapper<SystemUserLoginLog>();
        entityWrapper.eq("USER_ID", accountId);
        systemUserLoginLogMapper.delete(entityWrapper);
        //删除 SystemUserRoleMapper 表用户角色记录
//		SystemUserRole userRole = new SystemUserRole();
//		userRole.setId(accountId);
        QueryWrapper entity = new QueryWrapper<SystemUserRole>();
        entity.eq("USER_ID", accountId);
        systemUserRoleMapper.delete(entity);
        //systemUserRoleMapper.deleteSelective(userRole);
    }

}