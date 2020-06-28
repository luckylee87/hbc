package com.dsg.controller.setting;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.dsg.common.constants.CommonConstants;
import com.dsg.common.constants.MessageConstants;
import com.dsg.common.dto.AjaxResult;
import com.dsg.common.security.SystemAuthorizingUser;
import com.dsg.common.util.SingletonLoginUtils;
import com.dsg.common.util.UUIDGenerator;
import com.dsg.common.util.toolbox.StringUtil;
import com.dsg.controller.base.BaseController;
import com.dsg.dao.setting.SsoFunctionMapper;
import com.dsg.entity.setting.SsoFunction;
import com.dsg.service.setting.ISsoFunctionService;
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
 * 功能维护表 前端控制器
 * </p>
 *
 * @author zcb
 * @since 2017-10-18
 * @updateBy jokylao
 * @updateTime 2017-11-15
 */
@Controller
@RequestMapping("/setting/ssoFunction")
@CrossOrigin
@Slf4j
public class SsoFunctionController extends BaseController {

    private static final Gson gson = new Gson();
    //子节点
    private static  List<String> childList = new ArrayList<>();

    @Autowired
    private ISsoFunctionService ssoFunctionService;
    @Autowired
    private SsoFunctionMapper ssoFunctionMapper;

    /**
     * 获取功能树源数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMenu", method = {RequestMethod.GET, RequestMethod.POST})
    public Object getMenu() {
        try {
            Object ajaxResult = null;
            QueryWrapper<SsoFunction> entityWrapper = new QueryWrapper<>();
            //根据名称过滤
            String funcName = getParameter("searchFuncName");
            if (StringUtil.isNotEmpty(funcName)) {
                entityWrapper.like("NAME", funcName);
            }
            entityWrapper.orderByAsc("ORDER_INDEX");
            List<SsoFunction> ssoFunctList = ssoFunctionService.list(entityWrapper);
            if (ssoFunctList.size() != 0) {
                //搜索树形结构时，递归搜索父级节点
                if (StringUtil.isNotEmpty(funcName)) {
                    List<SsoFunction> ssoFunctions = new ArrayList<>();
                    for (SsoFunction ssoFunction : ssoFunctList) {
                        getParentNode(ssoFunction.getParentCode(), ssoFunctions, ssoFunctList);
                    }
                    if (ssoFunctions.size() != 0) {
                        ssoFunctList.addAll(ssoFunctions);
                    }
                }
            }
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoFunctList, ssoFunctList.size()).getData();
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sso_function-getMenu--", e);
            return e.toString();
        }
    }
    /**
     * 查找父级节点
     * @param code
     * @param funcList
     * @param sourceList 源数据集合
     */

    private void getParentNode(String code,List<SsoFunction> funcList,List<SsoFunction> sourceList) {
        QueryWrapper<SsoFunction> entityWrapper = new QueryWrapper();
        entityWrapper.eq("CODE", code);
        entityWrapper.orderByAsc("ORDER_INDEX");
        SsoFunction ssoFunction = ssoFunctionService.getOne(entityWrapper);
        if (ssoFunction != null) {
            if (!isExit(code, funcList,sourceList)) {
                funcList.add(ssoFunction);
            }
            if (StringUtil.isNotEmpty(ssoFunction.getParentCode())) {
                getParentNode(ssoFunction.getParentCode(), funcList,sourceList);
            }
        }
    }

    /**
     * 判断List中是否已存在code
     * @param code 代码
     * @param list 集合
     * @return
     */
    private boolean isExit(String code,List<SsoFunction> list,List<SsoFunction> sourceList){
        boolean result = false;
        for (SsoFunction ssoFunction : list) {
            if(code.equals(ssoFunction.getCode())){
                result = true;
                break;
            }
        }
        for (SsoFunction ssoFunction : sourceList) {
            if(code.equals(ssoFunction.getCode())){
                result = true;
                break;
            }
        }
        return result;
    }
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody

    public String find(String oid) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();

        QueryWrapper<SsoFunction> wrapper = new QueryWrapper<SsoFunction>();
        wrapper.eq("oid", oid);
        SsoFunction ssoFunction = ssoFunctionService.getOne(wrapper);

        data.put("ssoFunction", ssoFunction);
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    @RequestMapping(value = "/findByCode", method = RequestMethod.GET)
    @ResponseBody

    public String findByCode(String code) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();

        QueryWrapper<SsoFunction> wrapper = new QueryWrapper<SsoFunction>();
        wrapper.eq("code", code);
        SsoFunction ssoFunction = ssoFunctionService.getOne(wrapper);
        data.put("ssoFunction", ssoFunction);
        if(ssoFunction!=null){
            json = this.transJson("0", "成功", data);
        }else{
            json = this.transJson("1", "成功", data);
        }


        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * 保存
     *
     * @param ssoFunction
     * @return
     */
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody

