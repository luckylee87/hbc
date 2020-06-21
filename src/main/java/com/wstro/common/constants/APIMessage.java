package com.wstro.common.constants;

public enum APIMessage {

    API_STATUS_SUCCESS(10000, "成功"),
    API_STATUS_CLIENT_INFO_INVALID(11001, "客户信息无效，无法获取授权码"),
    API_STATUS_CLIENT_ID_NOT_EXISTS(11002, "客户ID不存在"),
    API_STATUS_TOKEN_INVALID(11003, "错误的授权码"),
    API_STATUS_TOKEN_CACHE_NOT_EXISTS(11004, "授权码对应的缓存不存在"),
    API_STATUS_PROJECT_CODE_EMPTY(11005, "查询不到项目代码"),
    API_STATUS_USER_NAME_EMPTY(11006, "用户名为空"),
    API_STATUS_USER_INFO_INVALID(11007, "用户名或密码错误"),
    API_STATUS_USER_NOT_EXISTS(11008, "用户不存在"),
    API_STATUS_UPDATE_USER_FAIL(11009, "更新用户失败"),
    API_STATUS_USER_INFO_ISVALID_INVALID(11010, "此用户信息无效"),
    API_STATUS_USER_LOGIN_UNAUTHORIZED(11011, "用户没有该平台的访问权限，无法登录"),
    API_STATUS_PROJECT_CODE_NOT_EMPTY(11012,"项目代码不能为空"),
    API_STATUS_ORG_CODE_NOT_EMPTY(11013,"机构代码不能为空"),
    API_STATUS_COMPANY_NAME_NOT_EMPTY(11014,"企业名称不能为空"),
    API_STATUS_USER_TYPE_NOT_EMPTY(11015,"用户类型不能为空"),
    API_STATUS_USER_EXISTS(11016,"用户已存在"),
    API_STATUS_USER_PWD_NOT_EMPTY(11017,"密码不能为空"),
    API_STATUS_ROLE_NOT_SET(11018,"角色未设置"),
    API_STATUS_ORG_CODE_NOT_EXISTS(11019,"机构代码不存在"),
    API_STATUS_USER_UPDATEPWD_ERR(11020,"修改密码失败"),
    API_STATUS_USER_TYPE_ERR(11021,"用户类型必须为ORG或COM"),
    API_STATUS_PROJECT_CODE_ERR(11026,"项目代码不存在或子菜单未赋权限"),
    API_STATUS_EMAIL_EXISTS(11022,"Email已被注册"),
    API_STATUS_PHONE_EXISTS(11023,"手机号已被注册"),
    API_STATUS_USEEMAIL_EXISTS(11024,"用户名与已注册的邮箱相同"),
    API_STATUS_USEPHONE_EXISTS(11025,"用户名与已注册的手机号相同"),
    API_GET_LICENSE_FAIL(11026,"获取license授权码失败"),

    API_STATUS_SYSTEM_ERROR(99999, "系统异常");

    private Integer code;
    private String message;

    APIMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
