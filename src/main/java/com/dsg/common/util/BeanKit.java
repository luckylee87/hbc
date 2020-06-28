package com.dsg.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：BeanKit
 * 类描述：Bean处理工具
 * 创建人：danagao
 * 创建时间：2017/5/21 11:02
 *
 * @version 1.0
 */
public class BeanKit {

    /*
    * 判断bean中是否存在某个属性
    * */
    public static boolean propertyExists(Object object, String propertyName) {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(propertyName)) {
                return true;
            }
        }

        return false;
    }

    /*
  * 调用service的selectList方法
  * */
    public static List selectRedisDropDown(Object tableService) {
        Class clazz = tableService.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        try {
            for (Method method : methods) {
                if (method.getName().equals("selectRedisDropDown")) {
                    return (List) method.invoke(tableService);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
    * 给bean中的某个属性赋值
    * */
    public static void setProperty(Object object, String propertyName, Object propertyValue) {
        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        try {
            for (Method method : methods) {
                if (method.getName().equalsIgnoreCase("set" + propertyName)) {
                    method.invoke(object, propertyValue);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /*
    * 获取bean中某个属性的值
    * */
    public static Object getProperty(Object object, String propertyName) {
        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        try {
            for (Method method : methods) {
                if (method.getName().equalsIgnoreCase("get" + propertyName)) {
                    return method.invoke(object);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
