package com.wstro.entity.setting;

import lombok.Data;

/**
 * <p>
 * 角色授权树形结构数据实体
 * </p>
 */
@Data
public class AuthorizeTreeMode {

    //项目代码
    private String projectCode;

    //项目名称
    private String projectName;

    //功能代码
    private String functionCode;

    //功能名称
    private String functionName;

    //父节点代码
    private String parentCode;

    //父节点名称
    private String parentName;

    //是否可以授权
    private String isAuthorized;
}
