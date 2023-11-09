package com.xunlei.framework.common.util;

import com.xunlei.framework.rule.RuleException;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final String RAND_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    /**
     * 个位
     */
    private static final String[] BIT_CHINESE = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    /**
     * 非个位
     */
    private static final String[] NOT_BIT_CHINESE = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
    /**
     * 字母或空格正则表达式
     */
    private static String REG_LETTER_SPACE = "^[a-zA-Z ]+$";

    public static String getUUID() {
        return UUID.randomUUID().toString().toLowerCase().replace("-", "");
    }

    public static String decode(String str, String enc) {
        try {
            return URLDecoder.decode(str, enc);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String decode(String src) {
        return StringUtils.decode(src, "utf-8");
    }

    public static String encode(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        try {
            str = URLEncoder.encode(str, "utf-8");
            return str.replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取URL参数对应的值Integer类型
     *
     * @param urlParams url地址参数：如http://www.baidu.com?a=123&c=123或者d=123&3=11
     * @param name      参数名称
     * @return Integer
     */
    public static Integer getQueryInteger(String urlParams, String name) {
        String res = getQueryString(urlParams, name);
        return isNotBlank(res) ? Integer.parseInt(res) : null;
    }

    /**
     * 获取URL参数对应的值Double类型
     *
     * @param urlParams url地址参数：如http://www.baidu.com?a=123&c=123或者d=123&3=11
     * @param name      参数名称
     * @return Double
     */
    public static Double getQueryDouble(String urlParams, String name) {
        String res = getQueryString(urlParams, name);
        return isNotBlank(res) ? Double.parseDouble(res) : null;
    }

    /**
     * 获取URL参数对应的值
     *
     * @param urlParams url地址参数：如http://www.baidu.com?a=123&c=123或者d=123&3=11
     * @param name      参数名称
     * @return
     */
    public static String getQueryString(String urlParams, String name) {
        Pattern pattern = Pattern.compile("(^|&|\\?)" + name + "=([^&]*)(&|$)");
        return StringUtils.getQueryString(urlParams, pattern);
    }

    /**
     * 获取URL参数对应的值
     *
     * @param urlParams url地址参数：如http://www.baidu.com?a=123&c=123或者d=123&3=11
     * @param pattern   匹配正则
     * @return
     */
    public static String getQueryString(String urlParams, Pattern pattern) {
        Matcher matcher = pattern.matcher(urlParams);
        if (matcher.find()) {
            String[] data = matcher.group().replace("&", "").split("=");
            if (data.length > 1) {
                return data[1];
            }
        }
        return null;
    }

    /**
     * 插入字符串，如果原始字符串为空则返回空字符串
     *
     * @param source    原始字符串
     * @param searchStr 在哪个字符前插入
     * @param insertStr 插入字符串
     * @return
     */
    public static String insertLastOf(String source, String searchStr, String insertStr) {
        if (isBlank(source)) {
            return source;
        }
        StringBuilder sb = new StringBuilder(source);
        int index = sb.lastIndexOf(searchStr);
        if (index == -1) {
            return source;
        }
        return sb.insert(index, insertStr).toString();
    }

    public static String trimToNull(Object value) {
        if (value == null) {
            return null;
        }
        return StringUtils.trimToNull(value.toString());
    }

    /**
     * 构建url参数
     */
    public static String buildUrlParam(Map<String, Object> params) {
        StringBuilder param = new StringBuilder("?");

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                param.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        String res = param.toString();
        if (res.endsWith("&")) {
            res = substringBeforeLast(res, "&");
        }
        return res;
    }

    /**
     * 构建url参数
     */
    public static String buildUrlParam(Object... kvArgs) {
        return buildUrlParam(MapUtils.gmap(kvArgs));
    }

    /**
     * 解析url参数
     */
    public static Map<String, Object> parseUrlParam(String url) {
        if (isBlank(url)) {
            return null;
        }
        String paramStr = substringAfterLast(url, "?");
        String[] paramArr = paramStr.split("&");

        Map<String, Object> param = new HashMap<>();
        for (String s : paramArr) {
            param.put(substringBeforeLast(s, "="), substringAfterLast(s, "="));
        }

        return param;
    }

    public static String appendElement(String arrayStr, String element) {
        return StringUtils.appendElement(arrayStr, element, ",");
    }

    public static String appendElement(String arrayStr, String element, String separatorChars) {
        if (StringUtils.isEmpty(arrayStr)) {
            return element;
        }
        return arrayStr + separatorChars + element;
    }

    public static String removeElement(String arrayStr, String element) {
        return StringUtils.removeElement(arrayStr, element, ",");
    }

    public static String removeElement(String arrayStr, String element, String separatorChars) {
        if (StringUtils.isEmpty(arrayStr)) {
            return null;
        }
        String[] values = StringUtils.split(arrayStr, separatorChars);
        values = ArrayUtils.removeElement(values, element);
        return StringUtils.join(values, separatorChars);
    }

    /**
     * 判断字符是否为中文
     *
     * @param ch
     * @return
     */
    public static boolean isChineseChar(char ch) {
        try {
            return String.valueOf(ch).getBytes("GBK").length > 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否为字母或者空格
     *
     * @param str
     * @return
     */
    public static boolean isLetterSpace(String str) {
        return str.matches(REG_LETTER_SPACE);
    }

    /**
     * 按字节截取字符串
     *
     * @param value 字符串
     * @param count 字节数
     * @return
     */
    public static String substrByByte(String value, Integer count) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        char[] values = value.toCharArray();
        StringBuffer data = new StringBuffer();
        int i = 0;
        for (char ch : values) {
            if (StringUtils.isChineseChar(ch)) {
                i += 2;
            } else {
                i += 1;
            }
            if (i > count) {
                break;
            }
            data.append(ch);
        }
        return data.toString();
    }

    /**
     * 根据代码点截取字符串
     * <p>
     * 常用于截取包含表情或未知的字符串
     *
     * @param str
     * @param end
     * @return
     */
    public static String substringByCodePoints(String str, int end) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        if (str.length() <= end) {
            return str;
        }
        String result = null;
        do {
            try {
                result = str.substring(0, str.offsetByCodePoints(0, end));
            } catch (IndexOutOfBoundsException e) {
                end--;
            }
        } while (result == null);
        return result;
    }


    /**
     * 生成指定位数的随机字符串
     *
     * @param count
     * @return
     */
    public static String generateRandomStr(Integer count) {
        StringBuffer buff = new StringBuffer();
        int len = RAND_STR.length();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            buff.append(RAND_STR.charAt(random.nextInt(len)));
        }
        return buff.toString();
    }

    /**
     * 判断分隔字符串是否包含指定内容
     *
     * @param source 按指定字符分隔的字符串，例如：AA,BB,CC
     * @param target 包含的内容，例如：BB
     * @return
     */
    public static boolean containsTarget(String source, String target) {
        return StringUtils.containsTarget(source, target, ",");
    }

    /**
     * 判断分隔字符串是否包含指定内容
     *
     * @param source         按指定字符分隔的字符串，例如：AA,BB,CC
     * @param target         包含的内容，例如：BB
     * @param separatorChars 字符串分隔符
     * @return
     */
    public static boolean containsTarget(String source, String target, String separatorChars) {
        if (target == null) {
            return false;
        }
        source = separatorChars + source + separatorChars;
        return source.contains(separatorChars + target + separatorChars);
    }

    public static String toStr(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    /**
     * 将字符串首字母转成小写
     *
     * @param str
     * @return
     */
    public static String firstLowerCase(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 将非负整数转为中文
     *
     * @param param Integer >= 0
     * @return
     */
    public static String toChinese(Integer param) {
        if (param == null || param < 0) {
            throw new RuleException("参数不合法");
        }
        if (param == 0) {
            return "零";
        }
        String paramStr = param.toString();

        String result = "";
        int n = paramStr.length();
        for (int i = 0; i < n; i++) {

            int num = paramStr.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += BIT_CHINESE[num] + NOT_BIT_CHINESE[n - 2 - i];
            } else {
                result += BIT_CHINESE[num];
            }
        }
        // 去除末尾多余 零
        while (true) {
            Integer index = result.lastIndexOf("零");
            if (index == result.length() - 1) {
                result = result.substring(0, result.length() - 2);
                continue;
            }
            break;
        }
        return result;
    }

    /**
     * URL安全的Base64编码
     *
     * @param content
     * @return
     */
    public static byte[] encodeBase64URLSafe(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            return Base64.encodeBase64URLSafe(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * URL安全的Base64编码
     *
     * @param content
     * @return
     */
    public static String encodeBase64URLSafeString(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            return Base64.encodeBase64URLSafeString(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 内容缩略显示
     *
     * @param content 需要缩略显示的内容
     * @param count   保留字符个数
     * @return
     */
    public static String overflowString(String content, int count) {
        String result = StringUtils.substringByCodePoints(content, count);
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        if (result.equals(content)) {
            return result;
        }
        return result.substring(0, count - 1) + "…";
    }

    public static void main(String[] args) {
//        System.out.println(StringUtils.firstLowerCase("ActionAwardUser"));
        String str = "贝贝麻麻\uD83C\uDF34 敏敏\uD83C\uDF3A";
        System.out.println(StringUtils.substringByCodePoints(str, 10));
        System.out.println(str.substring(0, 10));
        System.out.println(overflowString("这是五个字啊", 5));
    }

}
