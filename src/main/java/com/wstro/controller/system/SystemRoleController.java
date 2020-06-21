package com.wstro.controller.system;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.common.security.SystemAuthorizingUser;
import com.wstro.common.util.ServletUtils;
import com.wstro.common.util.SingletonLoginUtils;
import com.wstro.common.util.toolbox.StringUtil;
import com.wstro.controller.base.BaseController;
import com.wstro.entity.system.SystemMenu;
import com.wstro.entity.system.SystemRole;
import com.wstro.entity.system.SystemRoleMenu;
import com.wstro.service.system.ISystemMenuService;
import com.wstro.service.system.ISystemRoleMenuService;
import com.wstro.service.system.ISystemRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemRoleController
 * 类描述：角色控制器
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 */
@RestController
@RequestMapping("/system/sysrole")
@CrossOrigin
@Slf4j
public class SystemRoleController extends BaseController {

    private static final Gson gson = new Gson();
    @Autowired
    private ISystemRoleService systemRoleService;
    @Autowired
    private ISystemRoleMenuService systemRoleMenuService;
    @Autowired
    private ISystemMenuService systemMenuService;

    @InitBinder("systemRole")
    public void initBinderSysBole(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("systemRole.");
    }

    /**
     * 进入角色管理页面
     *
     * @return ModelAndView
     */
    @RequestMapping("/list")

