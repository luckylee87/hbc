package com.wstro.common.util.toolbox;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author simon.xie
 * @version 创建时间：2017年4月27日 下午10:12:17
 * @description：日期通用处理工具类
 */
public class DateUtil extends DateUtils {

    /**
     * 毫秒
     */
    public final static long MS = 1;
    /**
     * 每秒钟的毫秒数
     */
    public final static long SECOND_MS = MS * 1000;
    /**
     * 每分钟的毫秒数
     */
    public final static long MINUTE_MS = SECOND_MS * 60;
    /**
     * 每小时的毫秒数
     */
    public final static long HOUR_MS = MINUTE_MS * 60;
    /**
     * 每天的毫秒数
     */
    public final static long DAY_MS = HOUR_MS * 24;
    /**
     * 每月的毫秒数
     */
    public final static long MONTH_MS = DAY_MS * 30;
    /**
     * 每年的毫秒数
     */
    public final static long YEAR_MS = MONTH_MS * 12;
    /**
     * 标准日期（不含时间）格式化器
     */
    private final static SimpleDateFormat NORM_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 标准日期时间格式化器
     */
    private final static SimpleDateFormat NORM_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * HTTP日期时间格式化器
     */
    private final static SimpleDateFormat HTTP_DATETIME_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z",
            Locale.US);
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * date时间字符串为""时转为null；或者date的格式为2009-05-16T20:42:28时转为2009-05-16 20:42:28
     *
     * @Title: sjzh
     * @Description: TODO
     * @param: @param date
     * @param: @return
     * @return: String
     */
    public static String timeSwitch(String date) {
        Pattern p = null;
//		String zz1="\\d{1,4}[-]\\d{1,2}[-]\\d{1,2}[T]\\d{1,2}[:]\\d{1,2}[:]\\d{1,2}";//格式为2009-05-16T20:42:28
        //   	String zz2="\\d{1,4}[-]\\d{1,2}[-]\\d{1,2}[T]\\d{1,2}[:]\\d{1,2}[:]\\d{1,2}[+]\\d{1,2}[:]\\d{1,2}";
        if ("".equals(date)) {
            return null;
        }/*else if(Pattern.compile(zz1).matcher(date).matches()){
            date=date.replace("T", " ");
    	}*/
        return date;
    }


    public static String strFormate(String str) {
        if ("".equals(str)) {
            return null;
        } else if (str != null && str.contains("T")) {
            str = str.replace("T", " ");
        }
        return str;
    }


    /**
     * 当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的标准形式字符串
     */
    public static String now() {
        return formatDateTime(new Date());
    }

    /**
     * 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date 被格式化的日期
     * @return 格式化后的日期
     */
    public static String formatDateTime(Date date) {
        return NORM_DATETIME_FORMAT.format(date);
    }

    /**
     * 当前日期，格式 yyyy-MM-dd
     *
     * @return 当前日期的标准形式字符串
     */
    public static String today() {
        return formatDate(new Date());
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format 格式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 格式化为Http的标准日期格式
     *
     * @param date 被格式化的日期
     * @return HTTP标准形式日期字符串
     */
    public static String formatHttpDate(Date date) {
        return HTTP_DATETIME_FORMAT.format(date);
    }

    /**
     * 格式 yyyy-MM-dd
     *
     * @param date 被格式化的日期
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date) {
        return NORM_DATE_FORMAT.format(date);
    }

    /**
     * 将特定格式的日期转换为Date对象
     *
     * @param dateString 特定格式的日期
     * @param format     格式，例如yyyy-MM-dd
     * @return 日期对象
     */
    public static Date parse(String dateString, String format) {
        try {
            return (new SimpleDateFormat(format)).parse(dateString);
        } catch (ParseException e) {
            logger.error("Parse " + dateString + " with format " + format + " error!", e);
        }
        return null;
    }

    /**
     * 格式yyyy-MM-dd HH:mm:ss
     *
     * @param dateString 标准形式的时间字符串
     * @return 日期对象
     */
    public static Date parseDateTime(String dateString) {
        try {
            return NORM_DATETIME_FORMAT.parse(dateString);
        } catch (ParseException e) {
            logger.error("Parse " + dateString + " with format " + NORM_DATETIME_FORMAT.toPattern() + " error!", e);
        }
        return null;
    }

    /**
     * 格式yyyy-MM-dd
     *
     * @param dateString 标准形式的日期字符串
     * @return 标准形式的日期字符串
     */
    public static Date parseDate(String dateString) {
        try {
            return NORM_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            logger.error("Parse " + dateString + " with format " + NORM_DATE_FORMAT.toPattern() + " error!", e);
        }
        return null;
    }

    /**
     * 获取指定日期偏移指定时间后的时间
     *
     * @param date          基准日期
     * @param calendarField 偏移的粒度大小（小时、天、月等）使用Calendar中的常数
     * @param offsite       偏移量，正数为向后偏移，负数为向前偏移
     * @return 偏移后的日期
     */
    public static Date getOffsiteDate(Date date, int calendarField, int offsite) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarField, offsite);
        return cal.getTime();
    }

    /**
     * 判断两个日期相差的时长<br/>(列：1年前7月25日)
     * <p>
     * 返回 minuend - subtrahend 的差
     *
     * @param subtrahend 减数日期
     * @param minuend    被减数日期
     * @return 日期差
     */
    public static String dateDiff(Date subtrahend, Date minuend) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(subtrahend);
        long diff = minuend.getTime() - subtrahend.getTime();
        if (diff <= HOUR_MS)
            return diff / MINUTE_MS + "分钟前";
        else if (diff <= DAY_MS)
            return diff / HOUR_MS + "小时" + (diff % HOUR_MS / MINUTE_MS) + "分钟前";
        else if (diff <= DAY_MS * 2)
            return "昨天" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
        else if (diff <= DAY_MS * 3)
            return "前天" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
        else if (diff <= MONTH_MS)
            return diff / DAY_MS + "天前" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
        else if (diff <= YEAR_MS)
            return diff / MONTH_MS + "个月" + (diff % MONTH_MS) / DAY_MS + "天前" + calendar.get(Calendar.HOUR_OF_DAY) + "点" + calendar.get(Calendar.MINUTE) + "分";
        else
            return diff / YEAR_MS + "年前" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DATE) + "日";
    }


    /**
     * 距离截止日期还有多长时间
     *
     * @param date
     * @return
     */
    public static String fromDeadline(Date date) {
        long deadline = date.getTime();
        long now = new Date().getTime();
        long remain = deadline - now;
        if (remain <= HOUR_MS)
            return "只剩下" + remain / MINUTE_MS + "分钟";
        else if (remain <= DAY_MS)
            return "只剩下" + remain / HOUR_MS + "小时"
                    + (remain % HOUR_MS / MINUTE_MS) + "分钟";
        else {
            long day = remain / DAY_MS;
            long hour = remain % DAY_MS / HOUR_MS;
            long minute = remain % DAY_MS % HOUR_MS / MINUTE_MS;
            return "只剩下" + day + "天" + hour + "小时" + minute + "分钟";
        }
    }


    /**
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm:ss字符串，JDK1.7以上版本支持该方法
     *
     * @param sDay
     * @return
     */
    public static String DateString2formatString(String sDay) throws Exception {

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = sd.parse(sDay);
        return NORM_DATETIME_FORMAT.format(date);

    }


    public static String DateString3formatString(String sDay) throws Exception {
        return sDay.replace(" ", "T");
    }

    /**
     * 将字符串检查日期格式
     *
     * @param str
     * @return
     */
    public static boolean StringCheckDateTime(String str) throws Exception {
        String pattern = "([0-9]{4}-(0[1-9]|1[0-2])-[0-3][0-9] [0-2][0-3]:[0-5][0-9]:[0-5][0-9])?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.matches();
    }

    /**
     * 将字符串检查日期格式
     *
     * @param str
     * @return
     */
    public static boolean StringCheckDate(String str) throws Exception {
        String pattern = "([0-9]{4}-(0[1-9]|1[0-2])-[0-3][0-9])?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.matches();
    }

}
