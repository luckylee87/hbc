package com.dsg.controller.ws;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.powerbridge.sso.dto.JsonResultDto;
import com.powerbridge.sso.dto.ProjectDto;
import com.dsg.common.constants.APIMessage;
import com.dsg.common.constants.CommonConstants;
import com.dsg.common.dto.GetMenuInputDto;
import com.dsg.common.util.toolbox.StringUtil;
import com.dsg.controller.base.BaseController;
import com.dsg.entity.setting.SsoOrgCompany;
import com.dsg.entity.setting.SsoProAuth;
import com.dsg.entity.setting.SsoUser;
import com.dsg.entity.setting.SsoUserFilter;
import com.dsg.service.setting.ISsoOrgCompanyService;
import com.dsg.service.setting.ISsoProAuthService;
import com.dsg.service.setting.ISsoUserFilterService;
import com.dsg.service.setting.ISsoUserService;
import com.dsg.service.ws.IAPIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：MainController
 * 类描述：接口控制器
 * 创建人：zcb
 * 创建时间：2017年11月7日 上午11:33:17
 */
@RequestMapping("/api")
@CrossOrigin
@RestController
@Slf4j
public class APIController extends BaseController {

    @Autowired
    private IAPIService apiService;

    @Autowired
    private ISsoUserService ssoUserService;

//    @Autowired
//    private BlsConfig blsConfig;

    @Autowired
    private ISsoOrgCompanyService ssoOrgCompanyService;

    @Autowired
    private ISsoProAuthService ssoProAuthService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ISsoUserFilterService ssoUserFilterService;

