package com.wstro.entity.setting;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 岗位（角色）信息表
 * </p>
 *
 * @author wenyiwei
 * @since 2017-10-30
 */
@Data
@TableName("sso_role")
public class SsoRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * GUID主键
     */
    @TableId("OID")
	private String oid;
    /**
     * 所属机构代码
     */
	@TableField("ORG_CODE")
	private String orgCode;
    /**
     * 角色名称（机构代码+角色名称，唯一）
     */
	@TableField("ROLE_NAME")
	private String roleName;
    /**
     * 角色类型（预留）
     */
	@TableField("ROLE_TYPE")
	private String roleType;
    /**
     * 描述
     */
	@TableField("DESCRIPTION")
	private String description;
    /**
     * 排序
     */
	@TableField("ORDER_INDEX")
	private BigDecimal orderIndex;
    /**
     * 是否有效（Y-是，N-否）
     */
	@TableField("IS_VALID")
	private String isValid;
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
	 * 非数据库字段，用于页面传值或展示
	 */
	@TableField(exist = false)
	private String opTimeStart;
	@TableField(exist = false)
	private String opTimeEnd;
	@TableField(exist = false)
	private String orgName;
	@TableField(exist = false)
	private String userName;
}
