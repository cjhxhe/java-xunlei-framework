package com.xunlei.framework.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Auther: mq
 * @Date: 2019/5/22 15:14
 */
public class PinYinUtils {
    private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

    static {
        // 格式化为小写字母
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 不需要音调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // 设置对拼音字符 ü 的处理
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    public static String toPinyin(String chinese, PinyinType type) {
        if (chinese == null || chinese.isEmpty()) {
            return "";
        } else {
            StringBuilder pybf = new StringBuilder();

            // 英文字母不需要转换
            char[] chars = chinese.trim().toCharArray();

            String[] str = null;
            for (char ch : chars) {
                // 汉字的编码是两个字节
                if (ch <= 128) {
                    // 英文字母或者特殊字符
                    pybf.append(toUpperCaseAscii(ch));
                    continue;
                }

                try {
                    str = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                    // 不是汉字，估计是特殊字符
                    if (str == null || str.length == 0) {
                        pybf.append("0");
                        continue;
                    }

                    // 多音字只要第一个读音
                    String value = str[0];
                    Object obj = PinyinType.HEAD.equals(type) ? value.charAt(0) : value;
                    pybf.append(obj);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    // 出现格式化异常，则直接添加原字符
                    pybf.append(ch);
                }
            }
            return pybf.toString();
        }
    }

    /**
     * 汉字转化为驼峰 eg: 就聊 -> JiuLiao
     *
     * @param src
     * @return
     */
    public static String toHumpPinyin(String src) {
        char[] charArray = src.toCharArray();
        // 单个字的多个音组成的数组
        String[] multiPinYinArray;
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder result = new StringBuilder();
        try {
            for (char c : charArray) {
                //判断是否为汉字字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    multiPinYinArray =
                            PinyinHelper.toHanyuPinyinStringArray(c, format);
                    String word = multiPinYinArray[0];
                    result.append(word.substring(0, 1).toUpperCase())
                            .append(word.substring(1));
                } else {
                    result.append(c);
                }
            }
            return result.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 将字符中的小写英文字母转为大写
     *
     * @param ch 字符
     */
    private static char toUpperCaseAscii(char ch) {
        if (ch >= 97 && ch <= 122) {
            return (char) (ch - 32);
        } else {
            return ch;
        }
    }

    public static void main(String[] args) {
        System.out.println(toHumpPinyin("就聊聊"));
    }

    public enum PinyinType {
        FULL, HEAD
    }

}
