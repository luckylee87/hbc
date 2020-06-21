package com.wstro.controller.setting;

import com.powerbridge.core.security.SystemAuthorizingUser;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.controller.base.BaseController;
import com.wstro.entity.setting.AuthorizeTreeMode;
import com.wstro.entity.setting.RoleFuncsMode;
import com.wstro.entity.setting.SsoRoleFunc;
import com.wstro.service.setting.ISsoRoleFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色-功能对应表 前端控制器
 * </p>
 *
 * @author wenyiwei
 * @since 2017-11-01
 */
@RestController
@RequestMapping("/setting/ssoRoleFunc")
public class SsoRoleFuncController extends BaseController {

    @Autowired
    private ISsoRoleFuncService ssoRoleFuncService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通过角色oid查询角色已被授权的功能
     * @param roleOid
     * @return
     */
    @RequestMapping(value = "/getRoleAuth", method = RequestMethod.GET)

    public AjaxResult getRoleAuth(String roleOid) {
        AjaxResult rs = null;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("ROLE_OID", roleOid);
            //根据角色oid查询角色已授权的功能
            List<SsoRoleFunc> list = ssoRoleFuncService.listByMap(params);
            rs = json(MessageConstants.SSO_STATUS_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            rs = setErrorJson(e.getMessage());
        }
        return rs;
    }

    /**
     * 获取授权功能的树形结构数据
     * 如果用户名和机构代码参数不为空，则为saas调用结构树
     * @return
     */
    @RequestMapping(value = "/getAuthorizeTree", method = RequestMethod.GET)

    public AjaxResult getProFuncList() {
        AjaxResult rs = null;
        try {
            List<AuthorizeTreeMode> list = null;
            //如果登录用户不为空，则为其他服务访问，获取当前登录用户信息过滤筛选数据
            SystemAuthorizingUser systemUser = com.powerbridge.core.util.SingletonLoginUtils.getSystemUser(redisTemplate);
            if(systemUser != null && systemUser.getUserName() != null){
                list = ssoRoleFuncService.selectAuthTreeAll(systemUser.getUserName(),systemUser.getOrgCode());
            } else {
                list = ssoRoleFuncService.selectAuthTreeAll(null,null);
            }
            rs = json(MessageConstants.SSO_STATUS_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            rs = setErrorJson(e.getMessage());
        }
        return rs;
    }

    /**
     * 保存角色被授权功能的信息
     * @param roleFuncsMode
     * @return
     */
    @RequestMapping(value = "/saveTree", method = RequestMethod.POST)

    public AjaxResult saveTree(@RequestBody RoleFuncsMode roleFuncsMode) {
        AjaxResult rs = null;
        try {
            ssoRoleFuncService.saveRoleFunc(roleFuncsMode.getRoleOid(), roleFuncsMode.getFunctionList());
            rs = result(MessageConstants.SSO_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            rs = setErrorJson(e.getMessage());
        }
        return rs;
    }

}
