package com.dsg.entity.setting;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户数据过滤信息表
 * </p>
 *
 * @author zcb
 * @since 2017-12-05
 */
@Data
@TableName("sso_user_filter")
public class SsoUserFilter implements Serializable  {

	@TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("OID")
	private String oid;
    /**
     * 用户名
     */
	@TableField("USER_NAME")
	private String userName;
    /**
     * 是否需要过滤（Y-是，N-否）
     */
	@TableField("IS_NEED_FILTER")
	private String isNeedFilter;
    /**
     * 机构代码集合
     */
	@TableField("ORG_CODE_ARRAY")
	private String orgCodeArray;
    /**
     * 机构父节点代码集合
     */
	@TableField("ORG_CODE_P_ARRAY")
	private String orgCodePArray;
}