    /**
     * 菜单接口
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getMenus", method = RequestMethod.POST)
    public ResponseEntity<JsonResultDto> getMenus(GetMenuInputDto inputDto) {
        try {
            if (StringUtil.isBlank(inputDto.getUserName())) {
                return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_USER_NAME_EMPTY.getCode(), APIMessage.API_STATUS_USER_NAME_EMPTY.getMessage()));
            }
            String projectCode = inputDto.getProjectCode();
            //参数projectCode为空时，通过token缓存找到clientId，再查询数据库对应projectCode
            if (StringUtil.isBlank(projectCode)) {
                //获取token缓存
                Cache cache = cacheManager.getCache(CommonConstants.CACHE_TOKEN_KEY);
                if (null == cache) {
                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_TOKEN_CACHE_NOT_EXISTS.getCode(), APIMessage.API_STATUS_TOKEN_CACHE_NOT_EXISTS.getMessage()));
                }

                //获取clientId
                String clientId = cache.get(inputDto.getToken(), String.class);
                if (StringUtil.isBlank(clientId)) {
                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_TOKEN_INVALID.getCode(), APIMessage.API_STATUS_TOKEN_INVALID.getMessage()));
                }

                //通过clientId获取数据库对应实体
                QueryWrapper entityWrapper = new QueryWrapper();
                entityWrapper.eq("CLIENT_ID", clientId);
                SsoProAuth auth = ssoProAuthService.getOne(entityWrapper);
                if (null == auth) {
                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_CLIENT_ID_NOT_EXISTS.getCode(), APIMessage.API_STATUS_CLIENT_ID_NOT_EXISTS.getMessage()));
                }

                //配置的项目代码为空，返回错误信息
                projectCode = auth.getAuthProcode();
                if (StringUtil.isBlank(projectCode)) {
                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_PROJECT_CODE_EMPTY.getCode(), APIMessage.API_STATUS_PROJECT_CODE_EMPTY.getMessage()));
                }
                inputDto.setProjectCode(projectCode);
            }
            List<ProjectDto> projectDtoList = apiService.selectList(inputDto);
            return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_SUCCESS.getCode(), APIMessage.API_STATUS_SUCCESS.getMessage(), projectDtoList));
        } catch (Exception e) {
            log.error("getMenus error.", e);
            return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_SYSTEM_ERROR.getCode(), APIMessage.API_STATUS_SYSTEM_ERROR.getMessage()));
        }
    }


    /**
     * 获取该客户端所有有菜单权限的项目代码模块
     * @param inputDto 录入参数
     * @return ResponseEntity
     */
    @RequestMapping(value = "/getProjectCode", method = RequestMethod.POST)
    public ResponseEntity<JsonResultDto> getProjectCode(GetMenuInputDto inputDto) {
        try {
            if (StringUtil.isBlank(inputDto.getUserName())) {
                return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_USER_NAME_EMPTY.getCode(), APIMessage.API_STATUS_USER_NAME_EMPTY.getMessage()));
            }
            String projectCode = inputDto.getProjectCode();
            //参数projectCode为空时，通过token缓存找到clientId，再查询数据库对应projectCode
            if (StringUtil.isBlank(projectCode)) {
                //获取token缓存
                Cache cache = cacheManager.getCache(CommonConstants.CACHE_TOKEN_KEY);
                if (null == cache) {
                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_TOKEN_CACHE_NOT_EXISTS.getCode(), APIMessage.API_STATUS_TOKEN_CACHE_NOT_EXISTS.getMessage()));
                }

                //获取clientId
                String clientId = cache.get(inputDto.getToken(), String.class);
                if (StringUtil.isBlank(clientId)) {
                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_TOKEN_INVALID.getCode(), APIMessage.API_STATUS_TOKEN_INVALID.getMessage()));
                }

                //通过clientId获取数据库对应实体
                QueryWrapper entityWrapper = new QueryWrapper();
                entityWrapper.eq("CLIENT_ID", clientId);
                SsoProAuth auth = ssoProAuthService.getOne(entityWrapper);
                if (null == auth) {
                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_CLIENT_ID_NOT_EXISTS.getCode(), APIMessage.API_STATUS_CLIENT_ID_NOT_EXISTS.getMessage()));
                }

                //配置的项目代码为空，返回错误信息
                projectCode = auth.getAuthProcode();
                if (StringUtil.isBlank(projectCode)) {
                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_PROJECT_CODE_EMPTY.getCode(), APIMessage.API_STATUS_PROJECT_CODE_EMPTY.getMessage()));
                }
                inputDto.setProjectCode(projectCode);
            }
            List<ProjectDto> projectDtoList = apiService.getProjectCode(inputDto);
            if (projectDtoList.isEmpty()){
                return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_PROJECT_CODE_ERR.getCode(),APIMessage.API_STATUS_PROJECT_CODE_ERR.getMessage()));
            }
            return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_SUCCESS.getCode(), APIMessage.API_STATUS_SUCCESS.getMessage(), projectDtoList));
        } catch (Exception e) {
            log.error("getMenus error.", e);
            return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_SYSTEM_ERROR.getCode(), APIMessage.API_STATUS_SYSTEM_ERROR.getMessage()));
        }
    }


    /**
     * 更新用户
     *
     * @param ssoUser
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity<JsonResultDto> updateUser(@RequestBody SsoUser ssoUser) {
        JsonResultDto rs = null;
        try {
        ssoUser.setUserPwd(null);
        QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("USER_NAME",ssoUser.getUserName());
        List<SsoUser> checkSsoUsers = ssoUserService.list(entityWrapper);
        if (checkSsoUsers.size() > 0) {
            ssoUser.setOid(checkSsoUsers.get(0).getOid());
            APIMessage chkMsg = ssoUserService.updateUserCheck(ssoUser);
            if (chkMsg != null) {
                rs = new JsonResultDto(chkMsg.getCode(), chkMsg.getMessage());
                return ResponseEntity.ok(rs);
            }
            if (ssoUserService.updateById(ssoUser)) {
                return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_SUCCESS.getCode(), APIMessage.API_STATUS_SUCCESS.getMessage(), ssoUser));
            } else {
                return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_UPDATE_USER_FAIL.getCode(), APIMessage.API_STATUS_UPDATE_USER_FAIL.getMessage()));
            }
        } else {
            return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_USER_NOT_EXISTS.getCode(), APIMessage.API_STATUS_USER_NOT_EXISTS.getMessage()));
        }
        } catch (Exception e) {
            log.error("updateUser error.", e);
            rs = new JsonResultDto(APIMessage.API_STATUS_SYSTEM_ERROR.getCode(), APIMessage.API_STATUS_SYSTEM_ERROR.getMessage());
        }
        return ResponseEntity.ok(rs);

    }

    /**
     * 对外登陆
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JsonResultDto> login(String userName, String password) {

        try {
            log.info("api login username=" + userName);
            QueryWrapper<SsoUser> ssoUserEntityWrapper = new QueryWrapper<>();
            ssoUserEntityWrapper.eq("USER_NAME",userName);
            SsoUser ssoUser = ssoUserService.getOne(ssoUserEntityWrapper);
//            if(ssoUser != null){
//                //获取是否启用license授权码验证功能
//                String enable = blsConfig.getEnableValidateLicense();
//                //过滤"系统管理员"类型,不是系统管理员则需要软件授权码验证
//                if(!"SYS".equals(ssoUser.getUserType()) && "Y".equals(enable)) {
//                    QueryWrapper<SsoOrgCompany> ssoOrgCompanyEntityWrapper = new QueryWrapper<>();
//                    ssoOrgCompanyEntityWrapper.eq("ORG_CODE", ssoUser.getOrgCode());
//                    SsoOrgCompany ssoOrgCompany = ssoOrgCompanyService.selectOne(ssoOrgCompanyEntityWrapper);
//                    String entCode = "";
//                    if (ssoOrgCompany != null) {
//                        entCode = ssoOrgCompany.getCusCode();
//                    }
//                    log.info("软件授权码认证中...海关编码：" + entCode);
//                    try {
//                        GrantLicense gl = new GrantLicense();
//                        GrantLicense.LIC_CODE lc = gl.pass(entCode);
//                        log.info("license授权码解析成功，解析结果为：" + lc.getCode() + " " + lc.getMessage());
//                        if (!"00".equals(lc.getCode())) {
//                            return ResponseEntity.ok(new JsonResultDto(0, lc.getMessage()));
//                        }
//                    }catch (Exception e){
//                        log.error("获取license授权码失败",e);
//                        return ResponseEntity.ok(new JsonResultDto(APIMessage.API_GET_LICENSE_FAIL.getCode(), APIMessage.API_GET_LICENSE_FAIL.getMessage()));
//                    }
//                }
//            }else {
//                return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_USER_INFO_INVALID.getCode(), APIMessage.API_STATUS_USER_INFO_INVALID.getMessage()));
//            }
            if(ssoUser.getUserPwd().equals(password)){
                if ("N".equals(ssoUser.getIsValid())) {
                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_USER_INFO_ISVALID_INVALID.getCode(), APIMessage.API_STATUS_USER_INFO_ISVALID_INVALID.getMessage()));
                }
//                String token = getRequest().getParameter("token");
//                //获取token缓存
//                Cache cache = cacheManager.getCache(CommonConstants.CACHE_TOKEN_KEY);
//                if (null == cache) {
//                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_TOKEN_CACHE_NOT_EXISTS.getCode(), APIMessage.API_STATUS_TOKEN_CACHE_NOT_EXISTS.getMessage()));
//                }
//
//                //获取clientId
//                String clientId = cache.get(token, String.class);
//                if (StringUtil.isBlank(clientId)) {
//                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_TOKEN_INVALID.getCode(), APIMessage.API_STATUS_TOKEN_INVALID.getMessage()));
//                }
//
//                //通过clientId获取数据库对应实体
//                QueryWrapper ew = new QueryWrapper();
//                ew.eq("CLIENT_ID", clientId);
//                SsoProAuth auth = ssoProAuthService.getOne(ew);
//                if (null == auth) {
//                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_CLIENT_ID_NOT_EXISTS.getCode(), APIMessage.API_STATUS_CLIENT_ID_NOT_EXISTS.getMessage()));
//                }
//
//                //配置的项目代码为空，返回错误信息
//                String projectCode = auth.getAuthProcode();
//                if (StringUtil.isBlank(projectCode)) {
//                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_PROJECT_CODE_EMPTY.getCode(), APIMessage.API_STATUS_PROJECT_CODE_EMPTY.getMessage()));
//                }

                //登录用户为普通用户或者演示用户，且项目代码是权限平台代码，返回错误信息
//                if ( (CommonConstants.USER_TYPE_COM.equals(ssoUser.getUserType()) || CommonConstants.USER_TYPE_DEMO.equals(ssoUser.getUserType())) && projectCode.equals(CommonConstants.SSO_PLATFORM_CODE)) {
//                    return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_USER_LOGIN_UNAUTHORIZED.getCode(), APIMessage.API_STATUS_USER_LOGIN_UNAUTHORIZED.getMessage()));
//                }
                //设置过滤条件
                QueryWrapper<SsoUserFilter> entityWrapper = new QueryWrapper<>();
                entityWrapper.eq("USER_NAME", ssoUser.getUserName());
                SsoUserFilter userFilter = ssoUserFilterService.getOne(entityWrapper);
                if (userFilter != null) {
                    ssoUser.setOrgCodes(userFilter.getOrgCodeArray());
                }
                //设置用户所属企业
                QueryWrapper<SsoOrgCompany> entityWrapper2 = new QueryWrapper<>();
                entityWrapper2.eq("ORG_CODE", ssoUser.getOrgCode());
                SsoOrgCompany ssoOrgCompany = ssoOrgCompanyService.getOne(entityWrapper2);
                if(ssoOrgCompany != null){
                    ssoUser.setSsoOrgCompany(ssoOrgCompany);
                }
                return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_SUCCESS.getCode(), APIMessage.API_STATUS_SUCCESS.getMessage(), ssoUser));
            } else {
                return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_USER_INFO_INVALID.getCode(), APIMessage.API_STATUS_USER_INFO_INVALID.getMessage()));
            }
        } catch (Exception e) {
            log.error("api-login()",e);
            return ResponseEntity.ok(new JsonResultDto(APIMessage.API_STATUS_SYSTEM_ERROR.getCode(), APIMessage.API_STATUS_SYSTEM_ERROR.getMessage()));
        }
    }

    /**
     * 根据用户名获取企业信息接口
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getCompanyInfo", method = RequestMethod.POST)
    public ResponseEntity<JsonResultDto> getCompanyInfo(String userName) {
        JsonResultDto rs = null;
        try {
            QueryWrapper<SsoUser> wrapper = new QueryWrapper<SsoUser>();
            wrapper.eq("USER_NAME", userName);
            SsoUser ssoUser = ssoUserService.getOne(wrapper);
            if (ssoUser != null) {
                QueryWrapper<SsoOrgCompany> wrapperB = new QueryWrapper<SsoOrgCompany>();
                wrapperB.eq("ORG_CODE", ssoUser.getOrgCode());
                SsoOrgCompany ssoOrgCompany = ssoOrgCompanyService.getOne(wrapperB);
                rs = new JsonResultDto(APIMessage.API_STATUS_SUCCESS.getCode(), APIMessage.API_STATUS_SUCCESS.getMessage(), ssoOrgCompany);
            } else {
                rs = new JsonResultDto(APIMessage.API_STATUS_USER_NOT_EXISTS.getCode(), APIMessage.API_STATUS_USER_NOT_EXISTS.getMessage());
            }
        } catch (Exception e) {
            log.error("getCompanyInfo error.", e);
            rs = new JsonResultDto(APIMessage.API_STATUS_SYSTEM_ERROR.getCode(), APIMessage.API_STATUS_SYSTEM_ERROR.getMessage());
        }
        return ResponseEntity.ok(rs);
    }

    /**
     * 创建企业信息
     *
     * @param ssoOrgCompany
     * @param projectCode
     * @return
     */
    @RequestMapping(value = "/addCompany", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<JsonResultDto> addCompany(SsoOrgCompany ssoOrgCompany, String projectCode) {
        JsonResultDto rs = null;
        try {
            if (StringUtil.isEmpty(projectCode)) {
                rs = new JsonResultDto(APIMessage.API_STATUS_PROJECT_CODE_NOT_EMPTY.getCode(), APIMessage.API_STATUS_PROJECT_CODE_NOT_EMPTY.getMessage());
                return ResponseEntity.ok(rs);
            }
            if (StringUtil.isEmpty(ssoOrgCompany.getOrgCode())) {
                rs = new JsonResultDto(APIMessage.API_STATUS_ORG_CODE_NOT_EMPTY.getCode(), APIMessage.API_STATUS_ORG_CODE_NOT_EMPTY.getMessage());
                return ResponseEntity.ok(rs);
            }
            if (StringUtil.isEmpty(ssoOrgCompany.getCompanyName())) {
                rs = new JsonResultDto(APIMessage.API_STATUS_COMPANY_NAME_NOT_EMPTY.getCode(), APIMessage.API_STATUS_COMPANY_NAME_NOT_EMPTY.getMessage());
                return ResponseEntity.ok(rs);
            }
            boolean flag = ssoOrgCompanyService.addCompany(ssoOrgCompany, projectCode);
            if (flag) {
                rs = new JsonResultDto(APIMessage.API_STATUS_SUCCESS.getCode(), APIMessage.API_STATUS_SUCCESS.getMessage());
            } else {
                rs = new JsonResultDto(APIMessage.API_STATUS_SYSTEM_ERROR.getCode(), APIMessage.API_STATUS_SYSTEM_ERROR.getMessage());
            }
        } catch (Exception e) {
            log.error("addOrg error.", e);
            rs = new JsonResultDto(APIMessage.API_STATUS_SYSTEM_ERROR.getCode(), APIMessage.API_STATUS_SYSTEM_ERROR.getMessage());
        }
        return ResponseEntity.ok(rs);
    }

    /**
     * @param ssoUser
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResponseEntity<JsonResultDto> addUser(SsoUser ssoUser) {
        JsonResultDto rs = null;
        try {
            APIMessage chkMsg = ssoUserService.checkUser(ssoUser);
            if (chkMsg != null) {
                rs = new JsonResultDto(chkMsg.getCode(), chkMsg.getMessage());
                return ResponseEntity.ok(rs);
            }
            if (ssoUserService.addOrgUser(ssoUser)) {
                rs = new JsonResultDto(APIMessage.API_STATUS_SUCCESS.getCode(), APIMessage.API_STATUS_SUCCESS.getMessage());
            } else {
                rs = new JsonResultDto(APIMessage.API_STATUS_SYSTEM_ERROR.getCode(), APIMessage.API_STATUS_SYSTEM_ERROR.getMessage());
            }
        } catch (Exception e) {
            log.error("addUser error.", e);
            rs = new JsonResultDto(APIMessage.API_STATUS_SYSTEM_ERROR.getCode(), APIMessage.API_STATUS_SYSTEM_ERROR.getMessage());
        }
        return ResponseEntity.ok(rs);
    }

    @RequestMapping(value = "/userIsExists", method = RequestMethod.POST)
    public ResponseEntity<JsonResultDto> userIsExists(String userName) {
        JsonResultDto rs = null;
        try {
            QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
            entityWrapper.eq("USER_NAME", userName);
            int count = ssoUserService.count(entityWrapper);
            if (count != 0) {
                rs = new JsonResultDto(APIMessage.API_STATUS_USER_EXISTS.getCode(), APIMessage.API_STATUS_USER_EXISTS.getMessage());
            } else {
                rs = new JsonResultDto(APIMessage.API_STATUS_SUCCESS.getCode(), APIMessage.API_STATUS_SUCCESS.getMessage());
            }
        } catch (Exception e) {
            log.error("userIsExists error.", e);
            rs = new JsonResultDto(APIMessage.API_STATUS_SYSTEM_ERROR.getCode(), APIMessage.API_STATUS_SYSTEM_ERROR.getMessage());
        }
        return ResponseEntity.ok(rs);
    }
//    @RequestMapping(value = "/getProperty", method = RequestMethod.GET)
//    public ResponseEntity<String> getProperty(@RequestParam(name = "key") String key,
//                              @RequestParam(name = "defaultVal")String defaultVal){
//        String val = blsConfig.getProperty(key, defaultVal);
//        return ResponseEntity.ok(val);
//    }
}