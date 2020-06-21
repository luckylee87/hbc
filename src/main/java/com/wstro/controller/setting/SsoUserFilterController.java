package com.wstro.controller.setting;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.common.util.SingletonLoginUtils;
import com.wstro.common.util.UUIDGenerator;
import com.wstro.common.util.toolbox.StringUtil;
import com.wstro.controller.base.BaseController;
import com.wstro.entity.setting.SsoUserFilter;
import com.wstro.service.setting.ISsoUserFilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SsoUserController
 * 类描述：用户控制层
 * 创建人：wiezheng
 * 创建时间：2017年11月6日 上午10:12:17
 */
@RestController
@RequestMapping(value = "/ssoUserFilter")
@Slf4j
public class SsoUserFilterController extends BaseController {
    @Autowired
    private ISsoUserFilterService ssoUserFilterService;

    /**
     * 保存用户过滤条件
     * @param orgCodes 机构代码集合
     * @return
     */
    @RequestMapping(value = "/saveFilter", method = RequestMethod.POST)
    @ResponseBody

    public AjaxResult saveFilter(String orgCodes){
        AjaxResult ajaxResult = null;
        try {
            String userName = SingletonLoginUtils.getSystemUserId();
            if(StringUtil.isNotEmpty(userName)){
                QueryWrapper<SsoUserFilter> entityWrapper = new QueryWrapper<>();
                entityWrapper.eq("USER_NAME",userName);
                ssoUserFilterService.remove(entityWrapper);
            }
            if(orgCodes != null) {
                orgCodes = orgCodes.replace(',', '|');
            }
            SsoUserFilter ssoUserFilter = new SsoUserFilter();
            ssoUserFilter.setOid(UUIDGenerator.getUUID());
            ssoUserFilter.setIsNeedFilter("Y");
            ssoUserFilter.setUserName(userName);
            ssoUserFilter.setOrgCodeArray(orgCodes);
            boolean flag = ssoUserFilterService.save(ssoUserFilter);
            if(flag){
                return result(MessageConstants.SSO_STATUS_SUCCESS);
            }
            else{
                return setErrorJson("保存失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }
    }
    /**
     * 获取用户过滤条件
     * @return
     */
    @RequestMapping(value = "/getListByUserName", method = RequestMethod.GET)
    @ResponseBody

    public AjaxResult getListByUserName(){
        try {
            String userName = SingletonLoginUtils.getSystemUserId();
            QueryWrapper<SsoUserFilter> entityWrapper = new QueryWrapper<>();
            entityWrapper.eq("USER_NAME",userName);
            SsoUserFilter ssoUserFilter = ssoUserFilterService.getOne(entityWrapper);
            return json(MessageConstants.SSO_STATUS_SUCCESS, ssoUserFilter);
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }
    }

    /**
     * 查询用户过滤数据
     * @return
     */
    @RequestMapping(value = "/getUserFilterData", method = RequestMethod.GET)

    public AjaxResult getUserFilterData(String userName) {
        AjaxResult rs = null;
        try {
            QueryWrapper entityWrapper = new QueryWrapper();
            entityWrapper.eq("USER_NAME",userName);
            SsoUserFilter userFilter = ssoUserFilterService.getOne(entityWrapper);
            rs = json(MessageConstants.SSO_STATUS_SUCCESS, null != userFilter ? userFilter.getOrgCodeArray() : null);
        } catch (Exception e) {
            log.error("getUserFilterData error", e);
            rs = setErrorJson(e.toString());
        }
        return rs;
    }

    /**
     * 保存用户机构过滤信息
     * @return
     */
    @RequestMapping(value = "/saveUserFilter", method = RequestMethod.POST)

    public AjaxResult saveUserFilter(String userName, String userOrgFilter) {
        AjaxResult rs = null;
        try {
            boolean flag = ssoUserFilterService.insertEntity(userName, userOrgFilter);
            if(flag)
                rs = result(MessageConstants.SSO_STATUS_SUCCESS);
            else
                rs = result(MessageConstants.SSO_STATUS_FAIL);
        } catch (Exception e) {
            log.error("getUserFilterData error", e);
            rs = setErrorJson(e.toString());
        }
        return rs;
    }
}
