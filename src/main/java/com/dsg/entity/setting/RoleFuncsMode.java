package com.dsg.entity.setting;

import lombok.Data;

import java.util.List;

@Data
public class RoleFuncsMode {

    //角色oid
    private String roleOid;

    //角色功能对应信息列表
    private List<SsoRoleFunc> functionList;
}
