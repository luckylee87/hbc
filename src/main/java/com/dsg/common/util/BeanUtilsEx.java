package com.dsg.common.util;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：BeanUtilsEx
 * 类描述：扩展BeanUtils扩展类
 * 创建人：danagao
 * 创建时间：2017/6/17 11:37
 *
 * @version 1.0
 */

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

/**
 * @author wangzs
 * @Title 支持对BigDecimal类null值得copy
 * @Description
 * @date 2017-1-5
 */
public class BeanUtilsEx extends BeanUtils {
    static {
        ConvertUtils.register(new DateConvertNew(), java.util.Date.class);
        ConvertUtils.register(new DateConvertNew(), java.sql.Date.class);
        ConvertUtils.register(new BigDecimalConverterNew(), BigDecimal.class);
    }

    public static void copyProperties(Object target, Object source) {
    // 支持对BigDecimal类null值得copy
        try {
            BeanUtils.copyProperties(target, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
