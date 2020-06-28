package com.dsg.controller.setting;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powerbridge.core.util.DateUtil;
import com.powerbridge.sso.utils.MD5Utils;
import com.dsg.common.constants.CommonConstants;
import com.dsg.common.constants.MessageConstants;
import com.dsg.common.dto.AjaxResult;
import com.dsg.common.security.SystemAuthorizingUser;
import com.dsg.common.util.SingletonLoginUtils;
import com.dsg.common.util.toolbox.StringUtil;
import com.dsg.controller.base.BaseController;
import com.dsg.entity.setting.SsoUser;
import com.dsg.service.setting.ISsoPwConfigService;
import com.dsg.service.setting.ISsoUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SsoUserController
 * 类描述：用户控制层
 * 创建人：wiezheng
 * 创建时间：2017年11月6日 上午10:12:17
 */
@Api(description = "SsoUserController",tags = "用户管理控制层")
@RestController
@RequestMapping(value = "/ssoUser")
@Slf4j
public class SsoUserController extends BaseController {

    @Autowired
    private ISsoUserService ssoUserService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ISsoPwConfigService ssoPwConfigService;

    /**
     * 批量删除
     * @return
     */
    @RequestMapping(value = "/list/deleteByList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody

    public AjaxResult delete() {
        try{
            String oid = getParameter("idList");
            Boolean flag = ssoUserService.deleteUser(oid);
            if(flag){
                return result(MessageConstants.SSO_STATUS_SUCCESS);
            }else {
                return result(MessageConstants.SSO_STATUS_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }
    }

    /**
     * 更新
     * @param ssoUser
     * @return
     */
    @RequestMapping(value = "/list/update", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody

    public AjaxResult updateProject(SsoUser ssoUser) {
        AjaxResult ajaxResult = null;
        try {
            QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
            entityWrapper.eq("USER_NAME",ssoUser.getUserName());
            List<SsoUser> ssoUsers = ssoUserService.list(entityWrapper);
            if(ssoUsers.size()>1){
                return setErrorJson("用户名已存在！");
            }
            if(ssoUsers.size() == 1 && !ssoUsers.get(0).getOid().equals(ssoUser.getOid())){
                return setErrorJson("用户名已存在！");
            }

            Boolean flag = ssoUserService.updateById(ssoUser);
            if (flag) {
                ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS,ssoUser);
            } else {
                ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            }
        } catch (Exception e) {
            ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            log.error("updateSsoUser()--err", e);
        }
        return ajaxResult;
    }

    /**
     * 查询单条数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/list/{id}/view", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody

    public AjaxResult editCopEntInfo(@PathVariable String id) {
        AjaxResult ajaxResult = null;
        try {
            SsoUser ssoUser = ssoUserService.getById(id);
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoUser);
        } catch (Exception e) {
            ajaxResult = result(MessageConstants.SSO_STATUS_SUCCESS);
            log.error("editUser()--err", e);
        }
        return ajaxResult;
    }

    /**
     * 创建
     * @param ssoUser
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list/add", method = {RequestMethod.POST, RequestMethod.GET})

    public AjaxResult create(SsoUser ssoUser) {
        try{
            QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
            entityWrapper.eq("USER_NAME",ssoUser.getUserName());
            List<SsoUser> ssoUsers = ssoUserService.list(entityWrapper);
            if(ssoUsers.size() > 0){
                return setErrorJson("用户名已存在！");
            }
            ssoUser.setUpdpwdTime(new Date());
            boolean falg = ssoUserService.addUser(ssoUser);
            if(falg)
                return result(MessageConstants.SSO_STATUS_SUCCESS);
            else
                return result(MessageConstants.SSO_STATUS_FAIL);
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }
    }

    /**
     * 查询
     * @param ssoUser
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody

    public AjaxResult list(SsoUser ssoUser) {
        AjaxResult ajaxResult = null;
        SystemAuthorizingUser sysUser = SingletonLoginUtils.getSystemUser();
        //如果登录用户不为空，则为其他服务访问，获取当前登录用户信息过滤筛选数据
        com.powerbridge.core.security.SystemAuthorizingUser systemUser = com.powerbridge.core.util.SingletonLoginUtils.getSystemUser(redisTemplate);
        try {
            if(sysUser != null && CommonConstants.USER_TYPE_COM.equals(sysUser.getUserType())) {
                return null;
            }else if(sysUser != null && CommonConstants.USER_TYPE_ORG.equals(sysUser.getUserType())){
                ssoUser.setOrgCode(sysUser.getOrgCode());
            } else if(systemUser != null && systemUser.getUserName() != null){
                ssoUser.setOrgCode(systemUser.getOrgCode());
            }
            Page page = getPage();  // 分页
            String sort = getParameter("sort");
            boolean sortOrder = getOrderSort(getParameter("sortOrder"));

//            if (StringUtil.isNotEmpty(sort)) {
//                page.setAsc(sortOrder);
//                page.setOrderByField(sort);  // 排序
//            }else {
//                page.setAsc(false);
//                page.setOrderByField("opTime");
//            }

//            ssoUser.setOpUser(sysUser.getLoginName());
            Page<SsoUser> profunPage = ssoUserService.selectByList(page,ssoUser);

            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, profunPage.getRecords(), (int) page.getTotal()); // 格式要返回的数据
            return ajaxResult;
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody

    public AjaxResult login(String userName, String password){
        AjaxResult ajaxResult = null;
        try {
            SsoUser ssoUser = ssoUserService.login(userName, password);

            if (ssoUser != null) {

                return ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoUser);
            } else {
                return result(MessageConstants.SSO_STATUS_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }
    }

    /**
     * 验证用户名是否存在
     * @param userName
     * @return
     */
    @ApiOperation(value = "验证用户名是否存在")
    @ApiResponses({
            @ApiResponse(response = AjaxResult.class,code = 0,message = "该用户名存在!",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 1,message = "该用户名不存在!",responseContainer = "AjaxResult")
    })
    @RequestMapping(value = "/validateUser", method = RequestMethod.GET)
    @ResponseBody

    public AjaxResult validateUser(@ApiParam(value = "用户名",name = "userName",required = true) @RequestParam(value = "userName") String userName){
        try {
            QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
            entityWrapper.eq("USER_NAME",userName);
            SsoUser ssoUser = ssoUserService.getOne(entityWrapper);
            if (ssoUser != null) {
                return setJson(0,"该用户名存在！");
            } else {
                return setJson(1,"该用户名不存在！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }
    }

    /**
     * 通过微信用户openID绑定sso账户
     * @param openID
     * @param userName
     * @param userPwd
     * @return
     */
    @ApiOperation(value = "通过微信用户openID绑定sso账户")
    @ApiResponses({
            @ApiResponse(response = AjaxResult.class,code = 0,message = "失败",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 1,message = "成功!",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 5,message = "密码错误!",responseContainer = "AjaxResult")
    })
    @RequestMapping(value = "/addOpenID", method = RequestMethod.POST)
    @ResponseBody

    public AjaxResult addOpenID(@ApiParam(value = "微信用户ID",name = "openID",required = true) @RequestParam(value = "openID") String openID,
                                @ApiParam(value = "SSO用户名",name = "userName",required = true) @RequestParam(value = "userName") String userName,
                                @ApiParam(value = "SSO密码",name = "userPwd",required = true) @RequestParam(value = "userPwd") String userPwd){
        try {
            QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
            entityWrapper.eq("USER_NAME",userName);
            SsoUser ssoUser = ssoUserService.getOne(entityWrapper);
            if(ssoUser != null) {
                if (ssoUser.getUserPwd().equals(userPwd)) {
                    ssoUser.setOpenID(openID);
                    ssoUserService.updateById(ssoUser);
                    return result(MessageConstants.SSO_STATUS_SUCCESS);
                } else {
                    return result(MessageConstants.SSO_STATUS_USER_PASSWORD);
                }
            }else {
                return result(MessageConstants.SSO_STATUS_USER_NOTEXIST);
            }
        }catch (Exception e){
            log.error("addOpenID()--error ",e);
            return setErrorJson(e.toString());
        }
    }

    /**
     * 通过SSO用户名获取用户信息
     * @param userName
     * @return
     */
    @ApiOperation(value = "通过SSO用户名获取用户信息")
    @ApiResponses({
            @ApiResponse(response = AjaxResult.class,code = 0,message = "失败",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 1,message = "成功!",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 3,message = "该用户不存在!",responseContainer = "AjaxResult")
    })
    @RequestMapping(value = "/getUserByName", method = RequestMethod.GET)
    @ResponseBody

    public AjaxResult getUserByName(@ApiParam(value = "SSO用户名",name = "userName",required = true) @RequestParam(value = "userName") String userName){
        try {
            QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
            entityWrapper.eq("USER_NAME",userName);
            SsoUser ssoUser = ssoUserService.getOne(entityWrapper);
            if (ssoUser!= null) {
                return json(MessageConstants.SSO_STATUS_SUCCESS,ssoUser);
            } else {
                return result(MessageConstants.SSO_STATUS_USER_NOTEXIST);
            }
        }catch (Exception e){
            log.error("getUserByName()--error ",e);
            return setErrorJson(e.toString());
        }
    }

    @ApiOperation(value = "通过SSO用户名获取用户有效起止时间")
    @ApiResponses({
            @ApiResponse(response = AjaxResult.class,code = 0,message = "失败",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 1,message = "成功!",responseContainer = "AjaxResult"),
    })
    @RequestMapping(value = "/getValidDateByName", method = RequestMethod.GET)
    @ResponseBody

    public AjaxResult getValidDateByName(@ApiParam(value = "SSO用户名",name = "userName",required = true) @RequestParam(value = "userName") String userName){
        try {
            QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
            entityWrapper.eq("USER_NAME",userName);
            SsoUser ssoUser = ssoUserService.getOne(entityWrapper);
            if (ssoUser!= null) {
            	Map<String, String> dateMap = new HashMap<String, String>();
            	dateMap.put("startValidDate", DateUtil.formatDateTime(ssoUser.getStartValidDate()));
            	dateMap.put("endValidDate", DateUtil.formatDateTime(ssoUser.getEndValidDate()));
                return json(MessageConstants.SSO_STATUS_SUCCESS,dateMap);
            } else {
                return result(MessageConstants.SSO_STATUS_USER_NOTEXIST);
            }
        }catch (Exception e){
            log.error("getValidDateByName()--error ",e);
            return setErrorJson(e.toString());
        }
    }

    @ApiOperation(value = "通过SSO用户名及原密码修改密码")
    @ApiResponses({
            @ApiResponse(response = AjaxResult.class,code = 0,message = "失败",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 1,message = "成功",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 15,message = "原密码不正确!",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 1007,message = "用户名和密码不能为空!",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 1012,message = "用户不存在!",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 1015,message = "新旧密码不能相似!",responseContainer = "AjaxResult")
    })
    @RequestMapping(value = "/list/editpwd", method = RequestMethod.POST)
    @ResponseBody

    public AjaxResult editPwd(@ApiParam(value = "SSO用户名及密码",name = "map",required = true) @RequestBody Map<String,String> map) {
        try {
            String userName = map.get("userName");
            String oldPwd = map.get("oldPwd");
            String newPwd = map.get("newPwd");
            //saas传过来的密码是加密后的，其他传过来是明文需要加密处理
            String type = map.get("type");
            //进行参数userName,oldPwd,newPwd非空判断，防止后面空指针或数据查询错误
            if(StringUtil.isNotEmpty(userName) && StringUtil.isNotEmpty(oldPwd) && StringUtil.isNotEmpty(newPwd)) {
                QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
                entityWrapper.eq("USER_NAME",userName);
                SsoUser ssoUser = ssoUserService.getOne(entityWrapper);
                //先判断该用户名是否存在
                if(ssoUser != null){
                    ssoUser.setUpdpwdTime(new Date());
                    //原密码oldPwd用MD5加密后跟用户密码进行比较
                    if(StringUtil.isNotEmpty(type) && "1".equals(type)){
                        if (oldPwd.equals(ssoUser.getUserPwd())) {
                            ssoUser.setUserPwd(newPwd);
                            ssoUser.setUpdpwdTime(new Date());
                            ssoUserService.updateById(ssoUser);
                            return result(MessageConstants.SSO_STATUS_SUCCESS);
                        } else {
                            return result(MessageConstants.SSO_STATUS_PASSWORD_EXIST_ERROR);
                        }
                    }else {
                        if (MD5Utils.getMD5(oldPwd).equals(ssoUser.getUserPwd())) {
                            //如果密码验证通过了，则校验密码是否与近期相同，新密码包含旧密码 或 旧密码包含新密码
                            if (oldPwd.contains(newPwd) || newPwd.contains(oldPwd)) {
                                return result(MessageConstants.SSO_USER_PASSWORD_SAME);
                            }
                            //新密码newPwd用MD5加密保存
                            ssoUser.setUserPwd(MD5Utils.getMD5(newPwd));
                            ssoUser.setUpdpwdTime(new Date());
                            ssoUserService.updateById(ssoUser);
                            return result(MessageConstants.SSO_STATUS_SUCCESS);
                        } else {
                            return result(MessageConstants.SSO_STATUS_PASSWORD_EXIST_ERROR);
                        }
                    }
                }else {
                    return result(MessageConstants.SSO_STATUS_USER_NOT_EXISTS);
                }
            }else {
                return result(MessageConstants.SSO_USERNAME_PWD_NOTEMPTY);
            }
        } catch (Exception e) {
            log.error("editPwd()--err", e);
            return setErrorJson(e.toString());
        }
    }
}
