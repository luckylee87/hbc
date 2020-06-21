package com.wstro.common.constants;

import com.wstro.common.util.PropertiesUtils;

/**
 * 系统常量
 *
 * @author simon.xie
 */
public class CommonConstants {

    /**
     * 获取properties配置文件属性
     */
    private static final String CONSTANTSPROPERTIES = "properties/constants.properties";

    //用static修饰的代码块表示静态代码块，当Java虚拟机（JVM）加载类时，就会执行该代码块
    static {
        PropertiesUtils.readProperties(CONSTANTSPROPERTIES);
    }

    /**
     * 项目路径
     */
    private static final String CONTEXTPATH = "contextPath";
    public static final String contextPath = PropertiesUtils.getProperty(CONTEXTPATH);
    /**
     * 静态资源
     */
    private static final String STATICSERVER = "staticServer";
    public static final String staticServer = PropertiesUtils.getProperty(STATICSERVER);

    //S：发送
    public static final String S = "S";
    //R：接收
    public static final String R = "R";
    //生成
    public static final String C = "C";
    //处理
    public static final String D = "D";
    //已处理
    public static final String Y = "Y";
    //未处理
    public static final String N = "N";
    //异常E
    public static final String Error = "E";
    public static final String EDI_INTODB_SUCCESS = "文件转入成功！";
    public static final String EDI_INTODB_ERROR = "文件转入异常！";
    /**
     * 缓存key
     */
    public static final String SESSION_TOKEN = "SESSION_TOKEN";
    public static final String SESSION_USER_INFO = "SESSION_USER_INFO";

    /**
     * 用户类型
     */
    //系统管理员
    public static final String USER_TYPE_SYS = "SYS";
    //机构管理员
    public static final String USER_TYPE_ORG = "ORG";
    //普通用户
    public static final String USER_TYPE_COM = "COM";
    //演示用户
    public static final String USER_TYPE_DEMO = "DEMO";

    /**
     * 权限平台代码
     */
    public static final String SSO_PLATFORM_CODE = "SSO";

    /**
     * token缓存key
     */
    public static final String CACHE_TOKEN_KEY = "code-cache";
}
