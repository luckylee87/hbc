package com.wstro.common.util;

import org.apache.commons.beanutils.converters.NumberConverter;

import java.math.BigDecimal;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：BigDecimalConverter
 * 类描述：菜单控制器
 * 创建人：danagao
 * 创建时间：2017/6/17 11:42
 *
 * @version 1.0
 */
public class BigDecimalConverterNew extends NumberConverter {

    public BigDecimalConverterNew() {
        super(true);
    }

    @Override
    public Object convert(Class arg0, Object arg1) {
        BigDecimal p = (BigDecimal) arg1;
        if (p == null) {
            return null;
        } else {
            return p;
        }
    }


    @Override
    protected Class getDefaultType() {
        return BigDecimal.class;
    }
}
