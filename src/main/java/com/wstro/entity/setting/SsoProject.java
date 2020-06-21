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

@Data
@TableName("sso_project")
public class SsoProject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * GUID主键
     */
    @TableId("OID")
    private String oid;
    /**
     * 项目代码（唯一）
     */
    @TableField("CODE")
    private String code;
    /**
     * 项目名称（唯一）
     */
    @TableField("NAME")
    private String name;

    /**
     * 项目描述
     */
    @TableField("DESCRIPTION")
    private String description;
    /**
     * 排序
     */
    @TableField("ORDER_INDEX")
    private BigDecimal orderIndex;
    /**
     * 是否显示 Y-是 N-否
     */
    @TableField("IS_SHOW")
    private String isShow;
    /**
     * 是否可以对其授权 Y-是 N-否
     */
    @TableField("IS_AUTHORIZED")
    private String isAuthorized;
    /**
     * 是否显示 名称
     */
    @TableField(exist = false)
    private String isShowName;
    /**
     * 是否显示 Y-是 N-否
     */
    @TableField(exist = false)
    private String isAuthorizedName;
    /**
     * 创建人
     */
    @TableField("CREATE_NAME")
    private String createName;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField(exist = false)
    private Date createTimeStart;

    @TableField(exist = false)
    private Date createTimeEnd;

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
     * 父级节点
     */
    @TableField(exist = false)
    private String parentCode;
}
