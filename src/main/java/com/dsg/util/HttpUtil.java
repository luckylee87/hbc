package com.dsg.util;

import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * HttpServletRequest帮助类
 * 
 */
public class HttpUtil {

	/**
	 * 获取当前用户浏览器信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 当前用户浏览器信息
	 */
	public static String getHeader(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	/**
	 * 获取当前用户浏览器型号
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 当前用户浏览器型号
	 */
	public static String getUserBrowser(HttpServletRequest request) {
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		Browser browser = userAgent.getBrowser();
		return browser.toString();
	}

	/**
	 * 获取当前用户系统型号
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 当前用户系统型号
	 */
	public static String getUserOperatingSystem(HttpServletRequest request) {
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		OperatingSystem operatingSystem = userAgent.getOperatingSystem();
		return operatingSystem.toString();
	}
}
