package com.wstro.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/***
 * 用户登录日志
 * 
 */
@Data
@TableName("sys_user_login_log")
public class SysUserLoginLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 登录日志ID
	 */
	@TableId(type = IdType.AUTO)
	private Long logId;

	/**
	 * 登录时间
	 */
	@TableField
	private Long loginTime;

	/**
	 * 登录IP
	 */
	@TableField
	private String loginIp;

	/**
	 * 用户ID
	 */
	@TableField
	private Long userId;

	/**
	 * 操作系统
	 */
	@TableField
	private String operatingSystem;

	/**
	 * 浏览器
	 */
	@TableField
	private String browser;

}
