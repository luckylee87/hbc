package com.dsg.entity.cod_std;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("cod_std_codes")
public class CodStdCodes implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "OID")
    private String oid;//唯一主键

    @TableField(value = "ATTR_VALUE")
    private String attrValue;//属性值

    @TableField(value = "ATTR_NAME")
    private String attrName;//属性名称

    @TableField(value = "DICTIONARY_VALUE")
    private String dictionaryValue;//字典值

    @TableField(value = "DICTIONARY_DESCRIPT")
    private String dictionaryDescript;//字典描述

    @TableField(value = "ORDER_NO")
    private int orderNo;//排序号

    @TableField(value = "IS_ENABLE")
    private String isEnable;//是否启用

    @TableField(value = "CREATE_BY")
    private String createBy;//创建人

    @TableField(value = "CREATE_TIME")
    private Date createTime;//创建时间

    @TableField(value = "UPDATE_BY")
    private String updateBy;//修改人

    @TableField(value = "UPDATE_TIME")
    private Date updateTime;//修改时间

    @TableField(exist = false)
    private String id;

    @TableField(exist = false)
    private String text;
}
