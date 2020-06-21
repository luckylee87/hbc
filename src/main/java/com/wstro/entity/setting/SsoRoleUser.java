package com.wstro.entity.setting;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sso_role_user")
public class SsoRoleUser implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * GUID主键
     */
    @TableId("OID")
    private String oid;
    /**
     * 角色OID
     */
    @TableField("ROLE_OID")
    private String roleOid;
    /**
     * 用户名
     */
    @TableField("USER_NAME")
    private String userName;
    /**
     * 操作人
     */
    @TableField("OP_USER")
    private String opUser;
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    @TableField("OP_TIME")
    private Date opTime;
}