    public String showRoleList(Model model) {
        Map<String, Object> json = new HashMap<String, Object>();
        String jsonStr = "";
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            //查询所有的角色
            List<SystemRole> roleList = systemRoleService.selectRoleList();
            //model.addAttribute("roleList", roleList);
            List<SystemRole> systemRoles = systemRoleService.selectRoleAndNumber();
            //model.addAttribute("systemRoles", systemRoles);
            data.put("roleList", roleList);
            data.put("systemRoles", systemRoles);
            json = this.transJson("1", "成功", data);
            jsonStr = gson.toJson(json);
        } catch (Exception e) {
            log.error("showRoleList()--error", e);
            json = this.transJson("0", "失败", null);
            jsonStr = gson.toJson(json);
        }
        return jsonStr;
    }

    /**
     * @param model
     * @return
     * @throws
     * @Description: 添加角色
     */
    @RequestMapping(value = "/list/add", method = RequestMethod.GET)

    public String add(Model model) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<SystemMenu> menuList = systemMenuService.list(null);//所有菜单
        if (menuList != null) {
            //model.addAttribute("jsonMenu", JSON.toJSON(menuList));
            data.put("jsonMenu", menuList);
            log.info(JSON.toJSON(menuList).toString());
        }
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * @param model
     * @param roleId
     * @return
     * @throws
     * @Description: 编辑角色
     */
    @RequestMapping(value = "/list/{roleId}/edit", method = RequestMethod.GET)

    public String edit(Model model, @PathVariable String roleId) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        SystemRole systemRole = systemRoleService.getById(roleId);
        //model.addAttribute("systemRole", systemRole);//角色信息
        data.put("systemRole", systemRole);//角色信息
        List<SystemMenu> menuList = systemMenuService.list(null);//所有菜单
        if (menuList != null) {
            //根据角色，选出已经勾选的菜单
            Map<String, Object> columnMap = new HashMap<String, Object>();
            columnMap.put("ROLE_ID", roleId);
            List<SystemRoleMenu> roleMenuList = systemRoleMenuService.listByMap(columnMap);//角色拥有的菜单
            for (SystemMenu systemMenu : menuList) {
                for (SystemRoleMenu systemRoleMenu : roleMenuList) {
                    if (systemRoleMenu.getMenuId().equals(systemMenu.getId())) {
                        systemMenu.setChecked(true);
                    }
                }
            }
            //model.addAttribute("jsonMenu", JSON.toJSON(menuList));
            data.put("jsonMenu", menuList);
            log.info(JSON.toJSON(menuList).toString());
        }
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * @param systemRole
     * @return
     * @throws
     * @Description: 创建或修改角色
     */
    @RequestMapping(value = "/list/save", method = RequestMethod.POST)

    public Map<String, Object> save(SystemRole systemRole) {
        SystemAuthorizingUser sysUser = SingletonLoginUtils.getSystemUser();
        MessageConstants messageConstants = MessageConstants.SSO_STATUS_FAIL;
        try {
            if (StringUtil.isEmpty(systemRole.getId())) {
                systemRoleService.save(systemRole);//插入菜单
                //得到菜单ID串
                String menuIds = ServletUtils.getRequest().getParameter("menuIds");
                if (menuIds != null && menuIds.trim().length() > 0) {
                    List<SystemRoleMenu> systemRoleMenuList = new ArrayList<SystemRoleMenu>();
                    String[] funArr = menuIds.split(",");
                    if (funArr.length > 0) {
                        for (String menuId : funArr) {
                            SystemRoleMenu roleMenu = new SystemRoleMenu();
                            roleMenu.setMenuId(menuId);
                            roleMenu.setRoleId(systemRole.getId());
                            systemRoleMenuList.add(roleMenu);
                        }
                        systemRoleMenuService.saveBatch(systemRoleMenuList);
                    }
                }
                messageConstants = MessageConstants.SSO_STATUS_ROLE_CREATE_SUCCESS;
            } else {
                systemRoleService.updateById(systemRole);//更新角色
                //删除原有的角色菜单关联记录
                SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
                systemRoleMenu.setRoleId(systemRole.getId());
                //systemRoleMenuService.deleteSelective(systemRoleMenu);
                //systemRoleMenuService.removeById(systemRoleMenu);
                Map<String, Object> columnMap = new HashMap<String, Object>();
                columnMap.put("ROLE_ID", systemRoleMenu.getRoleId());
                systemRoleMenuService.removeByMap(columnMap);
                //得到菜单ID串
                String menuIds = ServletUtils.getRequest().getParameter("menuIds");
                if (menuIds != null && menuIds.trim().length() > 0) {
                    List<SystemRoleMenu> systemRoleMenuList = new ArrayList<SystemRoleMenu>();
                    String[] funArr = menuIds.split(",");
                    if (funArr.length > 0) {
                        for (String menuId : funArr) {
                            SystemRoleMenu roleMenu = new SystemRoleMenu();
                            roleMenu.setMenuId(menuId);
                            roleMenu.setRoleId(systemRole.getId());
                            systemRoleMenuList.add(roleMenu);
                        }
                        systemRoleMenuService.saveBatch(systemRoleMenuList);
                    }
                }
                messageConstants = MessageConstants.SSO_STATUS_ROLE_UPDATE_SUCCESS;
            }
        } catch (Exception e) {
            log.error("save()--error", e);
        }
        return this.setJson(messageConstants, null);
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return Map<String,Object>
     */
    @RequestMapping(value = "/list/{roleId}/delete", method = RequestMethod.POST)

    public AjaxResult deleteRole(@PathVariable("roleId") String roleId) {
        AjaxResult ajaxResult;
        try {
            if (StringUtils.isNotEmpty(roleId)) {
                systemRoleService.removeById(roleId);
                SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
                systemRoleMenu.setRoleId(roleId);
                Map<String, Object> columnMap = new HashMap<String, Object>();
                columnMap.put("ROLE_ID", systemRoleMenu.getRoleId());
                systemRoleMenuService.removeByMap(columnMap);
                ajaxResult = new AjaxResult(MessageConstants.SSO_STATUS_ROLE_DELETE_SUCCESS, null);
            } else {
                ajaxResult = new AjaxResult(MessageConstants.SSO_STATUS_ROLE_DELETE_CHOOSE, null);
            }
        } catch (Exception e) {
            ajaxResult = new AjaxResult(MessageConstants.SSO_STATUS_UNKOWN);
            log.error("deleteRole()--error");
        }
        return ajaxResult;
    }

    /**
     * POST 启用/禁止
     *
     * @return
     */
    @RequestMapping(value = "/list/audit", method = RequestMethod.POST)

    public AjaxResult audit(String roleId, Integer status) {
        SystemRole systemRole = new SystemRole();
        systemRole.setId(roleId);
        systemRole.setStatus(status);
        systemRoleService.updateById(systemRole);
        return result(MessageConstants.SSO_STATUS_SUCCESS);
    }

}

