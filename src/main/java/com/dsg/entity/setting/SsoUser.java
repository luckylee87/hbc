package com.dsg.entity.setting;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
 * 用户信息表
 * </p>
 *
 * @author weizheng
 * @since 2017-10-30
 */
@Data
@TableName("sso_user")
public class SsoUser implements Serializable{

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
     * 所属机构名称
     */
    @TableField(exist = false)
    private String orgName;
    /**
     * 用户名（不允许中文）
     */
    @TableField("USER_NAME")
    private String userName;
    /**
     * 密码（加密存储）
     */
    @TableField("USER_PWD")
    private String userPwd;
    /**
     * 用户类型（SYS-系统管理员，ORG-机构管理员，COM-普通用户，DEMO-演示用户）
     */
    @TableField("USER_TYPE")
    private String userType;

    /**
     * BLS用户类型（0-平台用户,1-企业用户，2-园区用户）
     */
    @TableField("BLS_USER_TYPE")
    private String blsUserType;
    /**
     * 真实姓名
     */
    @TableField("TRUE_NAME")
    private String trueName;
    /**
     * 排序
     */
    @TableField("ORDER_INDEX")
    private BigDecimal orderIndex;
    /**
     * IC卡号
     */
    @TableField("IC_CODE")
    private String icCode;
    /**
     * 证件类型
     */
    @TableField("CREDENTIALS_TYPE")
    private String credentialsType;
    /**
     * 证件号
     */
    @TableField("CREDENTIALS_NUM")
    private String credentialsNum;
    /**
     * 电话号码
     */
    @TableField("TEL_NUM")
    private String telNum;
    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;
    /**
     * 住址
     */
    @TableField("ADDRESS")
    private String address;
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
     * 微信用户ID
     */
    @TableField("OPEN_ID")
    private String openID;
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
     * 账号有效开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "START_VALID_DATE",whereStrategy = FieldStrategy.IGNORED)
    private Date startValidDate;

    /**
     * 账号有效结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "END_VALID_DATE",whereStrategy = FieldStrategy.IGNORED)
    private Date endValidDate;

    //用户机构数据过滤
    @TableField(exist = false)
    private String orgCodes;

    //用户机构数据过滤
    @TableField(exist = false)
    private SsoOrgCompany ssoOrgCompany;

    /**
     * 密码更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("UPDPWD_TIME")
    private Date updpwdTime;
}
