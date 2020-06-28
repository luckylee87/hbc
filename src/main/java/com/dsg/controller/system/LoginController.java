package com.dsg.controller.system;

import com.powerbridge.sso.common.MessageEnum;
import com.powerbridge.sso.dto.JsonResultDto;
import com.powerbridge.sso.dto.SsoOrgCompanyDto;
import com.powerbridge.sso.dto.SsoUserDto;
import com.powerbridge.sso.utils.SsoSystemUtils;
import com.dsg.common.constants.CommonConstants;
import com.dsg.common.constants.MessageConstants;
import com.dsg.common.dto.AjaxResult;
import com.dsg.common.security.SystemAuthorizingUser;
import com.dsg.common.util.RSAUtils;
import com.dsg.common.util.ServletUtils;
import com.dsg.controller.base.BaseController;
import com.dsg.entity.system.SystemUser;
import com.dsg.service.system.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：LoginController
 * 类描述：后台管理员登录表示层
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 * 修改备注：mybatis-plus整合完毕
 */
@Controller
@RequestMapping("/system")
@CrossOrigin
@Slf4j
public class LoginController extends BaseController {

    /**
     * 后台管理登录页面
     */
    private static final String ADMIN_LOGIN = getViewPath("admin/login/admin_login");

    @Autowired
    private ISystemUserService systemUserService;

    @InitBinder({"systemUser"})
    public void initBinderSystemUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("systemUser.");
    }

    /**
     * GET 登录
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        // 将公钥的 modulus 和 exponent 传给页面
        Map<String, Object> publicKeyMap = RSAUtils.getPublicKeyMap();
        model.addAttribute("publicKeyMap", publicKeyMap);
        return ADMIN_LOGIN;
    }

    /**
     * POST 登录
     *
     * @param systemUser
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public AjaxResult login(HttpServletRequest request, @ModelAttribute("systemUser") SystemUser systemUser) {
//        if (!SingletonLoginUtils.validate(request)) {
//            return result(MessageConstants.SSO_STATUS_VERIFY_WRONG);
//        }
        // 服务器端，使用RSAUtils工具类对密文进行解密
//        String passWord = RSAUtils.decryptStringByJs(systemUser.getLoginPassword());
		String passWord = systemUser.getLoginPassword();
//        systemUser.setLoginPassword(MD5Utils.getMD5(passWord));
        try {
            String token = (String)getRequest().getSession().getAttribute(CommonConstants.SESSION_TOKEN);
            log.info("token: " + token);
            log.info("/system/login username: " + systemUser.getLoginName());
            //用户登录
            JsonResultDto<SsoUserDto> result = SsoSystemUtils.login("http://127.0.0.1:8088/api/login", token, systemUser.getLoginName(), passWord);
            if(result.getCode() != MessageEnum.API_STATUS_SUCCESS.getCode()) {
                return new AjaxResult(result.getCode(), result.getMsg());
            }
            SsoUserDto userDto = (SsoUserDto) result.getData();
            //获取公司信息
            JsonResultDto<SsoOrgCompanyDto> resultDto = SsoSystemUtils.getCompanyInfo("http://127.0.0.1:8088/api/getCompanyInfo", token, userDto.getUserName());
            if(resultDto.getCode() != MessageEnum.API_STATUS_SUCCESS.getCode()) {
                return new AjaxResult(resultDto.getCode(), resultDto.getMsg());
            }
            SsoOrgCompanyDto companyDto = (SsoOrgCompanyDto)resultDto.getData();
            SystemAuthorizingUser authorizingUser = null;
            if(companyDto == null ){
                authorizingUser = new SystemAuthorizingUser(
                        userDto.getUserName(), userDto.getUserName(),
                        userDto.getUserName(), userDto.getTrueName(), userDto.getUserType(),
                        "", "");
            }
            else {
                authorizingUser = new SystemAuthorizingUser(
                        userDto.getUserName(), userDto.getUserName(),
                        userDto.getUserName(), userDto.getTrueName(), userDto.getUserType(),
                        companyDto.getCusCode(), companyDto.getCompanyName());

                authorizingUser.setInputCopName(companyDto.getCompanyName());
                authorizingUser.setAreaCode(companyDto.getAreaCode());
                authorizingUser.setCopGbCode(companyDto.getCreditCode());
                authorizingUser.setCustomsCode(companyDto.getCusCode());
            }

            authorizingUser.setIcCode(userDto.getIcCode());
            authorizingUser.setEmail(userDto.getEmail());
            authorizingUser.setAddress(userDto.getAddress());
            authorizingUser.setPassword(userDto.getUserPwd());
            authorizingUser.setOrgCode(userDto.getOrgCode());
            authorizingUser.setTelephone(userDto.getTelNum());

            getRequest().getSession().setAttribute(CommonConstants.SESSION_USER_INFO, authorizingUser);
            systemUserService.updateLogByLoginName(authorizingUser.getLoginName(), ServletUtils.getIpAddr(), ServletUtils.getUserBrowser(), ServletUtils.getUserOperatingSystem());
        } catch (Exception e) {
            log.error("login error!", e);
            return result(MessageConstants.SSO_STATUS_FAIL);
        }
        return result(MessageConstants.SSO_STATUS_SUCCESS);
    }

    /**
     * 获取session
     * @return
     */
    @RequestMapping(value = "/getCurrentSession", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getCurrentSession(){
        return ResponseEntity.ok(getRequest().getSession().getAttribute(CommonConstants.SESSION_USER_INFO));
    }

    /**
     * 退出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        getRequest().getSession().invalidate();
        return redirectTo("/system");
    }

    /**
     * 进入登录页面时获取公钥的 modulus 和 exponent
     *
     * @return
     */
    @RequestMapping(value = "/getPublicKey",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getPublicKey(){
        AjaxResult ajaxResult=null;
        try{
            Map<String, Object> publicKeyMap = RSAUtils.getPublicKeyMap();
            if(publicKeyMap!=null){
                ajaxResult=json(MessageConstants.SSO_STATUS_SUCCESS,publicKeyMap);
            }
            else{
                ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            }
        }catch (Exception e){
            ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            log.error("getPublicKey()--error", e);
        }
        return  ajaxResult;
    }
}
