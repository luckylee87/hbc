package com.wstro.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemRoleMenu
 * 类描述：SystemRoleMenu 表实体类
 * 创建人：simon.xie
 * 创建时间：2016年11月13日 下午10:41:36
 * 修改人：simon.xie
 * 修改时间：2016年11月13日 下午10:41:36
 */
@TableName("system_role_menu")
public class SystemRoleMenu implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 角色权限编号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /**
     * 角色编号
     */
    @TableField(value = "ROLE_ID")
    private String roleId;

    /**
     * 权限编号
     */
    @TableField(value = "MENU_ID")
    private String menuId;

    /**
     * 权限标识
     */
    @TableField(exist = false)
    private String permission;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return this.menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}
