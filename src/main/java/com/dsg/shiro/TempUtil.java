package com.dsg.shiro;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dsg.service.SysMenuService;
import com.dsg.service.SysUserService;
import com.dsg.util.Constant;

/**
 * @Configuration导致Shiro比Service实例化先执行
 */
@Component
public class TempUtil {

	public static SysMenuService sysMenuService;
	public static SysUserService sysUserService;
	public static Constant constant;

	@Resource
	public void setSysMenuService(SysMenuService sysMenuService) {
		TempUtil.sysMenuService = sysMenuService;
	}

	@Resource
	public void setSysUserService(SysUserService sysUserService) {
		TempUtil.sysUserService = sysUserService;
	}

	@Resource
	public void setConstant(Constant constant) {
		TempUtil.constant = constant;
	}

}
