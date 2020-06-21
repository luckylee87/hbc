package com.wstro.controller.setting;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.powerbridge.sso.utils.PullDownUtils;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.common.util.UUIDGenerator;
import com.wstro.common.util.toolbox.StringUtil;
import com.wstro.controller.base.BaseController;
import com.wstro.dao.setting.SsoRoleFuncMapper;
import com.wstro.entity.setting.Profun;
import com.wstro.entity.setting.SsoFunction;
import com.wstro.entity.setting.SsoProject;
import com.wstro.service.setting.IProfunSerivce;
import com.wstro.service.setting.IProjectSerivce;
import com.wstro.service.setting.ISsoFunctionService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：ProjectController
 * 类描述：项目维护层
 * 创建人：wiezheng
 * 创建时间：2017年11月10日 下午10:12:17
 */
@Api(description = "ProjectController", tags = "项目维护控制层")
@Controller
@RequestMapping("/project")
@CrossOrigin
@Slf4j
public class ProjectController extends BaseController {
    @Autowired
    private IProjectSerivce projectSerivce;
    @Autowired
    private ISsoFunctionService ssoFunctionService;
    @Autowired
    private IProfunSerivce profunSerivce;
    @Autowired
    private SsoRoleFuncMapper ssoRoleFuncMapper;

//    @ResponseBody
//    @RequestMapping(value = "/list/add", method = {RequestMethod.POST, RequestMethod.GET})
//    public AjaxResult create(SsoProject project) {
//        try {
//            QueryWrapper<SsoProject> entityWrapper = new QueryWrapper<>();
//            entityWrapper.eq("CODE", project.getCode());
//            int codes = projectSerivce.count(entityWrapper);
//            if (codes > 0) {
//                return setErrorJson("项目代码已存在！");
//            }
//            QueryWrapper<SsoProject> entityWrapper1 = new QueryWrapper<>();
//            entityWrapper1.eq("NAME", project.getName());
//            int names = projectSerivce.count(entityWrapper1);
//            if (names > 0) {
//                return setErrorJson("项目名称已存在！");
//            }
//            project.setOid(UUIDGenerator.getUUID());
//            boolean falg = projectSerivce.save(project);
//            if (falg) {
//                //新增数据成功时更新redis
//                reSetProjectDropDown();
//                return json(MessageConstants.SSO_STATUS_SUCCESS, project);
//            } else {
//                return result(MessageConstants.SSO_STATUS_FAIL);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return setErrorJson(e.toString());
//        }
//
//    }

    //重载项目下拉缓存redis
//    public void reSetProjectDropDown() {
//        List projectList = projectSerivce.selectRedisDropDown();
//        PullDownUtils.pullDownMap.put("project", projectList);
//    }

//    @RequestMapping(value = "/list/update", method = {RequestMethod.POST, RequestMethod.GET})
//    @ResponseBody
//    public AjaxResult updateProject(SsoProject ssoProject) {
//        AjaxResult ajaxResult = null;
//        try {
//            Boolean flag = projectSerivce.updateById(ssoProject);
//            if (flag) {
//                //更新数据成功时更新redis
//                reSetProjectDropDown();
//                ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoProject);
//            } else {
//                ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
//            }
//        } catch (Exception e) {
//            ajaxResult = result(MessageConstants.SSO_STATUS_FAIL);
//            log.error("updateProject()--err", e);
//        }
//        return ajaxResult;
//    }

