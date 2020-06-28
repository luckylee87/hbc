package com.dsg.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dsg.dao.SysUserLoginLogDao;
import com.dsg.entity.SysUserLoginLogEntity;
import com.dsg.service.SysUserLoginLogService;

/**
 * 用户登录日志
 * 
 */
@Service
public class SysUserLoginLogServiceImpl extends ServiceImpl<SysUserLoginLogDao, SysUserLoginLogEntity>
		implements SysUserLoginLogService {

	@Override
	public Page<SysUserLoginLogEntity> getSelf(Integer offset, Integer limit, Long adminId, String loginIp, String sort,
			Boolean order) {
		QueryWrapper<SysUserLoginLogEntity> wrapper = new QueryWrapper<SysUserLoginLogEntity>();
		wrapper.eq("user_id", adminId);
		if (StringUtils.isNoneBlank(sort) && null != order) {
			wrapper.orderBy(Boolean.parseBoolean(sort), order);
		}
		if (StringUtils.isNoneBlank(loginIp)) {
			wrapper.like("login_ip", loginIp);
		}
		Page<SysUserLoginLogEntity> page = new Page<>(offset, limit);
		return this.page(page, wrapper);
	}

}
