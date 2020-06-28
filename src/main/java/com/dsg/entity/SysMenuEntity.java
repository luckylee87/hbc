package com.dsg.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/***
 * 菜单管理
 * 
 */
@Data
@TableName("sys_menu")
public class SysMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@TableId(type = IdType.AUTO)
	private Long menuId;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	@TableField
	private Long parentId;

	/**
	 * 父菜单名称
	 */
	@TableField(exist = false)
	private String parentName;

	/**
	 * 菜单名称
	 */
	@TableField
	private String name;

	/**
	 * 菜单URL
	 */
	@TableField
	private String url;

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	@TableField
	private String perms;

	/**
	 * 类型 0：目录 1：菜单 2：按钮
	 */
	@TableField
	private Integer type;

	/**
	 * 菜单图标
	 */
	@TableField
	private String icon;

	/**
	 * 排序
	 */
	@TableField
	private Integer orderNum;

	/**
	 * ztree属性
	 */
	@TableField(exist = false)
	private Boolean open;

	@TableField(exist = false)
	private List<?> list;

}
