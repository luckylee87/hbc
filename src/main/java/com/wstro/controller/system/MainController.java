package com.wstro.controller.system;

import com.powerbridge.sso.common.MessageEnum;
import com.wstro.common.constants.CommonConstants;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.common.security.SystemAuthorizingUser;
import com.wstro.common.util.SingletonLoginUtils;
import com.wstro.controller.base.BaseController;
import com.powerbridge.sso.dto.JsonResultDto;
import com.powerbridge.sso.dto.ProjectDto;
import com.wstro.service.system.ISystemMenuService;
import com.wstro.service.system.ISystemUserRoleService;
import com.wstro.service.system.ISystemUserService;
import com.powerbridge.sso.utils.SsoSystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：MainController
 * 类描述：后台主页面表示层
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 */
@Controller
@RequestMapping("/system")
@CrossOrigin
@Slf4j
public class MainController extends BaseController {

    /**
     * 后台管理主界面
     */
    private static final String MAIN = getViewPath("admin/main/main");
    /**
     * 后台管理主界面初始化首页
     */
    private static final String MAIN_INDEX = getViewPath("admin/main/index");

    @Autowired
    private ISystemMenuService systemMenuService;
    @Autowired
    private ISystemUserRoleService systemUserRoleService;
    @Autowired
    private ISystemUserService systemUserService;

    /**
     * 进入操作中心
     *
     * @return
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult main(Model model) {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
//        List<SystemMenu> systemMenus = new ArrayList<SystemMenu>();
//        SystemAuthorizingUser systemUser = SingletonLoginUtils.getSystemUser();
//        //系统管理员具有所有菜单
//        if (systemUser.getLoginName().equals("admin")) {
//            systemMenus = systemMenuService.selectSystemMenu();
//        } else {
//            List<SystemUserRole> userRoleList = systemUserRoleService.selectRoleListByAccountId(systemUser.getUserId());//获取用户角色列表
//            if (userRoleList != null && userRoleList.size() > 0) {
//                List<String> roleIdList = new ArrayList<String>();
//                for (SystemUserRole systemUserRole : userRoleList) {
//                    roleIdList.add(systemUserRole.getRoleId());
//                }
//                systemMenus = systemMenuService.selectSystemMenuByRole(roleIdList);//获取用户拥有的菜单
//            }
//        }
//
//        SystemUser user = systemUserService.getById(systemUser.getUserId());

        String token = (String)getRequest().getSession().getAttribute(CommonConstants.SESSION_TOKEN);
        log.info("token: " + token);
        SystemAuthorizingUser user = SingletonLoginUtils.getSystemUser();
        data.put("systemUser", user);
        String projectCode = getParameter("projectCode");
        try {
            if (user != null){
                JsonResultDto dto = SsoSystemUtils.getMenus(token, user.getUserId(), projectCode);
                if (dto.getCode() != MessageEnum.API_STATUS_SUCCESS.getCode())
                    return new AjaxResult(dto.getCode(), dto.getMsg());
                List<ProjectDto> projectDto = (List<ProjectDto>) dto.getData();
                if(null == projectDto || projectDto.size() > 1)
                    return result(MessageConstants.SSO_STATUS_FAIL);
                data.put("systemMenus", projectDto.get(0).getData());
                return json(MessageConstants.SSO_STATUS_SUCCESS, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result(MessageConstants.SSO_STATUS_FAIL);
        }
        return result(MessageConstants.SSO_STATUS_FAIL);
    }

    /**
     * 后台管理主界面初始化首页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/main/index", method = RequestMethod.GET)
    public String mainIndex(HttpServletRequest request) {
        return MAIN_INDEX;
    }

}