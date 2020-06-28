package com.dsg.controller.setting;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsg.common.constants.MessageConstants;
import com.dsg.common.dto.AjaxResult;
import com.dsg.common.util.UUIDGenerator;
import com.dsg.common.util.toolbox.CollectionUtil;
import com.dsg.common.util.toolbox.StringUtil;
import com.dsg.controller.base.BaseController;
import com.dsg.entity.setting.SsoOrgCompany;
import com.dsg.entity.setting.SsoUser;
import com.dsg.service.setting.ISsoOrgCompanyService;
import com.dsg.service.setting.ISsoUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 组织机构-公司信息表 前端控制器
 * </p>
 *
 * @author zcb
 * @since 2017-10-30
 */
@Controller
@RequestMapping("/setting/ssoOrgCompany")
@Slf4j
public class SsoOrgCompanyController extends BaseController {

	@Autowired
    private ISsoOrgCompanyService ssoOrgCompanyService;
	@Autowired
    private ISsoUserService ssoUserService;


    @RequestMapping(value = "/getCompany", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody

    public AjaxResult list(SsoOrgCompany ssoOrgCompany) {
        AjaxResult ajaxResult = null;
        try {
            Page page = getPage();  // 分页
            Page page1 = getPage();
            String sort = getParameter("sort");
            boolean sortOrder = getOrderSort(getParameter("sortOrder"));
            String str = "";
//            if (StringUtil.isNotEmpty(sort)) {
////                page.setAsc(sortOrder);
//                page.setAsc(sortOrder);
//                page.setOrderByField(sort);  // 排序
//            }
           // BsspUtil.filterCopEnt(sasDclBsc, null);
            Page<SsoOrgCompany> profunPage = ssoOrgCompanyService.selectByList(page,ssoOrgCompany);
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, profunPage.getRecords(), (int) page.getTotal()); // 格式要返回的数据
            return ajaxResult;
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }

    }

    @RequestMapping("/edit")

	public String toEdit(String oid,Model model){
    	SsoOrgCompany ssoOrgCompany = ssoOrgCompanyService.getById(oid);
    	model.addAttribute("ssoOrgCompany", ssoOrgCompany);
    	model.addAttribute("companyName", ssoOrgCompany.getCompanyName());
		return "/setting/orgCompany/edit";
	}


    @RequestMapping("/save")

	public String save(SsoOrgCompany ssoOrgCompany){
    	if(ssoOrgCompany.getOid()==null || "".equals(ssoOrgCompany.getOid())){
    		ssoOrgCompany.setOid(UUIDGenerator.getUUID());
    		ssoOrgCompanyService.save(ssoOrgCompany);
    	}else{
    		ssoOrgCompanyService.updateById(ssoOrgCompany);
    	}

		return "redirect:/setting/ssoOrgCompany/list";
	}


    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody

    public AjaxResult delete() {

        try {

            String idList = getParameter("idList");
            Boolean flag = ssoOrgCompanyService.removeByIds(Arrays.asList(idList.split(",")));
            if(flag)
                return result(MessageConstants.SSO_STATUS_SUCCESS);
            else
                return result(MessageConstants.SSO_STATUS_FAIL);
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }

    }
    /**
     * 通过机构code获取指定机构信息
     * @param orgCode 机构代码
     * @return AjaxResult
     */
    @GetMapping(value = "/getOrgMessageByCode")
    @ResponseBody

    @ApiOperation(value = "通过机构code获取指定机构信息")
    @ApiImplicitParam(value = "机构code",name = "orgCode")
    public AjaxResult getOrgMessageByCode(String orgCode) {
        try {
            QueryWrapper<SsoOrgCompany> ssoOrgCompanyEntityWrapper = new QueryWrapper<>();
            ssoOrgCompanyEntityWrapper.eq("CUS_CODE",orgCode);
            SsoOrgCompany ssoOrgCompany = ssoOrgCompanyService.getOne(ssoOrgCompanyEntityWrapper);
            if (ssoOrgCompany != null) {
                return json(MessageConstants.SSO_STATUS_SUCCESS, ssoOrgCompany);
            }
            return result(MessageConstants.SSO_STATUS_FAIL);
        }catch (Exception e){
            log.error("getOrgMessageByCode-error", e);
            return result(MessageConstants.SSO_STATUS_FAIL);
        }
    }

