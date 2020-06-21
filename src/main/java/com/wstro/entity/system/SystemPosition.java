package com.wstro.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author xuzuotao
 * @since 2017-07-18
 */
@TableName("system_position")
public class SystemPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("OID")
    private String oid;
    /**
     * 岗位编号
     */
    @TableField("POS_CODE")
    private String posCode;
    /**
     * 岗位名称
     */
    @TableField("POS_NAME")
    private String posName;
    /**
     * 备注
     */
    @TableField("REMARKS")
    private String remarks;


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
