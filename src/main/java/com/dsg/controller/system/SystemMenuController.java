package com.dsg.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.dsg.common.constants.MessageConstants;
import com.dsg.common.dto.AjaxResult;
import com.dsg.common.security.SystemAuthorizingUser;
import com.dsg.common.util.SingletonLoginUtils;
import com.dsg.common.util.toolbox.StringUtil;
import com.dsg.controller.base.BaseController;
import com.dsg.entity.system.SystemMenu;
import com.dsg.service.system.ISystemMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemRoleController
 * 类描述：菜单控制器
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 */
@RestController
@RequestMapping("/system/sysmenu")
@CrossOrigin
@Slf4j
public class SystemMenuController extends BaseController {

    /**
     * 系统菜单列表
     */
    private static final Gson gson = new Gson();
    @Autowired
    private ISystemMenuService systemMenuService;

    @InitBinder({"systemMenu"})
    public void initBinderSystemMenu(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("systemMenu.");
    }

    /**
     * 进入菜单管理页面
     */
    @RequestMapping("/list")

    public String showMenuList() {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            //查询所有的菜单
            QueryWrapper entityWrapper = new QueryWrapper<>();
            entityWrapper.orderBy(true, true,"SORT");
            List<SystemMenu> menuList = systemMenuService.list(entityWrapper);
            //model.addAttribute("menuList", menuList);
            data.put("menuList", menuList);
        } catch (Exception e) {
            log.error("showMenuList()--error", e);
        }
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * @param model
     * @return
     * @throws
     * @Description: 添加菜单
     */
    @RequestMapping(value = "/list/add", method = RequestMethod.GET)

    public String add(Model model) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<SystemMenu> menuList = systemMenuService.selectSystemMenu();//查询所有的菜单
        //model.addAttribute("menuList", menuList);//所有菜单
        data.put("menuList", menuList);//所有菜单
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * @return
     * @throws
     * @Description: 编辑菜单
     */
    @RequestMapping(value = "/list/edit", method = RequestMethod.POST)

    public String edit(String id) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        SystemMenu systemMenu = systemMenuService.getById(id);
        List<SystemMenu> menuList = systemMenuService.selectSystemMenu();//查询所有的菜单
        data.put("systemMenu", systemMenu);//菜单信息
        data.put("menuList", menuList);//所有菜单
        json = this.transJson("1", "成功", data);
        String jsonStr = gson.toJson(json);
        return jsonStr;
    }

    /**
     * 创建或修改菜单
     *
     * @param systemMenu
     * @return
     */
    @RequestMapping(value = "/list/save", method = RequestMethod.POST)

    public Map<String, Object> update(SystemMenu systemMenu) {
        try {
            SystemAuthorizingUser sysUser = SingletonLoginUtils.getSystemUser();
            if (StringUtil.isEmpty(systemMenu.getId())) {
                Map<String, Object> columnMap = new HashMap<String, Object>();
                columnMap.put("MENU_NAME", systemMenu.getMenuName());
                List<SystemMenu> list = systemMenuService.listByMap(columnMap);
                if (list.size() > 0) {
                    return this.setJson(MessageConstants.SSO_STATUS_MENU_REDUPLICATED, null);
                }
                systemMenu.setCreateBy(sysUser.getUserName());
                systemMenuService.save(systemMenu);//插入菜单
                return this.setJson(MessageConstants.SSO_STATUS_MENU_CREATE_SUCCESS, null);
            } else {
                systemMenu.setUpdateBy(sysUser.getUserName());
                systemMenuService.updateById(systemMenu);//更新菜单
                return this.setJson(MessageConstants.SSO_STATUS_MENU_UPDATE_SUCCESS, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.setJson(MessageConstants.SSO_STATUS_FAIL, e.getMessage());
        }
    }

    /**
     * 删除菜单功能
     */
    @RequestMapping(value = "/list/{id}/delete", method = RequestMethod.POST)

    public AjaxResult deleteFunction(@PathVariable("id") String id) {
        try {
            if (systemMenuService.removeById(id)) {
                return new AjaxResult(MessageConstants.SSO_STATUS_SUCCESS, null);
            } else {
                return new AjaxResult(MessageConstants.SSO_STATUS_FAIL, null);
            }
        } catch (Exception e) {
            log.error("deleteFunction()--error", e);
            return setErrorJson(e.getMessage());
        }
    }

    /**
     * POST 启用/禁止
     *
     * @return
     */
    @RequestMapping(value = "/list/audit", method = RequestMethod.POST)

    public Map<String, Object> audit(String menuId, Integer status) {
        SystemMenu systemMenu = new SystemMenu();
        systemMenu.setId(menuId);
        systemMenu.setStatus(status);
        //systemMenuService.updateSelectiveById(systemMenu);
        systemMenuService.updateById(systemMenu);
        return this.setJson(MessageConstants.SSO_STATUS_SUCCESS, null);
    }
}
