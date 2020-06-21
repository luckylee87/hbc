package com.wstro.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("system_log")
public class SystemLog implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableId(value = "OID")
    private String oid;  //主键oid

    @TableField(value = "APPLY_ID")
    private String applyId;//应用id

    @TableField(value = "METHOD")
    private String method;//执行方法

    @TableField(value = "REPONES_DATA")
    private String reponesData;//响应时间

    @TableField(value = "IP")
    private String ip; //ip

    @TableField(value = "DATA")
    private Date data; //执行时间

    @TableField(value = "TYPE")
    private Integer type;  //问题类型

    @TableField(value = "COMMITE")
    private String commite;  //执行描述
}