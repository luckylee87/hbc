package com.dsg.controller.setting;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dsg.common.constants.MessageConstants;
import com.dsg.common.dto.AjaxResult;
import com.dsg.common.util.UUIDGenerator;
import com.dsg.controller.base.BaseController;
import com.dsg.entity.setting.SsoRoleUser;
import com.dsg.service.setting.ISsoRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SsoRoleUserController
 * 类描述：用户角色控制层
 * 创建人：wiezheng
 * 创建时间：2017年11月6日 下午10:12:17
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/ssoRoleUser")
public class SsoRoleUserController extends BaseController {

    @Autowired
    private ISsoRoleUserService ssoRoleUserService;

    /**
     * 保存用户-角色
     * @param ssoRoleUsers
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list/add",method = RequestMethod.POST)

    public AjaxResult create(@RequestBody List<SsoRoleUser> ssoRoleUsers) {

        try {

            for (int i = 0; i < ssoRoleUsers.size(); i++) {
                ssoRoleUsers.get(i).setOid(UUIDGenerator.getUUID());
            }
            QueryWrapper entityWrapper = new QueryWrapper();
            entityWrapper.eq("USER_NAME", ssoRoleUsers.get(0).getUserName());
            List<SsoRoleUser> roleList = ssoRoleUserService.list(entityWrapper);

            List roleIds = new ArrayList();
            for (SsoRoleUser ssoRoleUser : roleList) {
                roleIds.add(ssoRoleUser.getOid());
            }
            if (roleIds.size() > 0) {
                //删除原有的角色
                ssoRoleUserService.removeByIds(roleIds);
            }

            boolean falg = ssoRoleUserService.saveBatch(ssoRoleUsers);
            if (falg)
                return result(MessageConstants.SSO_STATUS_SUCCESS);
            else
                return result(MessageConstants.SSO_STATUS_FAIL);

        } catch (Exception e) {
            e.printStackTrace();
            return setErrorJson(e.toString());
        }

    }

    /**
     * 查询用户已拥有的角色
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/searchUserRole/{userName}",method = RequestMethod.GET)

    public AjaxResult searchUserRole(@PathVariable String userName){

        QueryWrapper entityWrapper = new QueryWrapper();
        entityWrapper.eq("USER_NAME",userName);

        List<SsoRoleUser> list = ssoRoleUserService.list(entityWrapper);

        return json(MessageConstants.SSO_STATUS_SUCCESS,list);
    }
}