    /**
     * 通过机构社会信用代码获取指定机构信息
     * @param creditCode 机构代码
     * @return AjaxResult
     */
    @GetMapping(value = "/getOrgMessageByCreditCode")
    @ResponseBody

    @ApiOperation(value = "通过机构社会信用代码获取指定机构信息")
    @ApiImplicitParam(value = "社会信用代码",name = "creditCode")
    public AjaxResult getOrgMessageByCreditCode(String creditCode) {
        try {
            QueryWrapper<SsoOrgCompany> ssoOrgCompanyEntityWrapper = new QueryWrapper<>();
            ssoOrgCompanyEntityWrapper.eq("CREDIT_CODE",creditCode);
            List<SsoOrgCompany> ssoOrgCompanyList = ssoOrgCompanyService.list(ssoOrgCompanyEntityWrapper);
            if (CollectionUtil.isNotEmpty(ssoOrgCompanyList)) {
                return json(MessageConstants.SSO_STATUS_SUCCESS, ssoOrgCompanyList.get(0));
            }
            return result(MessageConstants.SSO_STATUS_FAIL);
        }catch (Exception e){
            log.error("getOrgMessageByCode-error", e);
            return result(MessageConstants.SSO_STATUS_FAIL);
        }
    }

    /**
     * 通过企业海关编码更新企业信息
     * @param jsonObject 企业信息
     * @return AjaxResult
     */
    @RequestMapping(value = "/updateCompanyByCusCode", method = RequestMethod.POST)
    @ResponseBody

    @ApiOperation(value = "通过企业海关编码更新企业信息")
    public AjaxResult updateCompanyByCusCode(@RequestBody JSONObject jsonObject) {
        try {
            log.info("同步企业信息到SSO，jsonObject={}",jsonObject);
            QueryWrapper<SsoOrgCompany> ssoOrgCompanyEntityWrapper = new QueryWrapper<>();
            ssoOrgCompanyEntityWrapper.eq("CUS_CODE",jsonObject.get("codCus").toString());
            SsoOrgCompany ssoOrgCompany = ssoOrgCompanyService.getOne(ssoOrgCompanyEntityWrapper);
            if (ssoOrgCompany != null) {
                if(StringUtil.isNotEmpty(jsonObject.get("creditCode").toString()))
                    ssoOrgCompany.setCreditCode(jsonObject.get("creditCode").toString());
                if(StringUtil.isNotEmpty(jsonObject.get("companyName").toString()))
                    ssoOrgCompany.setCompanyName(jsonObject.get("companyName").toString());
                if(StringUtil.isNotEmpty(jsonObject.get("address").toString()))
                    ssoOrgCompany.setAddress(jsonObject.get("address").toString());
                if(StringUtil.isNotEmpty(jsonObject.get("linkMan").toString()))
                    ssoOrgCompany.setLinkMan(jsonObject.get("linkMan").toString());
                if(StringUtil.isNotEmpty(jsonObject.get("linkTel").toString()))
                    ssoOrgCompany.setLinkTel(jsonObject.get("linkTel").toString());
                if(StringUtil.isNotEmpty(jsonObject.get("masterCuscd").toString()))
                    ssoOrgCompany.setMasterCuscd(jsonObject.get("masterCuscd").toString());
                if(StringUtil.isNotEmpty(jsonObject.get("areaCode").toString()))
                    ssoOrgCompany.setAreaCode(jsonObject.get("areaCode").toString());
                ssoOrgCompanyService.updateById(ssoOrgCompany);
                return result(MessageConstants.SSO_STATUS_SUCCESS);
            }else{
                return json(MessageConstants.SSO_STATUS_FAIL,"该企业海关编码不存在！");
            }
        }catch (Exception e){
            log.error("updateCompanyByCusCode-error", e);
            return json(MessageConstants.SSO_STATUS_FAIL,e.getMessage());
        }
    }
    @ApiOperation(value = "通过传入企业内部编号取某个用户信息(默认取第一个)")
    @ApiResponses({
            @ApiResponse(response = AjaxResult.class,code = 0,message = "失败",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 1,message = "成功!",responseContainer = "AjaxResult"),
    })
    @RequestMapping(value = "/getSsoUserByCopNo", method = RequestMethod.GET)
    @ResponseBody

