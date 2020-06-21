package com.wstro.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuzuotao
 * @since 2017-07-18
 */
@TableName("system_user_position")
public class SystemUserPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("OID")
    private String oid;
    /**
     * 用户ID
     */
	@TableField("USER_ID")
	private Long userId;
    /**
     * 岗位编号
     */
	@TableField("POS_CODE")
	private String posCode;


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}


}
