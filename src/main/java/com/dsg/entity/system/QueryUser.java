package com.dsg.entity.system;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：QueryUser
 * 类描述：查询用户实体类
 * 创建人：simon.xie
 * 创建时间：2016年10月17日 下午6:45:21
 */
public class QueryUser {

    /**
     * 搜索内容
     */
    private String searchContent;

    /**
     * 状态 0正常 1冻结 2删除
     */
    private Integer status;

    /**
     * 查询 开始注册时间
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date beginCreateTime;

    /**
     * 查询 结束注册时间
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date endCreateTime;

    /**
     * 企业编号
     */
    private String tradeCode;

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(Date beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }
}
