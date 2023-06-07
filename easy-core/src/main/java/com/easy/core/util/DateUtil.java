package com.easy.core.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期操作工具类
 *
 * @author liupenghao
 */
@Log4j2
public class DateUtil extends cn.hutool.core.date.DateUtil {

    /**
     * 日期时间格式 - yyyyMMdd
     */
    private static final String DF_YYYYMMDD = "yyyyMMdd";
    /**
     * 日期时间格式 - yyyyMMddHHmmss
     */
    private static final String DF_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 日期时间格式 - yyyy-MM-dd HH:mm:ss
     */
    private static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间的yyyy-MM-dd HH:mm:ss格式字符串.
     *
     * @return yyyy-MM-dd HH:mm:ss格式的日期时间字符串
     */
    public static String getNowDateTimeStr() {
        return getNowDateTimeStr(DF_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前时间的yyyy-MM-dd HH:mm:ss格式字符串.
     *
     * @param pattern 指定的日期时间字符串模式
     * @return 获取到的指定模式的日期时间字符串
     */
    public static String getNowDateTimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 转换 {@linkplain #DF_YYYYMMDDHHMMSS yyyyMMddHHmmss} 格式long型日期数值为毫秒时间戳.
     *
     * @param date 待转换的long型日期数值
     * @return 转换后的毫秒时间戳
     */
    public static Long convertToTimeMillis(Long date) {
        LocalDateTime dateTime = convertToDateTime(date);
        if (dateTime != null) {
            return Timestamp.valueOf(dateTime).getTime();
        }
        return date;
    }

    /**
     * 转换 {@linkplain #DF_YYYYMMDDHHMMSS yyyyMMddHHmmss} 格式long型日期数值为毫秒时间戳.
     *
     * @param date 待转换的long型日期数值
     * @return 转换后的本地日期时间
     */
    public static LocalDateTime convertToDateTime(Long date) {
        if (date != null) {
            try {
                return LocalDateTime.parse(String.valueOf(date), DateTimeFormatter.ofPattern(DF_YYYYMMDDHHMMSS));
            } catch (Exception e) {
                log.warn("", e);
            }
        }
        return null;
    }

    /**
     * LocalDateTime 转 时间戳
     *
     * @param dateTime 日期时间
     * @return 时间戳
     */
    public static long dateTime2Long(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * LocalDateTime 转化为 Date
     *
     * @param localDateTime localDateTime
     * @return date
     */
    public static Date LocalDateTimeToDate(LocalDateTime localDateTime) {
        return new Date(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }

    /**
     * Date 转化为 LocalDateTime
     *
     * @param date 日期
     * @return localDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * Date 转化为 LocalDate
     *
     * @param date 日期
     * @return localDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 快速日期格式化
     *
     * @param date    日期
     * @param pattern 模式, 例: yyyy-MM-dd HH:mm:ss
     * @return {@link String}
     */
    public static String fastDateFormat(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 快速日期格式化
     *
     * @param millis  毫秒
     * @param pattern 模式, 例: yyyy-MM-dd HH:mm:ss
     * @return {@link String}
     */
    public static String fastDateFormat(long millis, String pattern) {
        return DateFormatUtils.format(millis, pattern);
    }
}
