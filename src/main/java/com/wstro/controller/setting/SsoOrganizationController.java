package com.wstro.controller.setting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.wstro.common.constants.CommonConstants;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.common.security.SystemAuthorizingUser;
import com.wstro.common.util.SingletonLoginUtils;
import com.wstro.common.util.UUIDGenerator;
import com.wstro.common.util.toolbox.StringUtil;
import com.wstro.controller.base.BaseController;
import com.wstro.entity.setting.SsoOrgCompany;
import com.wstro.entity.setting.SsoOrganization;
import com.wstro.entity.setting.SsoRole;
import com.wstro.entity.setting.SsoUser;
import com.wstro.service.setting.ISsoOrgCompanyService;
import com.wstro.service.setting.ISsoOrganizationService;
import com.wstro.service.setting.ISsoRoleService;
import com.wstro.service.setting.ISsoUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构表 前端控制器
 * </p>
 *
 * @author zcb
 * @since 2017-10-30
 */
@Controller
@RequestMapping("/setting/ssoOrganization")
@CrossOrigin
@Slf4j
public class SsoOrganizationController extends BaseController {

    private static final Gson gson = new Gson();

    @Autowired
    private ISsoOrganizationService ssoOrganizationService;

    @Autowired
    private ISsoOrgCompanyService ssoOrgCompanyService;
    
    @Autowired
    private ISsoUserService ssoUserService;
    
    @Autowired
    private ISsoRoleService ssoRoleService;

