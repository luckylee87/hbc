package com.dsg.common.security;

import com.dsg.entity.system.SystemRoleMenu;
import com.dsg.entity.system.SystemUserRole;
import com.dsg.service.system.ISystemRoleMenuService;
import com.dsg.service.system.ISystemUserRoleService;
import com.dsg.service.system.ISystemUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemAuthorizingRealm
 * 类描述：Shiro验证用户登录的类
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private ISystemUserService systemUserService;
    @Autowired
    private ISystemUserRoleService systemUserRoleService;
    @Autowired
    private ISystemRoleMenuService systemRoleMenuService;


    /**
     * 认证回调函数, 登录时调用
     * Shiro登录认证(原理：用户提交 用户名和密码  --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制
     * 该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 获取基于用户名和密码的令牌：实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        SystemUser systemUser = systemUserService.selectByLoginName(token.getUsername());
//        if (systemUser != null) {
//            // 校验用户状态
//            if (GlobalConstants.NO.equals(systemUser.getStatus().toString())) {
//                throw new DisabledAccountException();
//            }
//            SystemAuthorizingUser authorizingUser = new SystemAuthorizingUser(
//                    systemUser.getId(), systemUser.getLoginName(),
//                    systemUser.getUserName(), systemUser.getRealName(), systemUser.getUserType(),
//                    systemUser.getTradeCode(), systemUser.getEntName());
//
//
//            // 认证缓存信息
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
//                    authorizingUser, systemUser.getLoginPassword(), getName());
//            return simpleAuthenticationInfo;
//        } else {
//            return null;
//        }
        return null;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     * 经测试:本例中该方法的调用时机为需授权资源被访问时
     * 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
     * 比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SystemAuthorizingUser authorizingUser = (SystemAuthorizingUser) principalCollection.getPrimaryPrincipal();

        if (authorizingUser != null) {
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

            //获得用户角色列表
            List<SystemUserRole> systemUserRoles = systemUserRoleService.selectRoleListByAccountId(authorizingUser.getUserId());
            List<String> roleIdList = new ArrayList<>();
            for (SystemUserRole systemRole : systemUserRoles) {  // 添加用户角色信息
                simpleAuthorizationInfo.addRole(systemRole.getRoleName());
                roleIdList.add(systemRole.getRoleId());
            }

            //获得权限列表
            List<SystemRoleMenu> systemRoleMenus = systemRoleMenuService.selectMenuListByRoleId(roleIdList);
            for (SystemRoleMenu systemRoleMenu : systemRoleMenus) {
                if (StringUtils.isNotBlank(systemRoleMenu.getPermission())) {
                    // 添加基于Permission的权限信息
                    simpleAuthorizationInfo.addStringPermission(systemRoleMenu.getPermission());
                }
            }
            return simpleAuthorizationInfo;
        }
        return null;
    }


}
