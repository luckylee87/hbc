package com.dsg.entity.setting;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import java.util.Date;

/**
 * @Description: sso_pw_config实体表
 * @author: powerbridge@powerbridge.com
 * @Date: 2019年07月09日 10:56:45
 */
@Data
@TableName("sso_pw_config")
public class SsoPwConfig implements Serializable {

	private static final long serialVersionUID = 1L;

    @NotNull
    @TableId("OID")
    @ApiModelProperty(value="主键OID", name="oid", example="")
	private String oid;
	
    @Max(value = 10, message = "参数值最大值为10")
    @TableField("PW_LENG")
    @ApiModelProperty(value = "密码至少长度", name="pwLeng", example="")
    private Integer pwLeng;
    
    @Max(value = 10, message = "参数值最大值为10")
    @TableField("VALID_DAY")
    @ApiModelProperty(value = "密码有效天数", name="validDay", example="")
    private Integer validDay;
    
    @Max(value = 1, message = "参数值最大值为1")
    @TableField("PW_CAPITAL_LETTER")
    @ApiModelProperty(value = "密码复杂程度-大写字母", name="pwCapitalLetter", example="")
    private String pwCapitalLetter;
    
    @Max(value = 1, message = "参数值最大值为1")
    @TableField("PW_LITTLE_LETTER")
    @ApiModelProperty(value = "密码复杂程度小写字母", name="pwLittleLetter", example="")
    private String pwLittleLetter;
    
    @Max(value = 1, message = "参数值最大值为1")
    @TableField("PW_NUMBER")
    @ApiModelProperty(value = "密码复杂程度-数字", name="pwNumber", example="")
    private String pwNumber;
    
    @Max(value = 1, message = "参数值最大值为1")
    @TableField("PW_SPECOA_CHARACTERS")
    @ApiModelProperty(value = "密码复杂程度-特殊字符", name="pwSpecoaCharacters", example="")
    private String pwSpecoaCharacters;
    
    @Max(value = 64, message = "参数值最大值为64")
    @TableField("CREATE_BY")
    @ApiModelProperty(value = "创建人代码", name="createBy", example="")
    private String createBy;
    
    @Max(value = 64, message = "参数值最大值为64")
    @TableField("CREATE_NAME")
    @ApiModelProperty(value = "创建人名称", name="createName", example="")
    private String createName;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("CREATE_TIME")
    @ApiModelProperty(value = "创建时间", name="createTime", example="")
    private Date createTime;
    
    @Max(value = 64, message = "参数值最大值为64")
    @TableField("UPDATE_BY")
    @ApiModelProperty(value = "修改人代码", name="updateBy", example="")
    private String updateBy;
    
    @Max(value = 64, message = "参数值最大值为64")
    @TableField("UPDATE_NAME")
    @ApiModelProperty(value = "修改人名称", name="updateName", example="")
    private String updateName;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("UPDATE_TIME")
    @ApiModelProperty(value = "修改时间", name="updateTime", example="")
    private Date updateTime;
    
    @Max(value = 20, message = "参数值最大值为20")
    @TableField("INPUT_COP_NO")
    @ApiModelProperty(value = "录入企业编码", name="inputCopNo", example="")
    private String inputCopNo;
    
    @Max(value = 70, message = "参数值最大值为70")
    @TableField("INPUT_COP_NAME")
    @ApiModelProperty(value = "录入企业名称", name="inputCopName", example="")
    private String inputCopName;
}