    //获取树形结构JSON
    @RequestMapping(value = "/getMenu", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody

    public String getMenu() {
        SystemAuthorizingUser sysUser = SingletonLoginUtils.getSystemUser();
        if(CommonConstants.USER_TYPE_COM.equals(sysUser.getUserType())) {
            return null;
        }
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        QueryWrapper<SsoOrganization> wrapper = new QueryWrapper<SsoOrganization>();
        if(CommonConstants.USER_TYPE_ORG.equals(sysUser.getUserType())) {
            wrapper.eq("CODE", sysUser.getOrgCode());
        }
        //根据名称过滤
        String searchOrgName = getParameter("searchOrgName");
        if(StringUtil.isNotEmpty(searchOrgName)){
            wrapper.like("NAME",searchOrgName);
        }
        wrapper.orderByAsc("orderIndex");
        List<SsoOrganization> ssoOrganizationList = ssoOrganizationService.list(wrapper);
        if (ssoOrganizationList.size() == 0) {
            SsoOrganization ssoOrganization = new SsoOrganization();
            ssoOrganization.setName("请添加节点");
            ssoOrganization.setCode("1");
            ssoOrganization.setParentCode("0");
            ssoOrganizationList.add(ssoOrganization);
        }
        else {
            if(StringUtil.isNotEmpty(searchOrgName)) {
                List<SsoOrganization> ssoOrganizationList1 = new ArrayList<>();
                for (SsoOrganization ssoOrganization : ssoOrganizationList) {
                    getParentNode(ssoOrganization.getParentCode(),ssoOrganizationList1,ssoOrganizationList);
                }
                if(ssoOrganizationList1.size()!=0){
                    ssoOrganizationList.addAll(ssoOrganizationList1);
                }
            }
        }
        if ("" != null) {
            //根据角色，选出已经勾选的菜单
            Map<String, Object> columnMap = new HashMap<String, Object>();
            data.put("jsonMenu", ssoOrganizationList);
        }
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * 查找父级节点
     * @param code
     * @param orgList
     * @param sourceList 源数据集合
     */

    private void getParentNode(String code,List<SsoOrganization> orgList,List<SsoOrganization> sourceList) {
        QueryWrapper<SsoOrganization> entityWrapper = new QueryWrapper();
        entityWrapper.eq("CODE", code);
        SsoOrganization ssoOrganization = ssoOrganizationService.getOne(entityWrapper);
        if (ssoOrganization != null) {
            if (!isExit(code, orgList, sourceList)) {
                orgList.add(ssoOrganization);
            }
            if (StringUtil.isNotEmpty(ssoOrganization.getParentCode())) {
                getParentNode(ssoOrganization.getParentCode(), orgList, sourceList);
            }
        }
    }
    /**
     * 判断List中是否已存在code
     * @param code 代码
     * @param list 集合
     * @return
     */
    private boolean isExit(String code,List<SsoOrganization> list,List<SsoOrganization> sourceList){
        boolean result = false;
        for (SsoOrganization ssoOrganization : list) {
            if(code.equals(ssoOrganization.getCode())){
                result = true;
                break;
            }
        }
        for (SsoOrganization ssoOrganization : sourceList) {
            if(code.equals(ssoOrganization.getCode())){
                result = true;
                break;
            }
        }
        return result;
    }
    //根据oid查询SsoOrganization
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody

    public String find(String oid) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        QueryWrapper<SsoOrganization> wrapper = new QueryWrapper<SsoOrganization>();
        wrapper.eq("oid", oid);
        SsoOrganization ssoOrganization = ssoOrganizationService.getOne(wrapper);
        data.put("ssoOrganization", ssoOrganization);

        QueryWrapper<SsoOrgCompany> wrapperB = new QueryWrapper<SsoOrgCompany>();
        wrapperB.eq("ORG_CODE", ssoOrganization.getCode());
        SsoOrgCompany ssoOrgCompany = ssoOrgCompanyService.getOne(wrapperB);

        if(ssoOrgCompany != null) {
            /*判断主管海关和场所代码是否为null*/
            if (StringUtil.isEmpty(ssoOrgCompany.getMasterCuscd())) {
                ssoOrgCompany.setMasterCuscd("");
            }
            if (StringUtil.isEmpty(ssoOrgCompany.getAreaCode())) {
                ssoOrgCompany.setAreaCode("");
            }
        }

        data.put("ssoOrgCompany", ssoOrgCompany);
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    @RequestMapping(value = "/checkCode")
    @ResponseBody

    public String checkCode(String code, String oid) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> warper = new HashMap<String, Object>();
        warper.put("code", code);
        List<SsoOrganization> ssoOrganization = ssoOrganizationService.listByMap(warper);
        if (ssoOrganization.size() > 0) {
            json = this.transJson("1", "成功", 0);
        } else {
            json = this.transJson("1", "成功", 1);
        }

        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    //保存
    @RequestMapping(value = "/save")
    @ResponseBody

    public AjaxResult save(SsoOrganization ssoOrganization, SsoOrgCompany ssoOrgCompany, String oid2) {
        try {
            ssoOrgCompany.setOrgCode(ssoOrganization.getCode());
            ssoOrgCompany.setOid(oid2);

            SsoOrganization organization = ssoOrganizationService.getById(ssoOrganization);

            /*判断主管海关和场所代码是否为null*/
            if (StringUtil.isEmpty(ssoOrgCompany.getMasterCuscd())) {
                ssoOrgCompany.setMasterCuscd("");
            }
            if (StringUtil.isEmpty(ssoOrgCompany.getAreaCode())) {
                ssoOrgCompany.setAreaCode("");
            }

            //机构为空则新增机构和公司信息，不为空则修改机构和公司信息
            if (organization == null || "".equals(ssoOrganization.getOid())) {
                QueryWrapper<SsoOrganization> entityWrapper = new QueryWrapper();
                entityWrapper.eq("code", ssoOrganization.getCode());
                List<SsoOrganization> ssoOrganizations = ssoOrganizationService.list(entityWrapper);
                if (ssoOrganizations.size() > 0) {
                    return setErrorJson("机构代码已存在！");
                }
                //如果新增填写了企业海关编码，则检验企业海关编码是否存在
                if(StringUtil.isNotEmpty(ssoOrgCompany.getCusCode())) {
                    AjaxResult ajaxResult = validateCusCode(ssoOrgCompany.getCusCode(),null);
                    if(ajaxResult.getCode() != 1){
                        return ajaxResult;
                    }
                }
                ssoOrganization.setOid(UUIDGenerator.getUUID());
                ssoOrganizationService.save(ssoOrganization);

                ssoOrgCompany.setOid(UUIDGenerator.getUUID());
                ssoOrgCompanyService.save(ssoOrgCompany);
            } else {
                ssoOrganizationService.updateById(ssoOrganization);
                //如果新增填写了企业海关编码，则检验企业海关编码是否存在
                if(StringUtil.isNotEmpty(ssoOrgCompany.getCusCode())) {
                    AjaxResult ajaxResult = validateCusCode(ssoOrgCompany.getCusCode(),oid2);
                    if(ajaxResult.getCode() != 1){
                        return ajaxResult;
                    }
                }
                if (oid2 == null || "".equals(oid2)) {
                    ssoOrgCompany.setOid(UUIDGenerator.getUUID());
                    ssoOrgCompanyService.save(ssoOrgCompany);
                } else {
                    ssoOrgCompanyService.updateById(ssoOrgCompany);
                }
                //更新子节点的父节点代码
                QueryWrapper<SsoOrganization> WrapperB = new QueryWrapper<SsoOrganization>();
                WrapperB.eq("PARENT_CODE", organization.getCode());
                SsoOrganization org = new SsoOrganization();
                org.setParentCode(ssoOrganization.getCode());
                ssoOrganizationService.update(org, WrapperB);
            }
        }
        catch (Exception e){
            log.error("ssoOrganization save() --error",e);
            return  result(MessageConstants.SSO_STATUS_FAIL);
        }
        return result(MessageConstants.SSO_STATUS_SUCCESS);
    }

    //校验企业海关编码是否存在
    public AjaxResult validateCusCode(String cusCode,String oid){
        QueryWrapper<SsoOrgCompany> entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("CUS_CODE", cusCode);
        if(StringUtil.isNotEmpty(oid)){
            entityWrapper.ne("OID",oid);
        }
        List<SsoOrgCompany> ssoOrgCompanys = ssoOrgCompanyService.list(entityWrapper);
        if (ssoOrgCompanys != null && ssoOrgCompanys.size() > 0) {
            return setErrorJson("企业海关编码已存在！");
        }else{
            return result(MessageConstants.SSO_STATUS_SUCCESS);
        }
    }


    //删除节点及子节点
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public String delete(SsoOrganization ssoOrganization, String codes) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();

        QueryWrapper<SsoUser> entityWrapper = new QueryWrapper<>();
        entityWrapper.in("ORG_CODE",codes);
        ssoUserService.count(entityWrapper);
        int connt = ssoUserService.count(entityWrapper);

        QueryWrapper<SsoRole> entityWrapperB = new QueryWrapper<>();
        entityWrapperB.in("ORG_CODE",codes);
        int nunber = ssoRoleService.count(entityWrapperB);
        
        if(connt==0&&nunber==0){
        	 String[] code = codes.split(",");
             for (String cus : code) {
                 ssoOrganization.setCode(cus);
                 Map<String, Object> warper = new HashMap<String, Object>();
                 warper.put("code", ssoOrganization.getCode());
                 ssoOrganizationService.removeByMap(warper);
                 Map<String, Object> warperB = new HashMap<String, Object>();
                 warperB.put("ORG_CODE", ssoOrganization.getCode());
                 ssoOrgCompanyService.removeByMap(warperB);
             }
             data.put("ssoFunction", ssoOrganization);
             json = this.transJson("1", "成功", data);
             String jsonStr1 = gson.toJson(json);
             return jsonStr1;
        }else{
        	 json = this.transJson("0", "失败", data);
        	 String jsonStr2 = gson.toJson(json);
        	 return jsonStr2;
        }
        
    }

    //保存
    @RequestMapping(value = "/saveData", method = RequestMethod.GET)

    public String saveData(SsoOrganization ssoOrganization) {

        QueryWrapper Wrapper = new QueryWrapper();
        Wrapper.eq("code", ssoOrganization.getCode());
        ssoOrganizationService.update(ssoOrganization, Wrapper);

        return "redirect:/setting/ssoOrganization/list";
    }


    @RequestMapping(value = "/remove", method = RequestMethod.GET)

    public String remove(SsoOrganization ssoOrganization) {


        Map<String, Object> warper = new HashMap<String, Object>();
        warper.put("code", ssoOrganization.getCode());
        ssoOrganizationService.removeByMap(warper);


        return "redirect:/setting/ssoOrganization/list";
    }

    /**
     * 查询机构列表，用于新增/编辑角色页面下拉框展示
     */
    @RequestMapping(value = "/getOrgList", method = {RequestMethod.GET})
    @ResponseBody

    public AjaxResult getOrgList(String q) {
        AjaxResult rs = null;
        try {
            QueryWrapper ew = null;
            if (!StringUtil.isEmpty(q)) {
                ew = new QueryWrapper();
                ew.setEntity(new SsoOrganization());
                ew.like("name", "%" + q + "%");
            }
            List<SsoOrganization> list = ssoOrganizationService.list(ew);
            rs = json(MessageConstants.SSO_STATUS_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            rs = result(MessageConstants.SSO_STATUS_FAIL);
        }
        return rs;
    }

    //获取全部机构树形结构JSON
    @RequestMapping(value = "/getOrgTree", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody

    public Object getOrgTree() {
        Object ajaxResult = null;
        SystemAuthorizingUser sysUser = SingletonLoginUtils.getSystemUser();
        SsoOrganization organization = new SsoOrganization();
        try {
            if(sysUser.getUserType().equals(CommonConstants.USER_TYPE_COM)) {
                return null;
            }
            if(sysUser.getUserType().equals(CommonConstants.USER_TYPE_ORG)) {
                organization.setCode(sysUser.getOrgCode());
            }
            //根据名称过滤
            String orgName = getParameter("searchOrgName");
            if(StringUtil.isNotEmpty(orgName)){
                organization.setName(orgName);
            }
            //根据用户名称过滤
            String userName = getParameter("userName");
            if(StringUtil.isNotEmpty(userName)){
                organization.setUserName(userName);
            }
            //根据真实姓名过滤
            String trueName = getParameter("trueName");
            if(StringUtil.isNotEmpty(trueName)){
                organization.setTrueName(trueName);
            }
            List<SsoOrganization> ssoOrganizations = ssoOrganizationService.getOrgTree(organization);
            if(ssoOrganizations.size() != 0 ) {
                if(StringUtil.isNotEmpty(orgName)) {
                    List<SsoOrganization> ssoOrganizationList1 = new ArrayList<>();
                    for (SsoOrganization ssoOrganization : ssoOrganizations) {
                        getParentNode(ssoOrganization.getParentCode(),ssoOrganizationList1,ssoOrganizations);
                    }
                    if(ssoOrganizationList1.size()!=0){
                        ssoOrganizations.addAll(ssoOrganizationList1);
                    }
                }
            }
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS,ssoOrganizations,ssoOrganizations.size()).getData();
            return ajaxResult;
        }catch (Exception e){
            log.error("getOrgTree error", e);
            return result(MessageConstants.SSO_STATUS_FAIL);
        }
    }

    //通过机构code获取指定机构树
    @RequestMapping(value = "/getOrgTreeByCode", method = RequestMethod.GET)
    @ResponseBody

    public AjaxResult getOrgTreeByCode(String orgCode) {
        try {
            List<SsoOrganization> list = ssoOrganizationService.selectOrgTreeByCode(orgCode);
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(list));
            return json(MessageConstants.SSO_STATUS_SUCCESS, jsonArray);
        }catch (Exception e){
            log.error("getOrgTree error", e);
            return result(MessageConstants.SSO_STATUS_FAIL);
        }
    }

}
