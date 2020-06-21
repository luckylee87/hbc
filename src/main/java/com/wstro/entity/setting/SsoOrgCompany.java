package com.wstro.entity.setting;

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
 * 组织机构-公司信息表
 * </p>
 *
 * @author zcb
 * @since 2017-10-30
 */
@Data
@TableName("sso_org_company")
public class SsoOrgCompany implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * GUID主键
     */
    @TableId("OID")
	private String oid;
    /**
     * 机构代码
     */
	@TableField("ORG_CODE")
	private String orgCode;
    /**
     * 企业海关编码
     */
	@TableField("CUS_CODE")
	private String cusCode;
    /**
     * 企业国检编码
     */
	@TableField("CIQ_CODE")
	private String ciqCode;
    /**
     * 企业信用代码
     */
	@TableField("CREDIT_CODE")
	private String creditCode;
    /**
     * 企业名称
     */
	@TableField("NAME")
	private String companyName;
    /**
     * 企业简称
     */
	@TableField("SHORT_NAME")
	private String shortName;
    /**
     * 企业地址
     */
	@TableField("ADDRESS")
	private String address;
    /**
     * 联系人
     */
	@TableField("LINK_MAN")
	private String linkMan;
    /**
     * 联系电话
     */
	@TableField("LINK_TEL")
	private String linkTel;
    /**
     * 联系邮箱
     */
	@TableField("LINK_EMAIL")
	private String linkEmail;
    /**
     * 主管海关
     */
	@TableField("MASTER_CUSCD")
	private String masterCuscd;
    /**
     * 场所代码
     */
	@TableField("AREA_CODE")
	private String areaCode;
    /**
     * 检验检疫
     */
	@TableField("MASTER_CIQCD")
	private String masterCiqcd;
    /**
     * 是否有效（Y-是，N-否）
     */
	@TableField("IS_VALID")
	private String isValid;
    /**
     * 备注
     */
	@TableField("NOTES")
	private String notes;
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
