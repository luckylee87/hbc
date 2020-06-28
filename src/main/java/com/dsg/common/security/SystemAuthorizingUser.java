package com.dsg.common.security;

import java.io.Serializable;
import java.util.Date;


public class SystemAuthorizingUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String realName;


    /**
     * 用户类型
     */
    private String userType;

    /**
     * 企业编号
     */
    private String inputCopNo;

    /**
     * 企业名称
     */
    private String inputCopName;

    /**
     * 社会信用代码
     */
    private String copGbCode;

    /**
     * 主管海关
     */
    private String customsCode;

    /**
     * 场地代码
     */
    private String areaCode;

    /*
    * 页面新增时需要显示的公共字段
    * */
    private Date createTime;
    private String createBy;
    private String createName;
    private String inputerCode;
    private String inputerName;
    private Date decTime;

    /*
    *   整合权限接口新增字段
     */
    //IC卡号
    private String icCode;
    //邮箱地址
    private String email;
    //住址
    private String address;
    //密码
    private String password;
    //机构代码
    private String orgCode;
    //手机号码
    private String telephone;


    public SystemAuthorizingUser(String accountId, String loginName, String userName, String realName) {
        super();
        this.userId = accountId;
        this.loginName = loginName;
        this.userName = userName;
        this.realName = realName;
    }

    public SystemAuthorizingUser(String accountId, String loginName, String userName, String realName, String userType, String tradeCode, String entName) {
        super();
        this.userId = accountId;
        this.loginName = loginName;
        this.userName = userName;
        this.realName = realName;
        this.userType = userType;
        this.inputCopNo = tradeCode;
        this.inputCopName = entName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getInputCopNo() {
        return inputCopNo;
    }

    public void setInputCopNo(String inputCopNo) {
        this.inputCopNo = inputCopNo;
    }

    public String getInputCopName() {
        return inputCopName;
    }

    public void setInputCopName(String inputCopName) {
        this.inputCopName = inputCopName;
    }

    public String getCopGbCode() {
        return copGbCode;
    }

    public void setCopGbCode(String copGbCode) {
        this.copGbCode = copGbCode;
    }

    public String getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode(String customsCode) {
        this.customsCode = customsCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getInputerCode() {
        return inputerCode;
    }

    public void setInputerCode(String inputerCode) {
        this.inputerCode = inputerCode;
    }

    public String getInputerName() {
        return inputerName;
    }

    public void setInputerName(String inputerName) {
        this.inputerName = inputerName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDecTime() {
        return decTime;
    }

    public void setDecTime(Date decTime) {
        this.decTime = decTime;
    }

    public String getIcCode() {
        return icCode;
    }

    public void setIcCode(String icCode) {
        this.icCode = icCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
