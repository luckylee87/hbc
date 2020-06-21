package com.wstro.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：SystemUserRole
 * 类描述：SystemUserRole 表实体类
 * 创建人：simon.xie
 * 创建时间：2016年11月13日 下午9:48:11
 * 修改人：simon.xie
 * 修改时间：2016年11月13日 下午9:48:11
 */
@TableName("system_user_role")
public class SystemUserRole implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户角色编号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /**
     * 角色编号
     */
    @TableField(value = "ROLE_ID")
    private String roleId;

    /**
     * 用户编号
     */
    @TableField(value = "USER_ID")
    private String userId;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    /**
     * 创建者
     */
    @TableField(value = "CREATE_BY")
    private String createBy;

    /**
     * 角色名称
     */
    @TableField(value = "ROLE_NAME")
    private String roleName;

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


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
