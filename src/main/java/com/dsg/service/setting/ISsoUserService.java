package com.dsg.service.setting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dsg.common.dto.AjaxResult;
import com.dsg.entity.setting.SsoUser;
import com.dsg.common.constants.APIMessage;

import java.util.List;

public interface ISsoUserService extends IService<SsoUser> {

    Page<SsoUser> selectByList(Page<SsoUser> page, SsoUser project);

    SsoUser login(String userName, String password);

    boolean addUser(SsoUser ssoUser);

    boolean deleteUser(String oid);

    /**
     * 新增用户 外接接口调用
     * @param ssoUser
     * @return
     */
    boolean addOrgUser(SsoUser ssoUser) throws Exception;

    /**
     * 检查用户信息是否合法
     * @param ssoUser
     * @return
     * @throws Exception
     */
    APIMessage checkUser(SsoUser ssoUser) throws Exception;

    /**
     * 修改用户时，检查email,手机号是否被占用
     * @param ssoUser
     * @return
     * @throws Exception
     */
    APIMessage  updateUserCheck(SsoUser ssoUser) throws Exception;

    /**
     * 通过机构代码生成对应账号的权限
     * @param code SSO菜单模板机构
     * @param userName SSO用户名
     * @param roleNames SSO菜单模板机构对应角色名
     * @return
     */
    AjaxResult copyMenuTemplate(String code, String userName, List<String> roleNames);
}
