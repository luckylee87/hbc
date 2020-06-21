package com.wstro.entity.setting;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：profun 实体类
 * 类描述： SystemUser 表实体类
 * 创建人：
 * 创建时间：2017年10月16日 下午16:19:48
 */

@Data
@TableName("sso_pro_func")
public class Profun implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<FunctionMode> functionList;

    /**
     * 用户ID
     */
    @TableId(value = "OID")
    private String oid;

    /**
     * 项目代码
     */
    @TableField(value = "PROJECT_CODE")
    private String projectCode;

    /**
     * 项目名称
     */
    @TableField(value = "PROJECT_NAME")
    private String projectName;

    /**
     * 功能代码
     */
    @TableField(value = "FUNCTION_CODE")
    private String functionCode;

    /**
     * 功能名称
     */
    @TableField(value = "FUNCTION_NAME")
    private String functionName;

    /**
     * 父功能代码
     */
    @TableField(value = "PARENT_FUNC_CODE")
    private String parentFuncCode;

    /**
     * 父功能名称
     */
    @TableField(value = "PARENT_FUNCTION_NAME")
    private String parentFunctionName;

    /**
     * 创建人
     */
    @TableField(value = "CREATE_NAME")
    private String createName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @TableField(exist = false)
    private String createTimeStart;

    @TableField(exist = false)
    private String createTimeEnd;
}