    /**
     * 查询单条数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/list/{id}/view", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult editCopEntInfo(@PathVariable String id) {
        AjaxResult ajaxResult = null;
        try {
            SsoProject project = projectSerivce.getById(id);
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, project);
        } catch (Exception e) {
            ajaxResult = result(MessageConstants.SSO_STATUS_SUCCESS);
            log.error("editProject()--err", e);
        }
        return ajaxResult;
    }

    /**
     * 查询
     *
     * @param ssoProject
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult list(SsoProject ssoProject) {
        AjaxResult ajaxResult = null;
        try {
            Page page = getPage();  // 分页
            String sort = getParameter("sort");
            boolean sortOrder = getOrderSort(getParameter("sortOrder"));
//            if (StringUtil.isNotEmpty(sort)) {
//                page.setAsc(sortOrder);
//                page.setOrderByField(sort);  // 排序
//            } else {
//                page.setAsc(false);
//                page.setOrderByField("createTime");
//            }
            // BsspUtil.filterCopEnt(sasDclBsc, null);
            Page<SsoProject> profunPage = projectSerivce.selectByList(page, ssoProject);
            List<SsoProject> list = profunPage.getRecords();
            for (int i = 0; i < list.size(); i++) {
                if ("Y".equals(list.get(i).getIsShow())) {
                    list.get(i).setIsShow("是");
                } else if ("N".equals(list.get(i).getIsShow())) {
                    list.get(i).setIsShow("否");
                }

                if ("Y".equals(list.get(i).getIsAuthorized())) {
                    list.get(i).setIsAuthorized("是");
                } else if ("N".equals(list.get(i).getIsAuthorized())) {
                    list.get(i).setIsAuthorized("否");
                }

            }

            profunPage.setRecords(list);
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, profunPage.getRecords(), (int) page.getTotal()); // 格式要返回的数据
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
            return setErrorJson(e.toString());
        }

    }

    /**
     * 批量删除
     *
     * @return
     */
    @RequestMapping(value = "/list/deleteByList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult delete() {
        try {
            String idList = getParameter("idList");
            deleteFuncByPro(idList);
            Boolean flag = projectSerivce.removeByIds(Arrays.asList(idList.split(",")));
            if (flag) {
                return result(MessageConstants.SSO_STATUS_SUCCESS);
            } else {
                return result(MessageConstants.SSO_STATUS_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return setErrorJson(e.getMessage());
        }
    }

    /**
     * 获取项目树信息
     *
     * @return
     */
    @RequestMapping(value = "/list/getTree", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object getTree() {
        Object ajaxResult = null;
        try {
            //根据名称过滤
            String searchProName = getParameter("searchProName");
            QueryWrapper entityWrapper = new QueryWrapper();
            if (StringUtil.isNotEmpty(searchProName)) {
                entityWrapper.like("NAME", searchProName);
            }
            entityWrapper.orderByAsc("ORDER_INDEX");
            List<SsoProject> ssoProjects = projectSerivce.list(entityWrapper);
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoProjects, ssoProjects.size()).getData();
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    /**
     * 获取授权项目树信息
     *
     * @return
     */
    @RequestMapping(value = "/list/getAuthTree", method = {RequestMethod.POST})
    @ResponseBody
    public Object getAuthTree() {
        Object ajaxResult = null;
        try {
            //根据名称过滤
            String searchProName = getParameter("searchProName");
            QueryWrapper entityWrapper = new QueryWrapper();
            if (StringUtil.isNotEmpty(searchProName)) {
                entityWrapper.like("NAME", searchProName);
            }
            String userName = getParameter("userName");
            List<String> projectCodeList = ssoRoleFuncMapper.selectProjectCodeByUserName(userName);
            entityWrapper.in("CODE", projectCodeList);
            entityWrapper.orderByAsc("ORDER_INDEX");
            List<SsoProject> ssoProjects = projectSerivce.list(entityWrapper);
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoProjects, ssoProjects.size()).getData();
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    /**
     * 获取功能树信息
     *
     * @return
     */
    @RequestMapping(value = "/list/getFuncTree", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object getFuncTree(String projectCode) {
        Object ajaxResult = null;
        try {
            QueryWrapper entityWrapper = new QueryWrapper();
            entityWrapper.like("GROUP_BY", "|" + projectCode + "|");
            entityWrapper.orderByAsc("ORDER_INDEX");
            List<SsoFunction> ssoFunctions = ssoFunctionService.list(entityWrapper);
            if (ssoFunctions.size() > 0) {
                QueryWrapper<Profun> entityWrapper1 = new QueryWrapper<Profun>();
                entityWrapper1.eq("PROJECT_CODE", projectCode);
                List<Profun> profuns = profunSerivce.list(entityWrapper1);
                if (profuns.size() > 0) {
                    for (int i = 0; i < profuns.size(); i++) {
                        for (int j = 0; j < ssoFunctions.size(); j++) {
                            if ("Y".equals(ssoFunctions.get(j).getIsShow()) && "N".equals(ssoFunctions.get(j).getIsAuthorized())) {
                                ssoFunctions.get(j).setNocheck(true);
                            }
                            if ("N".equals(ssoFunctions.get(j).getIsShow())) {
                                ssoFunctions.remove(j);
                            }

                            if (profuns.get(i).getFunctionCode().equals(ssoFunctions.get(j).getCode())) {
                                ssoFunctions.get(j).setNocheck(true);
                                break;
                            }

                        }
                    }
                } else {//新增项目时的功能树
                    for (int j = 0; j < ssoFunctions.size(); j++) {
                        if ("Y".equals(ssoFunctions.get(j).getIsShow()) && "N".equals(ssoFunctions.get(j).getIsAuthorized())) {
                            ssoFunctions.get(j).setNocheck(true);
                        }
                        if ("N".equals(ssoFunctions.get(j).getIsShow())) {
                            ssoFunctions.remove(j);
                        }
                    }
                }
            }
            ajaxResult = json(MessageConstants.SSO_STATUS_SUCCESS, ssoFunctions, ssoFunctions.size()).getData();
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getFuncTree--", e);
            return e.toString();
        }
    }

    private void deleteFuncByPro(String projectIds) {
        List<SsoProject> ssoProjects = projectSerivce.listByIds(Arrays.asList(projectIds.split(",")));
        StringBuilder sb = new StringBuilder();
        try {
            for (SsoProject ssoProject : ssoProjects) {
                sb.append(ssoProject.getCode());
            }
            QueryWrapper<Profun> entityWrapper = new QueryWrapper<>();
            entityWrapper.in("PROJECT_CODE", sb.toString());
            profunSerivce.remove(entityWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    /**
     * 通过SSO用户名获取用户信息
     *
     * @param userName
     * @return
     */
    @ApiOperation(value = "通过用户名获取拥有权限的项目code")
    @ApiResponses({
            @ApiResponse(response = AjaxResult.class, code = 0, message = "失败", responseContainer = "AjaxResult"),
            @ApiResponse(response = AjaxResult.class, code = 1, message = "成功!", responseContainer = "AjaxResult")
    })
    @RequestMapping(value = "/getProjectCodeByName", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getProjectCodeByName(@ApiParam(value = "SSO用户名", name = "userName", required = true) @RequestParam(value = "userName") String userName) {
        try {
            List<String> codeList = projectSerivce.selectCodeByName(userName);
            codeList.add("SAAS");
            log.info("getProjectCodeByName返回：" + codeList.toString());
            return json(MessageConstants.SSO_STATUS_SUCCESS, codeList);
        } catch (Exception e) {
            log.error("getProjectCodeByName()--error ", e);
            return setErrorJson(e.toString());
        }
    }
}