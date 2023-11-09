package com.xunlei.framework.common.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 通用正则验证工具类：邮箱，电话，IP 等。。。
 *
 * @author luoqh
 */

public class ValidateUtils {

    /**
     * 分割字符串正则表达式
     */
    private static Pattern SPLIT_STRING_PATTERN = Pattern.compile("^([0-9]+)|(([0-9]+\\,)*[0-9]+)$");
    /**
     * 获取URL 的正则表达式
     */
    private static Pattern URL = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.]){1,2}[A-Za-z\\d]{2,5}$";
        return Pattern.matches(regex, email);
    }

    /**
     * 1、将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 ；
     * 2、将这17位数字和系数相乘的结果相加；
     * 3、用加出来和除以11，看余数是多少；
     * 4、余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2；
     * 5、通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的X。如果余数是10，身份证的最后一位号码就是2；
     * 验证身份证号码(只考虑第二代身份证)
     *
     * @param idCard 居民身份证号码18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "^[1-9]\\d{5}(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])\\d{3}[0-9Xx]{1}$";
        if (Pattern.matches(regex, idCard)) {
            int[] ratio = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};//前17位对应得系数
            int sum = 0;
            for (int i = 0; i < ratio.length; i++) {
                int c = Integer.valueOf(String.valueOf(idCard.charAt(i)));
                sum += ratio[i] * c;
            }
            int remainder = sum % 11;//余数
            //余数0-10分别对应最后一位数字{1, 0, X, 9, 8, 7, 6, 5, 4, 3, 2}先用10代替X
            int[] lastNums = new int[]{1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};
            String lastNum = lastNums[remainder] == 10 ? "X" : lastNums[remainder] + "";
            if (lastNum.equalsIgnoreCase(String.valueOf(idCard.charAt(idCard.length() - 1)))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 由于考虑到可能会开放新的号段，此处只验证数字以及位数，1开头
     *
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "^1[0-9]{10}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *              数字之后是空格分隔的国家（地区）代码。</p>
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex, chinese);
    }


    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * <pre>
     * 获取网址 URL 的一级域
     * </pre>
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        // 获取完整的域名
        // Pattern p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = URL.matcher(url);
        matcher.find();
        return matcher.group();
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }

    /**
     * 比较两个数的误差是否符合标准
     *
     * @param num1         第一个数字
     * @param num2         第二个数字
     * @param allowedError 允许的误差
     * @return
     */
    public static boolean checkDiffBetweenTwoNum(long num1, long num2, long allowedError) {
        long error = Math.abs(num1 - num2);
        if (error <= allowedError) {
            return true;
        }
        return false;
    }


    /**
     * 验证密码格式（数字与字母的组合8-20位）
     *
     * @param password
     */
    public static boolean checkPassword(String password) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
        return Pattern.matches(regex, password);
    }

    /**
     * 验证是否为正确的版本号
     *
     * @param version
     * @return
     */
    public static boolean checkVersion(String version) {
        if (StringUtils.isEmpty(version)) {
            return true;
        }
        String regex = "^[0-9]+\\.[0-9]+\\.[0-9]+$";
        return Pattern.matches(regex, version);
    }

    /**
     * 是否为分割字符串
     *
     * @param value
     * @return
     */
    public static boolean isSplitString(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return SPLIT_STRING_PATTERN.matcher(value).matches();
    }


    /**
     * 测试校验结果
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(ValidateUtils.isSplitString("22,33,454,1231,12234,"));
    }
}
