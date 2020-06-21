package com.wstro.controller.setting;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.common.util.UUIDGenerator;
import com.wstro.common.util.toolbox.StringUtil;
import com.wstro.controller.base.BaseController;
import com.wstro.entity.setting.Profun;
import com.wstro.entity.setting.SsoProject;
import com.wstro.entity.setting.SsoRoleFunc;
import com.wstro.service.setting.IProfunSerivce;
import com.wstro.service.setting.IProjectSerivce;
import com.wstro.service.setting.ISsoRoleFuncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：ProfunController
 * 类描述：项目功能维护控制层
 * 创建人：weizheng
 * 创建时间：2017年4月27日 下午10:12:17
 */
@Controller
@RequestMapping("/profun")
@CrossOrigin
@RestController
@Slf4j
public class ProfunController extends BaseController {


    @Autowired
    private IProfunSerivce profunSerivce;

    @Autowired
    private IProjectSerivce projectSerivce;

    @Autowired
    private ISsoRoleFuncService ssoRoleFuncService;

    /**
     * 创建
     * @param profun
     * @return
     */
    @RequestMapping(value = "/list/add", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxResult create(@RequestBody Profun profun) {
        boolean flag = true;
        try {

            List<Profun> profunList = new ArrayList<>();

            if(profun.getFunctionList().size()>0) {
                for (int i = 0; i < profun.getFunctionList().size(); i++) {
                    Profun profun1 = new Profun();
                    profun1.setOid(UUIDGenerator.getUUID());
                    profun1.setProjectCode(profun.getProjectCode());
                    profun1.setProjectName(profun.getProjectName());
                    profun1.setFunctionCode(profun.getFunctionList().get(i).getCode());
                    profun1.setFunctionName(profun.getFunctionList().get(i).getName());
                    profun1.setParentFuncCode(profun.getFunctionList().get(i).getParentCode());
                    profun1.setParentFunctionName(profun.getFunctionList().get(i).getParentName());
                    profunList.add(profun1);
                }
                profunSerivce.saveBatch(profunList);
            }else {
                flag = profunSerivce.save(profun);
            }


            if(flag) {
                return result(MessageConstants.SSO_STATUS_SUCCESS);
            }
            else{
                return result(MessageConstants.SSO_STATUS_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }

    }

    /**
     * 查询
     * @param profun
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult list(Profun profun) {
        AjaxResult ajaxResult = null;
        try {
            Page page = getPage();  // 分页
            String sort = getParameter("sort");
            boolean sortOrder = getOrderSort(getParameter("sortOrder"));
//            if(StringUtil.isNotEmpty(sort)){
//                page.setAsc(sortOrder);
//                page.setOrderByField(sort);
//            }else {
//                page.setAsc(false);
//                page.setOrderByField("createTime");
//            }

           // BsspUtil.filterCopEnt(sasDclBsc, null);
            Page<Profun> profunPage = profunSerivce.selectByList(page,profun);

            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, profunPage.getRecords(), (int) page.getTotal()); // 格式要返回的数据
            return ajaxResult;
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
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
            String code = getParameter("code");
            profunSerivce.getById(id);
            Profun profun = profunSerivce.getById(id);
            List<Profun> list = profunSerivce.selectByProjectCode(code);
            JSONObject json = new JSONObject();
            json.put("list",list);
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, json);
        } catch (Exception e) {
            ajaxResult = result(MessageConstants.SSO_STATUS_SUCCESS);
            log.error("editProfun()--err", e);
        }
        return ajaxResult;
    }

    /**
     * 批量删除
     * @return
     */
    @RequestMapping(value = "/list/delete", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult delete() {
        try{

            String idList = getParameter("idList");
            Boolean flag = profunSerivce.removeByIds(Arrays.asList(idList.split(",")));
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
     * 根据项目代码来查询
     * @return
     */
    @RequestMapping(value = "/list/selectByCode", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult selectByCode() {
        AjaxResult ajaxResult = null;
        try {

            String code = getParameter("codeList");
            List<String> codeList = Arrays.asList(code.split(","));
            List<Profun> profuns = new ArrayList<>();

            for(int i=0;i<codeList.size();i++) {
                profuns.add(profunSerivce.selectOneByProjectCode(codeList.get(i)));
            }

            return ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS,profuns);
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }

    }

    /**
     * 保存一个项目多个功能
     * @return
     */
    @RequestMapping(value = "/list/saveByCode", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult saveByCode() {
        try {

            String code = getParameter("codeList");
            List<String> codeList = Arrays.asList(code.split(","));

            for(int i=0;i<codeList.size();i++) {
                SsoProject project = projectSerivce.selectByCode(codeList.get(i));
                Profun profun = new Profun();
                Profun profun1 = profunSerivce.selectOneByProjectCode(project.getCode());
                if(profun1 == null){
                    profun.setOid(UUIDGenerator.getUUID());
                    profun.setProjectCode(project.getCode());
                    profun.setProjectName(project.getName());
                    profunSerivce.save(profun);
                }
            }

            return result(MessageConstants.SSO_STATUS_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return setErrorJson(e.toString());
        }

    }

    /**
     * 更新
     * @param profun
     * @return
     */
    @RequestMapping(value = "/list/update", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult updateProject(@RequestBody Profun profun) {
        AjaxResult ajaxResult = null;
        try {

            List<Profun> list = profunSerivce.selectByProjectCode(profun.getProjectCode());
            List<String> oidList = new ArrayList<>();
            List oidFunCodeList = new ArrayList<>();
            for(Profun profun1:list){
                oidList.add(profun1.getOid());
            }

            if(oidList.size()>0) {
                profunSerivce.removeByIds(oidList);
            }

            for(int i=0;i<profun.getFunctionList().size();i++){
                oidFunCodeList.add(profun.getFunctionList().get(i).getCode());
            }

            QueryWrapper<SsoRoleFunc> entityWrapper = new QueryWrapper<>();
            entityWrapper.eq("PROJECT_CODE",profun.getProjectCode());
            List<SsoRoleFunc> roleFunc = ssoRoleFuncService.list(entityWrapper);

            for(int i=0;i<roleFunc.size();i++){
                boolean fla = true;
                for(int j=0;j<oidFunCodeList.size();j++){
                    if(oidFunCodeList.get(j).equals(roleFunc.get(i).getFunctionCode())){
                        fla = false;
                        break;
                    }
                }

                if(fla) {
                    QueryWrapper<SsoRoleFunc> delectRoleFunc = new QueryWrapper<>();
                    delectRoleFunc.eq("OID", roleFunc.get(i).getOid());
                    ssoRoleFuncService.remove(delectRoleFunc);
                    roleFunc.remove(i);
                    i = i-1;
                }

            }

            create(profun);
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS,profun);

        } catch (Exception e) {
            ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
            log.error("updateProfun()--err", e);
        }
        return ajaxResult;
    }
}