    public AjaxResult save(SsoFunction ssoFunction,String codes) {
        AjaxResult ajaxResult = null;
       
        try {
        	SsoFunction function = ssoFunctionService.getById(ssoFunction);

            if (function == null || "".equals(function.getOid())) {
            	ssoFunction.setOid(UUIDGenerator.getUUID());
            	ssoFunctionService.save(ssoFunction);

            } else {
            	
            	//级联修改是都授权，是否显示。当为否时，子节点全设置为否
            	ssoFunctionService.updateById(ssoFunction);
            	if("N".equals(ssoFunction.getIsShow())||"N".equals(ssoFunction.getIsAuthorized())){
            		String[] code = codes.split(",");
                    for (String cus : code) {
                    	SsoFunction org = new SsoFunction();
                    	org.setCode(cus);
                    	if("N".equals(ssoFunction.getIsShow())){
                        	org.setIsShow("N");
                        }
                        if("N".equals(ssoFunction.getIsAuthorized())){
                        	org.setIsAuthorized("N");
                        }
                        QueryWrapper<SsoFunction> wrapper = new QueryWrapper<SsoFunction>();
                        wrapper.eq("code", cus);
                    	ssoFunctionService.update(org,wrapper);
                    }
            	}
            	System.out.println("Y".equals(ssoFunction.getIsShow()));
            	if("Y".equals(ssoFunction.getIsShow())){
            		QueryWrapper<SsoFunction> WrapperB = new QueryWrapper<SsoFunction>();
                    WrapperB.eq("CODE", ssoFunction.getParentCode());
                    SsoFunction org = new SsoFunction();
                    org.setIsShow("Y");                                      
                    ssoFunctionService.update(org, WrapperB);
            	}
            	
            	if("Y".equals(ssoFunction.getIsAuthorized())){
            		QueryWrapper<SsoFunction> WrapperB = new QueryWrapper<SsoFunction>();
            		WrapperB.eq("CODE", ssoFunction.getParentCode());
                    SsoFunction org = new SsoFunction();
                    org.setIsAuthorized("Y");                                      
                    ssoFunctionService.update(org, WrapperB);
            	}
            	
            	if(!function.getCode().equals(ssoFunction.getCode())){
            		 //更新子节点的父节点代码SsoFunction
                    QueryWrapper<SsoFunction> WrapperB = new QueryWrapper<SsoFunction>();
                    WrapperB.eq("PARENT_CODE", function.getCode());
                    SsoFunction org = new SsoFunction();
                    org.setParentCode(ssoFunction.getCode());
                                       
                    ssoFunctionService.update(org, WrapperB);
            	}
               
        	       	
      
                ajaxResult = result(MessageConstants.SSO_STATUS_SUCCESS);
            }
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return setErrorJson(e.toString());
        }
    }

    /**
     * 删除
     * @param code
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody

    public AjaxResult delete(String code) {
        QueryWrapper<SsoFunction> entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("code", code);
        SsoFunction ssoFunction = ssoFunctionService.getOne(entityWrapper);
        try {
            childList = new ArrayList<String>();
            childList.add(ssoFunction.getOid());
            //获取子节点
            List<String> ssoFunctionIds = getAllChildNode(ssoFunction, code);
            boolean flag = ssoFunctionService.removeByIds(ssoFunctionIds);
            if (flag) {
                return result(MessageConstants.SSO_STATUS_SUCCESS);
            } else {
                return result(MessageConstants.SSO_STATUS_FAIL);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return setErrorJson(e.toString());
        }
    }

    /**
     * 根据功能代码，获取所有子节点的数据集合
     * @param ssoFunction
     * @param parentCode
     * @return
     */

    private List<String> getAllChildNode(SsoFunction ssoFunction,String parentCode){
        QueryWrapper<SsoFunction> entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("PARENT_CODE",parentCode);
        List<SsoFunction> ssoFunctions = ssoFunctionService.list(entityWrapper);
        if(ssoFunctions.size() > 0) {
            for (SsoFunction ssoFunction1 : ssoFunctions) {
                return getAllChildNode(ssoFunction1,ssoFunction1.getCode());
            }
        }
        else{
            childList.add(ssoFunction.getOid());
        }
        return childList;
    }
    /**
     * 获取授权功能树源数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAuthMenu", method = {RequestMethod.GET})

    public Object getAuthMenu() {
        try {
            Object ajaxResult = null;
            QueryWrapper<SsoFunction> entityWrapper = new QueryWrapper<>();
            //根据名称过滤
            String funcName = getParameter("searchFuncName");
            if (StringUtil.isNotEmpty(funcName)) {
                entityWrapper.like("NAME", funcName);
            }
            SystemAuthorizingUser user = SingletonLoginUtils.getSystemUser();
            if(user != null && CommonConstants.USER_TYPE_ORG.equals(user.getUserType())) {
                Map<String,String> param = new HashMap<>();
                param.put("userName", user.getUserName());

                String projectCode= getParameter("projectCode");
                param.put("projectCode", projectCode);

                List<String> authFuncCode = ssoFunctionMapper.getAuthFuncCode(param);
                entityWrapper.in("CODE", authFuncCode);
            }
            entityWrapper.orderByAsc("ORDER_INDEX");
            List<SsoFunction> ssoFunctList = ssoFunctionService.list(entityWrapper);
            if (ssoFunctList.size() != 0) {
                //搜索树形结构时，递归搜索父级节点
                if (StringUtil.isNotEmpty(funcName)) {
                    List<SsoFunction> ssoFunctions = new ArrayList<>();
                    for (SsoFunction ssoFunction : ssoFunctList) {
                        getParentNode(ssoFunction.getParentCode(), ssoFunctions, ssoFunctList);
                    }
                    if (ssoFunctions.size() != 0) {
                        ssoFunctList.addAll(ssoFunctions);
                    }
                }
            }
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoFunctList, ssoFunctList.size()).getData();
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sso_function-getMenu--", e);
            return e.toString();
        }
    }
}
