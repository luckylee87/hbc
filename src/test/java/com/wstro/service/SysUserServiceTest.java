package com.wstro.service;

import java.util.List;

import javax.annotation.Resource;

import com.wstro.entity.SysUserEntity;

/**
 * 系统用户测试
 * 
 */
public class SysUserServiceTest {

	@Resource
	private SysUserService sysUserService;

	/**
	 * 查询列表
	 */
	// @Test
	public void selectList() {
		List<SysUserEntity> selectList = sysUserService.list(null);
		for (SysUserEntity sysUserEntity : selectList) {
			System.out.println(sysUserEntity);
		}
	}

}
