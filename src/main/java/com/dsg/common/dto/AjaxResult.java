package com.dsg.common.dto;

import com.dsg.common.constants.MessageConstants;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：AjaxResult
 * 类描述：封装Ajax结果,所有Ajax请求返回类型
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 * 修改人：haihuihuang
 * 修改时间：2017年5月5日 下午17:57
 */
public class AjaxResult {

    /**
     * 返回代码
     */
    private int code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;
    /**
     * 返回数据总数
     */
    private int total;    // 数据总数

    public AjaxResult(MessageConstants messageConstants) {
        super();
        this.code = messageConstants.getCode();
        this.message = messageConstants.getMessage();
    }

    public AjaxResult(MessageConstants messageConstants, Object data) {
        super();
        this.code = messageConstants.getCode();
        this.message = messageConstants.getMessage();
        this.data = data;
    }

    public AjaxResult(MessageConstants messageConstants, Object data, int total) {
        super();
        this.code = messageConstants.getCode();
        this.message = messageConstants.getMessage();
        this.data = data;
        this.total = total;
    }

    public AjaxResult(Integer code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public AjaxResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
