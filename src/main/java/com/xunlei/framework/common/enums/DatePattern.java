package com.xunlei.framework.common.enums;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


/**
 * 日期格式枚举定义
 */
public enum DatePattern {

    YMD("yyyy-MM-dd"),

    YMD2("yyyyMMdd"),

    YMD3("yyyy年MM月dd日"),

    YMD4("yyyy-M-d"),

    YMD5("yyyy.MM.dd"),

    YMD6("yyyy.MM.dd HH:mm"),

    YMD7("yyyy/MM/dd"),

    YMD8("yyMMdd"),

    YMD9("MM/dd/yyyy"),

    YM("yyyy-MM"),

    YM2("yyyyMM"),

    YMD_HM("yyyy-MM-dd HH:mm"),

    YMD_HM2("yyyy-M-d HH:mm"),

    YMD_HM3("yyyy年MM月dd日 HH时mm分"),

    YMD_H2("yyyyMMddHH"),

    HM("HH:mm"),

    YMD_HMS("yyyy-MM-dd HH:mm:ss"),

    YMD_HMS2("yyyyMMddHHmmss"),

    YMD_HMS3("yyyy/MM/dd HH:mm:ss"),

    YMD_HMSS("yyyy-MM-dd HH:mm:ss.SSS"),

    YMD_HMSS2("yyyyMMddHHmmssSSS"),

    YMD_T_HMSZ("yyyy-MM-dd'T'HH:mm:ss'Z'"),

    MD("MM.dd"),

    MD2("MM月dd日"),

    MD3("M-d"),

    MD4("MMdd"),

    MD5("MM/dd"),

    MD_HMS("MM月dd日 HH:mm:ss"),

    MD_HMS2("MM.dd HH:mm:ss"),

    MD_HMS3("MM月dd日HH时mm分ss秒"),

    MD_HMS4("MM-dd HH:mm:ss"),

    MD_HMS5("M-d HH:mm:ss"),

    MD_HM("MM.dd HH:mm"),

    MD_HM2("M-d HH:mm"),

    MD_HM3("MM月dd日 HH点mm分"),

    MD_HM4("MM/dd HH:mm"),

    MD_HM5("MM月dd日 HH:mm"),

    MD_H("M月d日H点"),

    MD_H1("MM月dd日 HH时"),

    DD("dd"), yyyyMMddHHmm("yyyyMMddHHmm"),

    HMS("HH:mm:ss"),

    WEEK("EEEE");;

    private DateTimeFormatter format;

    DatePattern(String pattern) {
        this.format = DateTimeFormat.forPattern(pattern);
    }

    public DateTimeFormatter getFormat() {
        return this.format;
    }
}
