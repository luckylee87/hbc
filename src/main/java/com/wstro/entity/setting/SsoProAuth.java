package com.wstro.entity.setting;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zcb
 * @since 2017-11-23
 */
@Data
@TableName("sso_pro_auth")
public class SsoProAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("OID")
	private String oid;
    /**
     * 客户端id
     */
	@TableField("CLIENT_ID")
	private String clientId;
    /**
     * 密匙
     */
	@TableField("CLIENT_KEY")
	private String clientKey;
    /**
     * 授权系统代码
     */
	@TableField("AUTH_PROCODE")
	private String authProcode;

	/**
	 * 授权系统名称
	 */
	@TableField("AUTH_PRONAME")
	private String authProname;

	/**
	 * 是否启动
	 */
	@TableField("IS_ENABLED")
	private String isEnabled;
}
