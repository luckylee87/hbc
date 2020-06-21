package com.wstro.common.util;

import com.google.code.kaptcha.Constants;
import com.wstro.common.constants.CommonConstants;
import com.wstro.common.security.SystemAuthorizingUser;
import com.wstro.common.util.toolbox.StringUtil;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author simon.xie
 * @version 创建时间：2017年4月27日 下午10:12:17
 * @description：获取登录用户通用处理工具类
 */
public class SingletonLoginUtils {

    private static final Logger logger = LoggerFactory.getLogger(SingletonLoginUtils.class);

    /**
     * 验证验证码
     *
     * @return
     */
    public static boolean validate(HttpServletRequest request) {
        // 获取Session中验证码
//		Object captcha = ServletUtils.getAttribute(Constants.KAPTCHA_SESSION_KEY);

        String registerCode = ServletUtils.getParameter("registerCode");
        if (StringUtil.isBlank(registerCode)) {
            return false;
        }


        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return true;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Constants.KAPTCHA_SESSION_KEY)) {
                return registerCode.equalsIgnoreCase(cookie.getValue());
            }
        }
        return false;
    }

    /**
     * 获取后台登录用户
     *
     * @return SystemAuthorizingUser
     */
    public static SystemAuthorizingUser getSystemUser() {
        try {
//            Subject subject = SecurityUtils.getSubject();
//            SystemAuthorizingUser systemUser = (SystemAuthorizingUser) subject.getPrincipal();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            SystemAuthorizingUser systemUser = (SystemAuthorizingUser)request.getSession().getAttribute(CommonConstants.SESSION_USER_INFO);
            if (systemUser != null) {
                return systemUser;
            }
        } catch (UnavailableSecurityManagerException e) {
            logger.error("SystemUserServiceImpl.getSystemUser", e);
        } catch (InvalidSessionException e) {
            logger.error("SystemUserServiceImpl.getSystemUser", e);
        }
        return null;
    }

    /**
     * 获取后台登录用户ID
     *
     * @return
     */
    public static String getSystemUserId() {
        return getSystemUser().getUserId();
    }

    /**
     * 获取后台登录用户昵称
     *
     * @return
     */
    public static String getSystemUserName() {
        return getSystemUser().getUserName();
    }
}
