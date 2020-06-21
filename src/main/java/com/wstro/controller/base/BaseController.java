package com.wstro.controller.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wstro.common.constants.MessageConstants;
import com.wstro.common.dto.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;


/**
 * 项目名称：sso Maven Webapp
 * 类名称：BaseController
 * 类描述： 公共表示层：控制器支持类
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：haihuihuang
 * 修改时间：2017年5月8日 下午17:13
 */
public abstract class BaseController {

    private static ValidatorFactory factory;
    private static Validator validator;
//    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);
    /** ============================     ajax    =================================================  */

    /**
     * 获取当前请求对象
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取页面地址url
     *
     * @param path
     * @return
     */
    protected static String getViewPath(String path) {
        StringBuffer viewPath = new StringBuffer();
        viewPath.append(path);
        return viewPath.toString();
    }

    /**
     * 返回状态
     *
     * @param messageConstants 状态码
     * @return
     */
    public AjaxResult result(MessageConstants messageConstants) {
        return new AjaxResult(messageConstants);
    }

    /**
     * 返回json数据
     *
     * @param messageConstants 状态
     * @param data             实体
     * @return
     */
    public AjaxResult json(MessageConstants messageConstants, Object data) {
        return new AjaxResult(messageConstants, data);
    }

    /**
     * 返回json数据
     *
     * @param messageConstants 状态
     * @param data             实体
     * @param total            数据总数
     * @return
     */
    public AjaxResult json(MessageConstants messageConstants, Object data, int total) {
        return new AjaxResult(messageConstants, data, total);
    }

    /**
     * 转换为ajax需要的 JSON
     *
     * @param messageConstants 状态
     * @param entity           实体
     * @return
     */
    protected Map<String, Object> setJson(MessageConstants messageConstants, Object entity) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("code", messageConstants.getCode());
        json.put("message", messageConstants.getMessage());
        json.put("entity", entity);
        return json;
    }

    /**
     * 转换为ajax需要的 JSON
     *
     * @param status  状态
     * @param message 消息
     * @param data    返回数据
     * @return
     */
    protected Map<String, Object> transJson(String status, String message, Object data) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("status", status);
        json.put("message", message);
        json.put("data", data);
        return json;
    }

    /**
     * 转换为ajax需要的 JSON
     *
     * @param code    状态
     * @param message 消息
     * @return
     */
    protected AjaxResult setJson(int code, String message) {
        return new AjaxResult(code, message);
    }

    /**
     * @param message 消息
     * @return
     */
    protected AjaxResult setErrorJson(String message) {
        return new AjaxResult(0, message);
    }

    /**
     * @param message 消息
     * @param object  返回数据
     * @return
     */
    protected AjaxResult setErrorJson(String message, Object object) {
        return new AjaxResult(0, message, object);
    }

    /** ============================     requset    =================================================  */

    /**
     * ajax成功/失败
     *
     * @param success 状态
     * @return
     */
    protected Map<String, Object> setJson(boolean success) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("success", success);
        return json;
    }

    /**
     * ajax成功/失败 + 消息
     *
     * @param success 状态
     * @param message 消息
     * @return
     */
    protected Map<String, Object> setJson(boolean success, String message) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("message", message);
        json.put("success", success);
        return json;
    }

    /**
     * getParameter系列的方法主要用于处理“请求数据”，是服务器端程序获取浏览器所传递参数的主要接口。
     *
     * @param name 表单name属性
     * @return
     */
    public String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * getParameterValues这个方法是获得传过来的参数名相同的一个数组;
     *
     * @param name
     * @return
     */
    public String[] getParameterValues(String name) {
        return getRequest().getParameterValues(name);
    }

    /**
     * getAttribute这个方法是提取放置在某个共享区间的对象
     *
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        return getRequest().getSession().getAttribute(name);
    }

    /**
     * 返回的是相对路径，工程的项目的相对路径
     *
     * @return
     */
    public String getContextPath() {
        return getRequest().getContextPath();
    }

    /**
     * 重定向至地址 url
     *
     * @param url 请求地址
     * @return
     */
    protected String redirectTo(String url) {
        StringBuffer rto = new StringBuffer("redirect:");
        rto.append(url);
        return rto.toString();
    }

    /**
     * 生成随机数
     *
     * @param count 生成个数
     * @return String
     */
    protected String getRandomNum(int count) {
        Random ra = new Random();
        String random = "";
        for (int i = 0; i < count; i++) {
            random += ra.nextInt(9);
        }
        return random;
    }

    /**
     * 返回boolean值（用于判断查询是升序，还是降序）
     *
     * @param orderSort 是否升序
     * @return boolean
     */
    protected boolean getOrderSort(String orderSort) {
        if (orderSort == null || "".equals(orderSort)) return true;
        if ("asc".equals(orderSort)) return true;
        return false;
    }

    /**
     * 获取分页
     *
     * @return Page
     */
    protected Page getPage() {

        int pageSize = 20;
        try {
            if (getParameter("pageSize") != null && !"".equals(getParameter("pageSize"))) {
                pageSize = Integer.parseInt(getParameter("pageSize"));
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int pageNumber = 0; // 起始行数
        try {
            if (getParameter("pageNumber") != null && !"".equals(getParameter("pageNumber"))) {
                pageNumber = Integer.parseInt(getParameter("pageNumber"));
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Page page = new Page(pageNumber / pageSize + 1, pageSize); // (页数，页面大小)
        return page;
    }

    /**
     * @Title: valid
     * @Description: 通用校验方法
     * @param: @param result
     * @param: @return
     * @return: AjaxResult
     * @author 宋轲
     */
    protected AjaxResult valid(BindingResult result) {
        AjaxResult ajaxResult = null;
        //字段验证
        if (result.hasErrors()) {
            //获取全部的字段验证错误
            List<FieldError> fieldErrors = result.getFieldErrors();
            StringBuffer str = new StringBuffer();

            for (FieldError fieldError : fieldErrors) {
                //将拼接的字段及错误信息封装到json结果集
                ajaxResult = setJson(0, str.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append(" ").toString());
            }
        }
        return ajaxResult;
    }

    /**
     * @Title: check
     * @Description: 通用校验方法
     * @param: @param object
     * @param: @return
     * @return: AjaxResult
     */
    public AjaxResult check(Object object) {
        AjaxResult ajaxResult = null;
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Set<ConstraintViolation<Object>> set = validator.validate(object);
        for (ConstraintViolation<Object> c : set) {
            StringBuffer str = new StringBuffer();
            ajaxResult = setJson(0, str.append(c.getMessage()).toString());
            break;
        }

        return ajaxResult;
    }

}
 