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
 * 组织机构表
 * </p>
 *
 * @author zcb
 * @since 2017-10-30
 */
@Data
@TableName("sso_organization")
public class SsoOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * GUID主键
     */
    @TableId("OID")
	private String oid;
    /**
     * 机构代码（唯一）
     */
	@TableField("CODE")
	private String code;
    /**
     * 机构名称
     */
	@TableField("NAME")
	private String name;
    /**
     * 父节点机构代码
     */
	@TableField("PARENT_CODE")
	private String parentCode;
    /**
     * 排序
     */
	@TableField("ORDER_INDEX")
	private BigDecimal orderIndex;
    /**
     * 描述
     */
	@TableField("DESCRIPTION")
	private String description;
    /**
     * 操作人
     */
	@TableField("OP_USER")
	private String opUser;
    /**
     * 操作时间
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField("OP_TIME")
	private Date opTime;

	/**
	 *  查询用户名
	 */
	@TableField(exist = false)
	private String userName;
	/**
	 *  查询真实姓名
	 */
	@TableField(exist = false)
	private String trueName;
}
