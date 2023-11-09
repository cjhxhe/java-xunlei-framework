package com.xunlei.framework.common.util;

import com.xunlei.framework.common.enums.DatePattern;

import java.util.Collection;
import java.util.Date;

/**
 * Redis工具类
 */
public class RedisUtils {

    public static boolean isEmpty(Collection<?> values) {
        if (values == null || values.size() == 0) {
            return true;
        }
        if (values.size() == 1) {
            Object value = values.iterator().next();
            return value == null || "null".equals(value.toString());
        }
        return false;
    }

    public static boolean isNotEmpty(Collection<?> values) {
        return !RedisUtils.isEmpty(values);
    }

    /**
     * 按天为周期的缓存，缓存时间参数格式化
     */
    public static String formatCacheDay(Long time) {
        return formatCacheDay(new Date(time));
    }

    /**
     * 按天为周期的缓存，缓存时间参数格式化
     */
    public static String formatCacheDay(Date date) {
        return DateUtils.toString(date, DatePattern.YMD2);
    }

    /**
     * 按自然周计算的缓存周期，时间参数格式化方法
     * 统一取当前时间所在周的周一
     */
    public static String formatCacheWeek(Long time) {
        return formatCacheWeek(new Date(time));

    }

    /**
     * 按自然周计算的缓存周期，时间参数格式化方法
     * 统一取当前时间所在周的周一
     */
    public static String formatCacheWeek(Date date) {
        Date firstOfWeek = DateUtils.getFirstOfWeek(date);
        return DateUtils.toString(firstOfWeek, DatePattern.YMD2);

    }

    /**
     * 按月为周期的缓存，时间参数格式化
     */
    public static String formatCacheMonth(Long time) {
        return formatCacheMonth(new Date(time));
    }

    /**
     * 按月为周期的缓存，时间参数格式化
     */
    public static String formatCacheMonth(Date date) {
        return DateUtils.toString(date, DatePattern.YM2);
    }

    /**
     * 按小时周期的缓存，时间参数格式化
     */
    public static String formatCacheHour(Long time) {
        return formatCacheHour(new Date(time));
    }

    /**
     * 按小时为周期的缓存，时间参数格式化
     */
    public static String formatCacheHour(Date date) {
        return DateUtils.toString(date, DatePattern.YMD_H2);
    }

    /**
     * 按分钟周期的缓存，时间参数格式化
     */
    public static String formatCacheMinute(Long time) {
        return formatCacheMinute(new Date(time));
    }

    /**
     * 按分钟为周期的缓存，时间参数格式化
     */
    public static String formatCacheMinute(Date date) {
        return DateUtils.toString(date, DatePattern.yyyyMMddHHmm);
    }
}
