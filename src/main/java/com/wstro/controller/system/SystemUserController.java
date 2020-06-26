package com.wstro.controller.system;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.powerbridge.sso.common.MessageEnum;
import com.powerbridge.sso.dto.JsonResultDto;
import com.powerbridge.sso.dto.SsoUserDto;
import com.powerbridge.sso.utils.SsoSystemUtils;
import com.wstro.common.constants.CommonConstants;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.common.security.SystemAuthorizingUser;
import com.wstro.common.util.SingletonLoginUtils;
import com.wstro.common.util.UploadFileUtils;
import com.wstro.common.util.toolbox.StringUtil;
import com.wstro.common.util.toolbox.WebUtil;
import com.wstro.controller.base.BaseController;
import com.wstro.entity.system.*;
import com.wstro.service.system.ISystemRoleService;
import com.wstro.service.system.ISystemUserLoginLogService;
import com.wstro.service.system.ISystemUserRoleService;
import com.wstro.service.system.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemUserController
 * 类描述：系统管理员控制器
 * 创建人：
 * 创建时间：2017年4月27日 下午10:12:17
 */
@RestController
@RequestMapping("/system/sysuser")
@CrossOrigin
@Slf4j
public class SystemUserController extends BaseController {

    /**
     * 系统管理员头像界面
     */
    private static final String SYSTEM_USER_AVATAR = getViewPath("admin/system/system_user_avatar");

    private static final Gson gson = new Gson();

    @Autowired
    private ISystemUserService systemUserService;
    @Autowired
    private ISystemUserLoginLogService systemUserLoginLogService;
    @Autowired
    private ISystemRoleService systemRoleService;
    @Autowired
    private ISystemUserRoleService systemUserRoleService;


