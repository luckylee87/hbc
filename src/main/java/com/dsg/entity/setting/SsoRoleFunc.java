package com.dsg.entity.setting;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色-功能对应表
 * </p>
 *
 * @author wenyiwei
 * @since 2017-11-01
 */
@Data
@TableName("sso_role_func")
public class SsoRoleFunc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * GUID主键
     */
    @TableId("OID")
	private String oid;
    /**
     * 角色表OID
     */
	@TableField("ROLE_OID")
	private String roleOid;
    /**
     * 角色名称
     */
	@TableField("ROLE_NAME")
	private String roleName;
    /**
     * 项目代码
     */
	@TableField("PROJECT_CODE")
	private String projectCode;
    /**
     * 项目名称
     */
	@TableField("PROJECT_NAME")
	private String projectName;
    /**
     * 功能代码
     */
	@TableField("FUNCTION_CODE")
	private String functionCode;
    /**
     * 功能名称
     */
	@TableField("FUNCTION_NAME")
	private String functionName;
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
    /**
     * 角色所属机构代码
     */
	@TableField("ORG_CODE")
	private String orgCode;
}
