package com.wstro.controller.setting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wstro.common.constants.CommonConstants;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import com.wstro.common.security.SystemAuthorizingUser;
import com.wstro.common.util.SingletonLoginUtils;
import com.wstro.common.util.UUIDGenerator;
import com.wstro.common.util.toolbox.StringUtil;
import com.wstro.controller.base.BaseController;
import com.wstro.entity.setting.SsoRole;
import com.wstro.entity.setting.SsoRoleUser;
import com.wstro.service.setting.ISsoRoleService;
import com.wstro.service.setting.ISsoRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 岗位（角色）信息表 前端控制器
 * </p>
 *
 * @author wenyiwei
 * @since 2017-10-30
 */
@RestController
@RequestMapping("/setting/ssoRole")
public class SsoRoleController extends BaseController {

	@Autowired
	private ISsoRoleService ssoRoleService;
	@Autowired
	private ISsoRoleUserService ssoRoleUserService;
	@Autowired
	private RedisTemplate redisTemplate;
	// @Autowired
	// private ISsoRoleFuncService ssoRoleFuncService;

	/**
	 * 查询角色列表
	 * 
	 * @param ssoRole
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)

	public AjaxResult list(SsoRole ssoRole, int pageSize, int pageNumber) {
		AjaxResult rs = null;
		SystemAuthorizingUser sysUser = SingletonLoginUtils.getSystemUser();
		com.powerbridge.core.security.SystemAuthorizingUser systemUser = com.powerbridge.core.util.SingletonLoginUtils
				.getSystemUser(redisTemplate);
		try {
			// 如果systemUser用户不为空，则代表是userToken登录的用户
			if (systemUser != null && systemUser.getUserName() != null) {
				ssoRole.setOrgCode(systemUser.getOrgCode());
				ssoRole.setUserName(systemUser.getUserName());
			} else {
				if (CommonConstants.USER_TYPE_COM.equals(sysUser.getUserType())) {
					return null;
				} else if (CommonConstants.USER_TYPE_ORG.equals(sysUser.getUserType())) {
					ssoRole.setOrgCode(sysUser.getOrgCode());
				}
			}
			// 计算当前页码，实例化page
			Page page = new Page(pageNumber / pageSize + 1, pageSize);
			String sort = getParameter("sort");
			boolean sortOrder = getOrderSort(getParameter("sortOrder"));
//			if (StringUtil.isNotEmpty(sort)) {
//				page.setAsc(sortOrder);
//				page.setOrderByField(sort); // 排序
//			} else {
//				page.setOrderByField("ORDER_INDEX");
//			}
			ssoRoleService.selectByPage(page, ssoRole);
			rs = json(MessageConstants.SSO_STATUS_SUCCESS, page.getRecords(), (int) page.getTotal());
		} catch (Exception e) {
			e.printStackTrace();
			rs = setErrorJson(e.toString());
		}
		return rs;
	}

	/**
	 * 添加角色信息
	 * 
	 * @param ssoRole
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)

	public AjaxResult addEntity(SsoRole ssoRole) {
		AjaxResult rs = null;
		try {
			// 如果没有传orgCode参数，则是其他服务调用，从当前用户获取orgCode
			if (StringUtil.isEmpty(ssoRole.getOrgCode())) {
				com.powerbridge.core.security.SystemAuthorizingUser systemUser = com.powerbridge.core.util.SingletonLoginUtils
						.getSystemUser(redisTemplate);
				ssoRole.setOrgCode(systemUser.getOrgCode());
				ssoRole.setOpUser(systemUser.getUserName());
				ssoRole.setOpTime(new Date());
			}
			ssoRole.setOid(UUIDGenerator.getUUID());
			boolean flag = ssoRoleService.save(ssoRole);
			if (flag)
				rs = json(MessageConstants.SSO_STATUS_SUCCESS, ssoRole);
			else
				rs = result(MessageConstants.SSO_STATUS_FAIL);
		} catch (Exception e) {
			e.printStackTrace();
			rs = setErrorJson(e.toString());
		}
		return rs;
	}

	/**
	 * 查阅角色信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/view", method = RequestMethod.POST)

	public AjaxResult viewEntity(@PathVariable String id) {
		AjaxResult rs = null;
		try {
			SsoRole ssoRole = ssoRoleService.getById(id);
			rs = json(MessageConstants.SSO_STATUS_SUCCESS, ssoRole);
		} catch (Exception e) {
			e.printStackTrace();
			rs = setErrorJson(e.toString());
		}
		return rs;
	}

	/**
	 * 更新角色信息
	 * 
	 * @param ssoRole
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)

	public AjaxResult updateEntity(SsoRole ssoRole) {
		AjaxResult rs = null;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("ROLE_OID", ssoRole.getOid());
			// List<SsoRoleFunc> list = ssoRoleFuncService.listByMap(params);
			// if(null != list && list.size() > 0)
			// rs = setJson(MessageConstants.SSO_STATUS_FAIL.getCode(), "角色已经被授权，无法修改！");
			// else {
			boolean flag = ssoRoleService.updateById(ssoRole);
			if (flag)
				rs = json(MessageConstants.SSO_STATUS_SUCCESS, ssoRole);
			else
				rs = result(MessageConstants.SSO_STATUS_FAIL);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			rs = setErrorJson(e.toString());
		}
		return rs;
	}

	/**
	 * 删除角色
	 * 
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)

	public AjaxResult deleteEntity(String oid) {
		AjaxResult rs = null;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("ROLE_OID", oid);
			// 查询角色是否已经被用户关联
			List<SsoRoleUser> list = ssoRoleUserService.listByMap(params);
			if (null != list && list.size() > 0)
				rs = setJson(MessageConstants.SSO_STATUS_FAIL.getCode(), "角色已经被用户关联，无法删除！");
			else {
				ssoRoleService.deleteByOid(oid);
				rs = result(MessageConstants.SSO_STATUS_SUCCESS);
			}
			// ssoRoleService.removeByIds(Arrays.asList(idList.split(",")));
		} catch (Exception e) {
			e.printStackTrace();
			rs = setErrorJson(e.toString());
		}
		return rs;
	}

	/**
	 * 通过机构代码查询角色列表
	 * 
	 * @param orgCode
	 * @return
	 */
	@RequestMapping(value = "/getDropDownList", method = RequestMethod.GET)

	public AjaxResult getDropDownList(String orgCode) {
		AjaxResult rs = null;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("ORG_CODE", orgCode);
			List<SsoRole> list = ssoRoleService.listByMap(params);
			rs = json(MessageConstants.SSO_STATUS_SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();
			rs = setErrorJson(e.toString());
		}
		return rs;
	}

}
