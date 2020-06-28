package com.dsg.common.constants;

/**
 * 项目名称：sso-web
 * 类名称：MessageConstants
 * 类描述：返回状态信息
 * 创建人：willchen
 * 创建时间：2017/5/4
 * 修改人：willchen
 * 修改时间：2017/5/4
 */
public enum MessageConstants {
    SSO_STATUS_FAIL(0, "失败"),
    SSO_STATUS_SUCCESS(1, "成功"),
    SSO_STATUS_VERIFY_WRONG(2, "验证码错误"),
    SSO_STATUS_USER_NOTEXIST(3, "该账号不存在"),
    SSO_STATUS_USER_DISABLE(4, "该账号已被冻结"),
    SSO_STATUS_USER_PASSWORD(5, "密码错误"),
    SSO_STATUS_MENU_REDUPLICATED(6, "该菜单名已被使用"),
    SSO_STATUS_MENU_CREATE_SUCCESS(7, "菜单创建成功"),
    SSO_STATUS_MENU_UPDATE_SUCCESS(8, "菜单修改成功"),
    SSO_STATUS_EMAIL_INPUT_ERROR(9, "请输入正确的电子邮箱"),
    SSO_STATUS_PHONE_INPUT_ERROR(10, "请输入正确的手机号码"),
    SSO_STATUS_USER_REDUPLICATED(11, "该用户名已被使用"),
    SSO_STATUS_USER_CREATE_SUCCESS(12, "用户创建成功"),
    SSO_STATUS_USER_UPDATE_SUCCESS(13, "用户信息修改成功"),
    SSO_STATUS_USER_OVERTIME(14, "您未登录或者登录已超时,请先登录!"),
    SSO_STATUS_PASSWORD_EXIST_ERROR(15, "原密码不正确"),
    SSO_STATUS_PASSWORD_INPUT_ERROR(16, "密码长度8~16位，其中数字，字母和符号至少包含两种!"),
    SSO_STATUS_PASSWORD_UPDATE_ERROR(17, "两次输入的新密码不一致"),
    SSO_STATUS_PASSWORD_UPDATE_SUCCESS(18, "密码修改成功"),
    SSO_STATUS_IMAGE_FILE_ERROR(19, "不支持的文件类型，仅支持图片!"),
    SSO_STATUS_IMAGE_UPDATE_SUCCESS(20, "上传成功!"),
    SSO_STATUS_IMAGE_UPDATE_FAIL(21, "上传失败!"),
    SSO_STATUS_ROLE_CREATE_SUCCESS(22, "角色创建成功!"),
    SSO_STATUS_ROLE_UPDATE_SUCCESS(23, "角色修改成功!"),
    SSO_STATUS_ROLE_DELETE_SUCCESS(24, "角色删除成功!"),
    SSO_STATUS_ROLE_DELETE_CHOOSE(25, "请选择要删除的角色!"),
    SSO_STATUS_VERIFY_INPUT_ERROR(26, "请输入正确的验证码!"),
    SSO_STATUS_VERIFY_OVERTIME(27, "验证码已过期,请重新输入验证码!"),
    SSO_STATUS_INTERFACE_JURISDICTION(28, "没有此接口的权限"),
    SSO_STATUS_CODE_NOTEXIST(29, "Code不能为空"),
    SSO_STATUS_COPENT_SOLE(30, "企业备案数据必须唯一"),
    SSO_STATUS_APPROVAL_NOTDELETE(31, "当前单据状态不可删除"),
    SSO_STATUS_APPROVAL_NOTBATCHDELETE(32, "存在单据状态不可删除"),
    SSO_STATUS_VEHICLENO_IsExist(33, "当前车牌号已备案或提交申报中，请检查"),
    SSO_STATUS_VEHICLENO_Exist(34, "第二车牌号已备案或提交申报中，请检查"),
    SSO_STATUS_SASDCLBSC_ISNOTCHANGE(35, "当前记录已存在变更申请记录或已结案，不可变更，请检查"),
    SSO_STATUS_SASDCLBSC_ISNOTCLOSE(36, "当前记录已存在变更申请记录，不可结案，请检查"),
    SSO_STATUS_SUBMIT_FAIL(37, "申报失败"),
    SSO_STATUS_COPY_SUCCESS(38, "成功复制记录"),
    SSO_STATUS_COPY_FAIL(39, "复制失败"),
    SSO_STATUS_SAS_DCL_DT_CHANGE(40, "变更失败"),
    SSO_STATUS_EMS_ISNOTCHANGE(41, "当前账册已存在变更申请记录，不可变更，请检查"),
    SSO_STATUS_SAVE_SUCCESS(42, "保存成功"),
    SSO_STATUS_DECLARE_SUCCESS(43, "申报成功"),
    SSO_STATUS_UNKOWN(-1, "未知错误,请联系管理员!"),
    SSO_STATUS_REPEAT_SUBMIT(44, "不允许重复提交"),

