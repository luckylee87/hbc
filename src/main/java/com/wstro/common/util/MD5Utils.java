package com.wstro.common.util;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 项目名称：sso Maven Webapp
 * 类名称：MD5Utils
 * 类描述：MD5加密工具类
 * 创建人：simon.xie
 * 创建时间：2017年4月27日 下午10:12:17
 * 修改人：simon.xie
 * 修改时间：2017年4月27日 下午10:12:17
 * 修改备注：
 */
public class MD5Utils {

    private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

    public static String getMD5(String src) {
        try {
            // 创建加密对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");//提供信息摘要算法的功能
            byte[] md5bytes = messageDigest.digest(src.getBytes());//使用指定的 byte 数组对摘要进行最后更新，然后完成摘要计算
            String encrypt = Hex.toHexString(md5bytes);
            return encrypt;
        } catch (NoSuchAlgorithmException e) {
            logger.info("MD5Utils.getMD5", e);
        }
        return null;
    }
}
