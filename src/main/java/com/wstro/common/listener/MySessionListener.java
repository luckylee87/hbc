package com.wstro.common.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author simon.xie
 * @version 创建时间：2017年4月27日 下午10:12:17
 * @description：监听器,统计在线用户人数
 */
@WebListener
public class MySessionListener implements HttpSessionListener {

    //全站在线人数
    public static int userNumber;
    //创建session的时候+1

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        userNumber++;
        System.out.println("全站在线人数:" + userNumber);
        se.getSession().getServletContext().setAttribute("userNumber", userNumber);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        userNumber--;
        System.out.println("全站在线人数:" + userNumber);
        se.getSession().getServletContext().setAttribute("userNumber", userNumber);
    }

}