    SSO_STATUS_APPLYID(52, "应用id为空"),
    SSO_STATUS_CUSTOMSCODE(53, "关区代码为空"),
    SSO_STATUS_BILLCODE(54, "单据代码为空"),
    SSO_STATUS_BILLCODES(55, "单据代码非法"),
    SSO_STATUS_AREACODE(56, "场所代码为空"),
    SSO_STATUS_DOCTYPE(57, "单据类型为空"),
    SSO_STATUS_SERVERTYPE(58, "服务类型为空"),
    SSO_STATUS_PARAM(59, "没有找到对应的单据生成规则"),
    SSO_STATUS_EMSNO_ERROR(60, "账册编号不能为空"),
    SSO_STATUS_ENDPRD_SEQNO_ERROR(61, "成品序号不能为空"),
    SSO_STATUS_GDS_SEQNO_EXIST(61, "序号已存在"),
    SSO_STATUS_ENDPRD_NET_WT(62, "净重必须小于或等于毛重"),
    SSO_STATUS_SAS_STOCK_SEQNO(63, "商品序号已存在，请检查"),
    SSO_STATUS_SAS_STOCK_Dt(64, "申报商品不能为空，请检查"),
    SSO_STATUS_TRADECODE_INPUT_ERROR(65, "请输入正确的企业编号"),
    SSO_STATUS_ENTNAME_INPUT_ERROR(66, "请输入正确的企业名称"),
    SSO_STATUS_SAS_STOCK_DtTotal(67, "申报数量超过申请表申报的数量"),

    SSO_STATUS_UNAUTHORIZED(403, "无权限"),
    SSO_STATUS_UNLOGIN(-999, "未登录"),
    SSO_STATUS_EMS(1001, ""),
    SSO_STATUS_EMS_AREACODE_ERROR(1002, "监管场所不能为空"),
    SSO_STATUS_EMS_DOCTYPE_ERROR(1003, "单据类型不能为空"),
    SSO_STATUS_EMS_BIZTYPE_ERROR(1004, "业务类型不能为空"),
    SSO_STATUS_EMS_SEQNO_ERROR(1005, "单据编号不能为空"),
    SSO_PARAMETER_NO_INCORRECT(1006, "参数不正确"),
    SSO_USERNAME_PWD_NOTEMPTY(1007, "用户名和密码不能为空"),
    SSO_ORGANIZATION_CODE_EXIST(1008, "机构代码已存在"),
    SSO_ORGCOMPANY_CUSCODE_EXIST(1009, "企业海关编码已存在"),
    SSO_ORGANIZATION_CODE_NO_EXIST(1010, "机构代码不存在"),
    SSO_ROLEUSER_NO_EXIST(1011, "该用户角色不存在"),
    SSO_STATUS_USER_NOT_EXISTS(1012, "用户不存在或该用户不存在机构"),
    SSO_USER_ORGCOMPANY_CODE_NOT_EXISTS(1014, "该企业机构代码不存在"),
    SSO_USER_PASSWORD_SAME(1015, "新旧密码不能相似"),
    SSO_ROLEUSER_NO_MATCHING(1016, "角色名不存在或角色名不匹配");

    private Integer code;
    private String message;

    MessageConstants(Integer code, String message) {
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