    @InitBinder("systemUser")
    public void initBinderSystemUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("systemUser.");
    }

    @InitBinder("queryUser")
    public void initQueryUser(WebDataBinder dinder) {
        dinder.setFieldDefaultPrefix("queryUser.");
    }

    /**
     * GET 管理员列表
     *
     * @param queryUser
     * @return
     */
    @RequestMapping(value = "/list")

    public String list(Model model, @ModelAttribute("queryUser") QueryUser queryUser) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();

        //如果登录用户用企业编号,只能查看同企业用户
        String loginUserTradCode = SingletonLoginUtils.getSystemUser().getInputCopNo();
        if (!StringUtil.isEmpty(loginUserTradCode)) {
            queryUser.setTradeCode(loginUserTradCode);
        }


        int sysUserNumber = systemUserService.selectAllSystemUserNumber();
        //model.addAttribute("sysUserNumber", sysUserNumber);
        List<SystemUser> systemUsers = systemUserService.selectAllSystemUser(queryUser);
        //model.addAttribute("systemUsers", systemUsers);
        List<SystemRole> systemRoles = systemRoleService.selectRoleAndNumber();
        //model.addAttribute("systemRoles", systemRoles);
        data.put("sysUserNumber", sysUserNumber);// 用户总数量
        data.put("systemUsers", systemUsers);// 用户列表
        data.put("systemRoles", systemRoles);// 权限列表
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * GET 个人资料
     *
     * @param userId 用户编号
     * @return
     */
    @RequestMapping(value = "/list/{userId}/detail", method = RequestMethod.GET)

    public String detail(Model model, @PathVariable String userId) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        SystemUser user = systemUserService.getById(userId);
        model.addAttribute("user", user);//用户信息
        List<SystemUserRole> systemUserRoles = systemUserRoleService.selectRoleListByAccountId(userId);
        StringBuffer userRole = new StringBuffer();
        for (SystemUserRole systemUserRole : systemUserRoles) {
            userRole.append(systemUserRole.getRoleName());
            userRole.append("&nbsp");
        }
        data.put("userRole", userRole);//用户权限
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * Get 用户登录日志
     *
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/list/{accountId}/log", method = RequestMethod.GET)

    public String userLog(Model model, @PathVariable String accountId) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<SystemUserLoginLog> systemUserLoginLogList = systemUserLoginLogService.selectUserLoginLog(accountId);
        data.put("systemUserLoginLogList", systemUserLoginLogList);
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * POST 启用/禁止用户
     *
     * @return
     */
    @RequestMapping(value = "/list/audit", method = RequestMethod.POST)

    public AjaxResult audit(String accountId) {
        Integer status = Integer.valueOf(getParameter("status"));
        systemUserService.updateUserStatus(accountId, status);
        return result(MessageConstants.SSO_STATUS_SUCCESS);
    }

    /**
     * DELETE 删除用户
     *
     * @return
     */
    @RequestMapping(value = "/list/{accountId}/delete", method = RequestMethod.POST)

    public Map<String, Object> delete(@PathVariable String accountId) {
        systemUserService.deleteSysUser(accountId);
        return this.setJson(MessageConstants.SSO_STATUS_SUCCESS, null);
    }

    /**
     * GET 角色分类下管理员列表
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/list/{roleId}/role", method = RequestMethod.GET)

    public String listrole(Model model, @PathVariable Integer roleId) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<SystemUser> systemUsers = systemUserService.selectSysUserByRoleId(roleId);
        data.put("systemUsers", systemUsers);
        json = this.transJson("1", "成功获取数据", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * GET 修改用户页面
     *
     * @return
     */
    @RequestMapping(value = "/list/{accountId}/edit", method = RequestMethod.GET)

    public String edit(Model model, @PathVariable String accountId) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        SystemUser systemUser = systemUserService.getById(accountId);
        List<SystemRole> systemRoles = systemRoleService.selectRoleList();
        List<SystemUserRole> systemRoleList = systemUserRoleService.selectRoleListByAccountId(accountId);
        String userType = SingletonLoginUtils.getSystemUser().getUserType();
        data.put("loginUserType", userType);//登录用户的用户类型,用于前端判断
        data.put("systemUser", systemUser);//用户信息
        data.put("systemRoles", systemRoles);//所有角色
        data.put("systemRoleList", systemRoleList);//分配角色
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * GET 创建用户页面
     *
     * @return
     */
    @RequestMapping(value = "/list/add", method = RequestMethod.GET)

    public String add(Model model) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();

        //待入默认的企业信息
        String userType = SingletonLoginUtils.getSystemUser().getUserType();
        SystemUser systemUser = new SystemUser();

        systemUser.setUserType(SingletonLoginUtils.getSystemUser().getUserType());
        //获取当前登录用户的菜单权限
        if ("1".equals(userType)) {//企业用户
            systemUser.setLoginName("");
            systemUser.setUserName("");
            systemUser.setTradeCode(SingletonLoginUtils.getSystemUser().getInputCopNo());
            systemUser.setEntName(SingletonLoginUtils.getSystemUser().getInputCopName());
            List<SystemUserRole> systemRoleList = systemUserRoleService.selectRoleListByAccountId(SingletonLoginUtils.getSystemUserId());
            data.put("systemRoleList", systemRoleList);//分配角色
        }
        data.put("systemUser", systemUser);//用户信息
        data.put("loginUserType", userType);//登录用户的用户类型,用于前端判断

        List<SystemRole> systemRoles = systemRoleService.selectRoleList();
        data.put("systemRoles", systemRoles);
        json = this.transJson("1", "待入用戶基本信息", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * POST 创建或修改用户
     *
     * @return
     */
    @RequestMapping(value = "/list/save", method = RequestMethod.POST)

    public Map<String, Object> update(SystemUser systemUser, String roleId) {
        String[] roleIds = getParameterValues("roleId");
        if (!WebUtil.isEmail(systemUser.getEmail())) {
            return this.setJson(MessageConstants.SSO_STATUS_EMAIL_INPUT_ERROR, null);
        }
        if (!WebUtil.isTelephone(systemUser.getTelephone())) {
            return this.setJson(MessageConstants.SSO_STATUS_PHONE_INPUT_ERROR, null);
        }

        //当前登录用户是企业用户，则新增的用户企业与当前用户相同
        if ("1".equals(SingletonLoginUtils.getSystemUser().getUserType())) {
            systemUser.setTradeCode(SingletonLoginUtils.getSystemUser().getInputCopNo());
            systemUser.setEntName(SingletonLoginUtils.getSystemUser().getInputCopName());
        }


        if (StringUtils.isNotEmpty(systemUser.getId())) {
            if (systemUserService.checkLoginName(systemUser.getLoginName())) {
                return this.setJson(MessageConstants.SSO_STATUS_USER_REDUPLICATED, null);
            }
            systemUserService.insertSystemUser(systemUser, roleIds);//创建用户及插入角色记录
            return this.setJson(MessageConstants.SSO_STATUS_USER_CREATE_SUCCESS, null);
        } else {
            systemUserService.updateUserInfoBySystem(systemUser, roleIds);//更新用户及角色记录
            return this.setJson(MessageConstants.SSO_STATUS_USER_UPDATE_SUCCESS, null);
        }
    }

    /**
     * GET 管理员个人信息界面
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)

    public String view(Model model) {
        String jsonStr = "";
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        SystemAuthorizingUser sysUser = SingletonLoginUtils.getSystemUser();
//        if (sysUser != null) {
//            SystemUser systemUser = systemUserService.selectByLoginName(sysUser.getLoginName());
            //model.addAttribute("systemUser", systemUser);// 用户信息
//            if(systemUser != null) {
//                List<SystemUserLoginLog> systemUserLoginLogList = systemUserLoginLogService.selectUserLoginLog(systemUser.getId());
//                //model.addAttribute("systemUserLoginLogList", systemUserLoginLogList);
//
//                List<SystemUserRole> systemUserRoles = systemUserRoleService.selectRoleListByAccountId(systemUser.getId());
//                StringBuffer userRole = new StringBuffer();
//                for (SystemUserRole systemUserRole : systemUserRoles) {
//                    userRole.append(systemUserRole.getRoleName());
//                    userRole.append(" ");
//                }
//                data.put("systemUserLoginLogList", systemUserLoginLogList);// 用户日志
//                data.put("userRole", userRole);// 用户权限
//            }
            //model.addAttribute("userRole", userRole);
//        }
        List<SystemUserLoginLog> systemUserLoginLogList = systemUserLoginLogService.selectUserLoginLog(sysUser.getUserId());
        data.put("systemUserLoginLogList", systemUserLoginLogList);// 用户日志
        data.put("systemUser", sysUser);// 用户信息
        json = this.transJson("1", "成功获取数据", data);
        jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * GET 获取登录用户信息
     *
     * @return
     */
    @RequestMapping(value = "/loginuser", method = RequestMethod.GET)
    public AjaxResult loginUserInfo() {
        AjaxResult result = new AjaxResult(MessageConstants.SSO_STATUS_SUCCESS);
        Date now = new Date();
        SystemAuthorizingUser sysUser = SingletonLoginUtils.getSystemUser();
        if (sysUser != null){
            sysUser.setCreateTime(now);
            sysUser.setDecTime(now);
            sysUser.setCreateBy(String.valueOf(sysUser.getUserId()));
            sysUser.setCreateName(sysUser.getUserName());
            sysUser.setInputerCode(String.valueOf(sysUser.getUserId()));
            sysUser.setInputerName(sysUser.getUserName());
            result.setData(sysUser);
        }else {
            result = new AjaxResult(MessageConstants.SSO_STATUS_FAIL);
        }
        return result;
    }


    /**
     * POST 更新管理员信息
     *
     * @return
     */
    @RequestMapping(value = "/info/edit", method = RequestMethod.POST)

    public AjaxResult edit(SystemUser editUser) {
        SystemAuthorizingUser sysUser = SingletonLoginUtils.getSystemUser();
        String token = (String) getRequest().getSession().getAttribute(CommonConstants.SESSION_TOKEN);
        Map<String, String> params = new HashMap<>();
        params.put("trueName", editUser.getRealName());
        params.put("icCode", editUser.getIcCode());
        params.put("telNum", editUser.getTelephone());
        params.put("email", editUser.getEmail());
        params.put("address", editUser.getAddress());
        try {
            JsonResultDto<SsoUserDto> resultDto = SsoSystemUtils.updateUserInfo(token , sysUser.getUserId(), sysUser.getPassword(), JSON.toJSONString(params));
            if(resultDto.getCode() != MessageEnum.API_STATUS_SUCCESS.getCode())
                return this.setJson(resultDto.getCode(), resultDto.getMsg());
            SsoUserDto userDto = (SsoUserDto) resultDto.getData();
            //修改后的用户信息设置给缓存对象
            sysUser.setTelephone(userDto.getTelNum());
            sysUser.setIcCode(userDto.getIcCode());
            sysUser.setEmail(userDto.getEmail());
            sysUser.setAddress(userDto.getAddress());
            sysUser.setRealName(userDto.getTrueName());
            return this.json(MessageConstants.SSO_STATUS_SUCCESS, null);
        } catch (Exception e) {
            log.error("edit error.", e);
            return this.json(MessageConstants.SSO_STATUS_FAIL, null);
        }
//        if (sysUser != null) {
//            systemUser.setId(sysUser.getUserId());
//            systemUserService.updateUserInfo(systemUser);
//            return this.setJson(MessageConstants.SSO_STATUS_SUCCESS, null);
//        } else {
//            return this.setJson(MessageConstants.SSO_STATUS_USER_OVERTIME, null);
//        }
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/info/edit/psw", method = RequestMethod.POST)

    public AjaxResult editPwd(HttpServletRequest request) {
        SystemAuthorizingUser sysUser = SingletonLoginUtils.getSystemUser();
        String token = (String) getRequest().getSession().getAttribute(CommonConstants.SESSION_TOKEN);
        if (sysUser != null) {
            // 原密码
            String nowPassword = request.getParameter("nowPassword") == null ? ""
                    : request.getParameter("nowPassword");
            // 新密码
            String newPassword = request.getParameter("newPassword") == null ? ""
                    : request.getParameter("newPassword");
            Map<String, String> params = new HashMap<>();
            params.put("userPwd", newPassword);
            try {
                JsonResultDto<SsoUserDto> resultDto = SsoSystemUtils.updateUserPwd(token , sysUser.getUserId(), nowPassword, JSON.toJSONString(params));
                if(resultDto.getCode() != MessageConstants.SSO_STATUS_SUCCESS.getCode())
                    return this.setJson(resultDto.getCode(), resultDto.getMsg());
                SsoUserDto userDto = (SsoUserDto) resultDto.getData();
                //修改后的用户信息设置给缓存对象
                sysUser.setPassword(userDto.getUserPwd());
            } catch (Exception e) {
                log.error("editPwd error.", e);
                return this.json(MessageConstants.SSO_STATUS_FAIL, null);
            }
            return this.json(MessageConstants.SSO_STATUS_PASSWORD_UPDATE_SUCCESS, null);
        }else{
            return this.json(MessageConstants.SSO_STATUS_USER_OVERTIME, null);
        }
    }

    /**
     * 设置头像页面
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String setAvatar() {
        return SYSTEM_USER_AVATAR;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json;charset=utf-8")

    public Map<String, Object> uploadHeadPortrait(MultipartFile avatar_file, String avatar_src, String avatar_data, HttpServletRequest request) {
        Map<String, Object> json = new HashMap<String, Object>();
        if (!avatar_file.isEmpty()) {
            try {
                //判断文件的MIMEtype
                String type = avatar_file.getContentType();
                if (type == null || !type.toLowerCase().startsWith("image/")) {
                    json = this.setJson(MessageConstants.SSO_STATUS_IMAGE_FILE_ERROR, null);
                    return json;
                }
                //头像存放文件
                String dir = "icon";
                Map<String, Object> returnMap = UploadFileUtils.Upload(request, avatar_file, avatar_data, dir);
                //返回的布尔型参数的值为true，如果字符串参数不为null，是相等的，忽略大小写字符串“true”。
                if (Boolean.parseBoolean(returnMap.get("flag").toString()) == true) {
                    SystemAuthorizingUser sysUser = SingletonLoginUtils.getSystemUser();
                    if (sysUser != null) {
                        SystemUser systemUser = systemUserService.selectByLoginName(sysUser.getLoginName());
                        systemUser.setPicImg(returnMap.get("savaPath").toString());
                        systemUserService.saveOrUpdate(systemUser);
                    }
                    json = this.setJson(MessageConstants.SSO_STATUS_IMAGE_UPDATE_SUCCESS, returnMap.get("savaPath").toString());
                    return json;
                }
            } catch (Exception e) {
                log.error("ImageUploadController.uploadHeadPortrait", e);
                json = this.setJson(MessageConstants.SSO_STATUS_IMAGE_UPDATE_FAIL, null);
                return json;
            }
        }
        json = this.setJson(MessageConstants.SSO_STATUS_IMAGE_FILE_ERROR, null);
        return json;
    }

}