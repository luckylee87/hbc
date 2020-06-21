package com.wstro.controller.setting;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.common.util.UUIDGenerator;
import com.wstro.common.util.toolbox.StringUtil;
import com.wstro.controller.base.BaseController;
import com.wstro.entity.setting.SsoProAuth;
import com.wstro.service.setting.ISsoProAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SsoProAuthController
 * 类描述：用户控制层
 * 创建人：wiezheng
 * 创建时间：2017年11月6日 上午10:12:17
 */
@RestController
@RequestMapping(value = "/ssoProAuth")
@Slf4j
public class SsoProAuthController extends BaseController {

    @Autowired
    private ISsoProAuthService ssoProAuthService;

    /**
     * 根据项目代码获取单条数据
     * @param code
     * @return
     */
    @RequestMapping(value = "/list/getProAuthByCode/{code}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody

    public ResponseEntity getProAuthByCode(@PathVariable String code){

        QueryWrapper entityWrapper = new QueryWrapper();
        entityWrapper.eq("PROJECT_CODE",code);

        SsoProAuth ssoProAuth = ssoProAuthService.getOne(entityWrapper);
        if(ssoProAuth != null){

            return ResponseEntity.ok(json(MessageConstants.SSO_STATUS_SUCCESS,ssoProAuth));
        }else {
            return ResponseEntity.ok(result(MessageConstants.SSO_STATUS_FAIL));
        }

    }

    /**
     * 批量删除
     * @return
     */
    @RequestMapping(value = "/list/deleteByList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody

    public AjaxResult delete() {
        try{
            String idList = getParameter("idList");
            Boolean flag = ssoProAuthService.removeByIds(Arrays.asList(idList.split(",")));
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
     * @param ssoProAuth
     * @return
     */
    @RequestMapping(value = "/list/update", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody

    public ResponseEntity updateProject(@RequestBody SsoProAuth ssoProAuth) {
        AjaxResult ajaxResult = null;
        try {

            boolean flag = ssoProAuthService.updateById(ssoProAuth);
            if (flag) {
                ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS,ssoProAuth);
            } else {
                ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            }

            return ResponseEntity.ok(ajaxResult);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(result(MessageConstants.SSO_STATUS_FAIL));
        }

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
            SsoProAuth ssoProAuth = ssoProAuthService.getById(id);
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoProAuth);
        } catch (Exception e) {
            ajaxResult = result(MessageConstants.SSO_STATUS_SUCCESS);
            log.error("editProAuth()--err", e);
        }
        return ajaxResult;
    }

    /**
     * 创建
     * @param ssoProAuth
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list/add", method = {RequestMethod.POST, RequestMethod.GET})

    public AjaxResult create(@RequestBody SsoProAuth ssoProAuth) {

        try{

            ssoProAuth.setOid(UUIDGenerator.getUUID());
            boolean falg = ssoProAuthService.save(ssoProAuth);
            if(falg)
                return result(MessageConstants.SSO_STATUS_SUCCESS);
            else
                return result(MessageConstants.SSO_STATUS_FAIL);
        }catch (Exception e){
            e.printStackTrace();
            return result(MessageConstants.SSO_STATUS_FAIL);
        }
    }

    /**
     * 查询
     * @param ssoProAuth
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody

    public AjaxResult list(SsoProAuth ssoProAuth) {
        AjaxResult ajaxResult = null;
        try {
            Page page = getPage();  // 分页
            String sort = getParameter("sort");
            boolean sortOrder = getOrderSort(getParameter("sortOrder"));

//            if (StringUtil.isNotEmpty(sort)) {
//                page.setAsc(sortOrder);
//                page.setOrderByField(sort);  // 排序
//            }else {
//                page.setAsc(false);
////                page.setOrderByField("A.OP_TIME");
//            }

            Page<SsoProAuth> proAuthPage = ssoProAuthService.selectByList(page,ssoProAuth);

            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, proAuthPage.getRecords(), (int) page.getTotal()); // 格式要返回的数据
            return ajaxResult;
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }

    }

    @ResponseBody
    @RequestMapping(value = "/listByClientIdAndClientKey/{clientId}/{clientKey}", method = {RequestMethod.POST, RequestMethod.GET})

    public AjaxResult listByClientIdAndClientKey(@PathVariable String clientId, @PathVariable String clientKey) {

        try{
            QueryWrapper entityWrapper = new QueryWrapper();
            entityWrapper.eq("CLIENT_ID",clientId);
//            entityWrapper.and();
            entityWrapper.eq("CLIENT_KEY",clientKey);
            List<SsoProAuth> list = ssoProAuthService.list(entityWrapper);
            return json(MessageConstants.SSO_STATUS_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }
    }

}
