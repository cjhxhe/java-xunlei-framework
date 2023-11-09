package com.xunlei.framework.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 消息格式化工具类，
 */
public final class MsgUtils {

    private static final Logger logger = LoggerFactory.getLogger(MsgUtils.class);

    private static final Pattern PATTERN = Pattern.compile("\\{}");
    private static final Pattern PATTERN_TPL = Pattern.compile("(\\{\\s*[\\w\\.]+\\s*\\})");
    private static final Pattern PATTERN_TPL_DOUBLE = Pattern.compile("(\\{\\{\\s*[\\w\\.]+\\s*\\}\\})");
    private static final Pattern PATTERN_$ = Pattern.compile("(\\$\\{\\s*[\\w\\.]+\\s*\\})");

    /**
     * 格式化字符串
     * 字符串格式："hello, {}"
     *
     * @param message
     * @param args
     * @return
     */
    public static String format(String message, Object... args) {
        if (message == null) {
            return null;
        }
        if (args == null || args.length == 0) {
            return message;
        }
        int length = args.length;
        Matcher m = PATTERN.matcher(message);
        StringBuffer sb = new StringBuffer();
        int index = 0;
        Object value;
        while (m.find()) {
            if (index >= length) {
                break;
            }
            value = args[index++];
            if (value == null) {
                m.appendReplacement(sb, "null");
            } else {
                m.appendReplacement(sb, Matcher.quoteReplacement(value.toString()));
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 格式化模板
     * <p>
     * 模板支持如："hello, {user}"，
     * 参数须以Key-Value形式的Map参数
     * 模板中可支持"."符号
     *
     * @param template
     * @param kvArgs
     * @return
     */
    public static String formatTpl(String template, Object... kvArgs) {
        return MsgUtils.formatTpl(template, MapUtils.gmap(kvArgs));
    }

    /**
     * 格式化模板
     * <p>
     * 模板支持如："hello, {user}"，
     * 参数须以Key-Value形式的Map参数
     * 模板中可支持"."符号
     *
     * @param template
     * @param args
     * @return
     */
    public static String formatTpl(String template, Map<String, Object> args) {
        if (template == null) {
            return null;
        }
        Matcher m = PATTERN_TPL.matcher(template);
        StringBuffer buffer = new StringBuffer();
        while (m.find()) {
            String group = m.group();
            String argKey = group.substring(1, group.length() - 1).trim();
            Object v = args.get(argKey);
            if (v != null) {
                m.appendReplacement(buffer, v.toString());
            }

            if (logger.isDebugEnabled()) {
                logger.debug(argKey + ":" + v);
            }
        }
        m.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * 模板支持如："hello, ${user}"，
     * 参数须以Key-Value形式的Map参数
     *
     * @param template
     * @param args
     * @return
     */
    public static String format$(String template, Map<String, Object> args) {
        if (template == null) {
            return null;
        }
        Matcher m = PATTERN_$.matcher(template);
        StringBuffer buffer = new StringBuffer();
        while (m.find()) {
            String group = m.group();
            String argKey = group.substring(2, group.length() - 1).trim();
            Object v = args.get(argKey);
            if (v != null) {
                String valueString = v.toString().replaceAll(Matcher.quoteReplacement("$"), Matcher.quoteReplacement("\\$"));
                m.appendReplacement(buffer, valueString);
            }

            if (logger.isDebugEnabled()) {
                logger.debug(argKey + ":" + v);
            }
        }
        m.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * 格式化模板
     * <p>
     * 模板支持如："hello, {{user}}"，
     * 参数须以Key-Value形式的Map参数
     * 模板中可支持"."符号
     *
     * @param template
     * @param args
     * @return
     */
    public static String formatTplDouble(String template, Map<String, Object> args) {
        if (template == null) {
            return null;
        }
        Matcher m = PATTERN_TPL_DOUBLE.matcher(template);
        StringBuffer buffer = new StringBuffer();
        while (m.find()) {
            String group = m.group();
            String argKey = group.substring(2, group.length() - 2).trim();
            Object v = args.get(argKey);
            if (v != null) {
                m.appendReplacement(buffer, v.toString());
            }

            if (logger.isDebugEnabled()) {
                logger.debug(argKey + ":" + v);
            }
        }
        m.appendTail(buffer);
        return buffer.toString();
    }

    public static void main(String[] args) {
        String url = "http://als.baidu.com/cb/actionCb?a_type={{ATYPE}}&a_value={{AVALUE}}&s=123&o=123&actType=123&ext_info=T6H2n7uqqtI";
        Map<String, Object> map = new HashMap<>();
        map.put("ATYPE", "active");
        map.put("AVALUE", "0");

        String replace = formatTplDouble(url, map);
        System.out.println(replace);
    }
}