    public AjaxResult getSsoUserByCopNo(@ApiParam(value = "企业内部编号", name = "inputCopNo", required = true) @RequestParam(value = "inputCopNo") String inputCopNo) {
        try {
            QueryWrapper<SsoOrgCompany> ssoOrgCompanyEntityWrapper = new QueryWrapper<>();
            ssoOrgCompanyEntityWrapper.eq("CUS_CODE", inputCopNo);
            SsoOrgCompany ssoOrgCompany = ssoOrgCompanyService.getOne(ssoOrgCompanyEntityWrapper);
            if (ssoOrgCompany != null) {
                QueryWrapper<SsoUser> ssoUserEntityWrapper = new QueryWrapper<>();
                ssoUserEntityWrapper.eq("ORG_CODE", ssoOrgCompany.getOrgCode());
                List<SsoUser> ssoUserList = ssoUserService.list(ssoUserEntityWrapper);
                if (CollectionUtils.isNotEmpty(ssoUserList)) {
                    return json(MessageConstants.SSO_STATUS_SUCCESS, ssoUserList.get(0).getUserName());
                }
            }
            return result(MessageConstants.SSO_ORGANIZATION_CODE_NO_EXIST);
        } catch (Exception e) {
            log.error("getSsoUserByCopNo()--error ", e);
            return setErrorJson(e.toString());
        }
    }
    @ApiOperation(value = "通过传入企业海关编码获取该企业下面所有用户名")
    @ApiResponses({
            @ApiResponse(response = AjaxResult.class,code = 0,message = "失败",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 1,message = "成功!",responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class,code = 1014,message = "该企业机构代码不存在",responseContainer = "AjaxResult")
    })
    @RequestMapping(value = "/getUserByInputCopNo", method = RequestMethod.GET)
    @ResponseBody

    public AjaxResult getUserByInputCopNo(@ApiParam(value = "企业内部编号", name = "inputCopNo", required = true) @RequestParam(value = "inputCopNo") String inputCopNo) {
        try {
            QueryWrapper<SsoOrgCompany> ssoOrgCompanyEntityWrapper = new QueryWrapper<>();
            ssoOrgCompanyEntityWrapper.eq("CUS_CODE", inputCopNo);
            SsoOrgCompany ssoOrgCompany = ssoOrgCompanyService.getOne(ssoOrgCompanyEntityWrapper);
            if (ssoOrgCompany != null) {
                QueryWrapper<SsoUser> ssoUserEntityWrapper = new QueryWrapper<>();
                ssoUserEntityWrapper.select("OID AS OID,USER_NAME AS USERNAME,EMAIL AS EMAIL,TRUE_NAME AS TRUENAME ");
                ssoUserEntityWrapper.eq("ORG_CODE", ssoOrgCompany.getOrgCode());
                List<SsoUser> ssoUserList = ssoUserService.list(ssoUserEntityWrapper);
                return json(MessageConstants.SSO_STATUS_SUCCESS, ssoUserList);
            }else {
                return result(MessageConstants.SSO_USER_ORGCOMPANY_CODE_NOT_EXISTS);
            }
        } catch (Exception e) {
            log.error("getSsoUserByCopNo()--error ", e);
            return setErrorJson(e.toString());
        }
    }
}
