package com.xunlei.framework.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.Function;

public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {

    private static final String UNIT = "万仟佰拾亿仟佰拾万仟佰拾元角分";
    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
    private static final double MAX_VALUE = 9999999999999.99D;

    public static long toLong(Long value) {
        if (value == null) {
            return 0;
        }
        return value.longValue();
    }

    public static long toLong(Object value, Long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return Long.parseLong(value.toString());
    }

    public static long toLong(Object value) {
        return NumberUtils.toLong(value, 0L);
    }

    public static double toDouble(Object value, Double defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return Double.parseDouble(value.toString());
    }

    public static double toDouble(Object value) {
        return NumberUtils.toDouble(value, 0D);
    }

    public static int toInt(Integer value) {
        if (value == null) {
            return 0;
        }
        return value.intValue();
    }

    public static int toInt(Object value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value.toString());
    }

    public static int toInt(Object value) {
        return NumberUtils.toInt(value, 0);
    }


    /**
     * 判断是否为不为null的正整数
     */
    public static boolean ifNullNegative(Integer value) {
        return value != null && value > 0;
    }

    /**
     * 将int型数据转换为字符串，若小于0或大于100000000则抛出异常，小于10000，显示全部数字。小于100000000则以万为单位保留两位小数。
     */
    public static String formatBeans(int beans) {
        if (beans < 0) {
            throw new IllegalArgumentException("参数小于0");
        } else if (beans < 10000) {
            return String.valueOf(beans);
        } else if (beans < 100000000) {
            String result = String.valueOf(beans);
            StringBuilder builder = new StringBuilder();
            builder.append(result.substring(0, result.length() - 4));
            if (result.substring(result.length() - 4, result.length() - 2).equals("00")) {
                builder.append("万");
            } else {
                if (result.substring(result.length() - 4, result.length() - 2).endsWith("0")) {
                    builder.append("." + result.substring(result.length() - 4, result.length() - 3) + "万");
                } else {
                    builder.append("." + result.substring(result.length() - 4, result.length() - 2) + "万");
                }
            }
            return builder.toString();
        }
        throw new IllegalArgumentException("参数大于100000000");
    }

    /**
     * 将数字转化成以万为结尾的字符串，
     *
     * @param num
     * @param showPlus 是否在正数前显示 + 号
     * @return
     */
    public static String formatNumber(Long num, boolean showPlus, String end) {
        Long absNum = Math.abs(num);
        if (absNum <= 10000) {
            if (showPlus && num > 0) {
                return "+" + num;
            }
            return String.valueOf(num);
        }
        String result = String.valueOf(absNum);
        StringBuilder builder = new StringBuilder();
        builder.append(result.substring(0, result.length() - 4));
        if (result.substring(result.length() - 4, result.length() - 2).equals("00")) {
            builder.append(end);
        } else {
            if (result.substring(result.length() - 4, result.length() - 2).endsWith("0")) {
                builder.append("." + result.substring(result.length() - 4, result.length() - 3) + end);
            } else {
                builder.append("." + result.substring(result.length() - 4, result.length() - 2) + end);
            }
        }
        if (num < 0) {
            builder.insert(0, "-");
        }
        if (showPlus && num > 0) {
            builder.insert(0, "+");
        }
        return builder.toString();
    }

    /**
     * 格式化数字，最多保留2位小数
     *
     * @param value
     * @return
     */
    public static String format(double value) {
        return new DecimalFormat("0.##").format(value);
    }

    /**
     * 格式化数字，最多保留指定位小数
     *
     * @param value
     * @param precision
     * @return
     */
    public static String format(double value, int precision) {
        String pattern = "0.";
        for (int i = 0; i < precision; i++) {
            pattern += "#";
        }
        return new DecimalFormat(pattern).format(value);
    }

    /**
     * 格式化数字，最多保留指定位小数(不四舍五入)
     *
     * @param value
     * @param precision
     * @return
     */
    public static String formatWithRoundingDown(double value, int precision) {
        String pattern = "0.";
        for (int i = 0; i < precision; i++) {
            pattern += "#";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(value);
    }

    /**
     * 格式化数字，小数位为0保留
     *
     * @param value
     * @return
     */
    public static String formatStyle1(double value) {
        return NumberUtils.formatStyle1(value, 2);
    }

    /**
     * 格式化数字，小数位为0保留
     *
     * @param value
     * @param precision
     * @return
     */
    public static String formatStyle1(double value, int precision) {
        String pattern = "0.";
        for (int i = 0; i < precision; i++) {
            pattern += "0";
        }
        return new DecimalFormat(pattern).format(value);
    }

    /**
     * 格式化数字，不四舍五入
     *
     * @param value
     * @param precision
     * @return
     */
    public static String formatStyle1WithRoundingDown(double value, int precision) {
        String pattern = "0.";
        for (int i = 0; i < precision; i++) {
            pattern += "0";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(value);
    }

    /**
     * 格式化数字，每3位用逗号分割
     */
    public static String formatComma(int value) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(value);
    }

    /**
     * 阿拉伯数字转换中文大写数字
     */
    public static String digitUppercase(double v) {
        if (v < 0 || v > MAX_VALUE) {
            return "参数非法!";
        }
        long l = Math.round(v * 100);
        if (l == 0) {
            return "零元整";
        }
        String strValue = l + "";
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        String rs = "";
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++) {
            char ch = strValue.charAt(i);
            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {
                    rs = rs + UNIT.charAt(j);
                    isZero = false;
                }
            } else {
                if (isZero) {
                    rs = rs + "零";
                    isZero = false;
                }
                rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
            }
        }
        if (!rs.endsWith("分")) {
            rs = rs + "整";
        }
        rs = rs.replaceAll("亿万", "亿");
        return rs;
    }

    /**
     * 长整型乘以指定倍数
     * 可用于经验值计算等等，防止数值精度丢失
     *
     * @param value
     * @param ratio
     * @return
     */
    public static Long multiply(Long value, Double ratio) {
        if (ratio == null) {
            return value;
        }
        BigDecimal result = new BigDecimal(value).multiply(new BigDecimal(ratio));
        return result.setScale(0, RoundingMode.HALF_UP).longValue();
    }

    /**
     * 长整型乘以指定倍数
     * 可用于经验值计算等等，防止数值精度丢失
     *
     * @param value
     * @param ratio
     * @return
     */
    public static Long multiply(Long value, Float ratio) {
        if (ratio == null) {
            return value;
        }
        return NumberUtils.multiply(value, Double.parseDouble(ratio.toString()));
    }


    /**
     * 将Long型数据转换为字符串，绝对值小于10000，显示全部数字
     */
    public static String formatBeansWithUnitW(Long beans) {
        if (beans == null) {
            return "0";
        }
        if (Math.abs(beans) < 10000) {
            return String.valueOf(beans);
        }
        BigDecimal bigDecimal = new BigDecimal(beans);
        // 转换为万元（除以10000）
        BigDecimal decimal = bigDecimal.divide(new BigDecimal("10000"));
        // 保留两位小数
        DecimalFormat format = new DecimalFormat("#.##");
        // 四舍五入
        format.setRoundingMode(RoundingMode.HALF_UP);
        // 格式化完成之后得出结果
        String formatNum = format.format(decimal) + "W";
        return formatNum;
    }

    /**
     * 四舍五入，保留2位小数
     *
     * @param value
     * @return
     */
    public static Double rounding(Double value) {
        return NumberUtils.rounding(value, 2);
    }

    /**
     * 四舍五入，保留指定位数小数
     *
     * @param value
     * @param scale
     * @return
     */
    public static Double rounding(Double value, Integer scale) {
        if (value == null) {
            return null;
        }
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 获取对象指定属性中的Int类型值，如果对象为空或者属性值为空，返回0
     *
     * @param obj
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> int getIntValue(T obj, Function<? super T, ? extends Integer> keyExtractor) {
        return NumberUtils.getValue(obj, 0, keyExtractor);
    }

    /**
     * 获取对象指定属性中的Long类型值，如果对象为空或者属性值为空，返回0
     *
     * @param obj
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> long getLongValue(T obj, Function<? super T, ? extends Long> keyExtractor) {
        return NumberUtils.getValue(obj, 0L, keyExtractor);
    }

    /**
     * 获取对象指定属性中的Double类型值，如果对象为空或者属性值为空，返回0
     *
     * @param obj
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> double getDoubleValue(T obj, Function<? super T, ? extends Double> keyExtractor) {
        return NumberUtils.getValue(obj, 0D, keyExtractor);
    }

    /**
     * 手机号转化为保留前3 后4 其他 * 号表示
     *
     * @param mobile
     * @return
     */
    public static String encryptMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        mobile = mobile.trim();
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 身份证号码保留前4位，后4位 其他 * 号表示
     *
     * @param idCard
     * @return
     */
    public static String encryptIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return null;
        }
        idCard = idCard.trim();
        if (idCard.length() == 18) {
            return idCard.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1****$2");
        }
        if (idCard.length() == 15) {
            return idCard.replaceAll("(\\d{4})\\d{7}(\\d{4})", "$1****$2");
        }
        return null;

    }

    /**
     * 不够位数的在前面补0，保留num的长度位数字
     *
     * @param code 补充的数值
     * @param num  总长度
     * @return
     */
    public static String autoGenericCode(String code, int num) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return String.format("%0" + num + "d", Integer.parseInt(code));
    }


    /**
     * 获取对象指定属性的值
     *
     * @param obj
     * @param defaultValue 默认值
     * @param keyExtractor
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> V getValue(T obj, V defaultValue, Function<? super T, ? extends V> keyExtractor) {
        if (obj == null) {
            return defaultValue;
        }
        V value = keyExtractor.apply(obj);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 加法运算
     *
     * @param m1
     * @param m2
     * @return
     */
    public static double addDouble(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.add(p2).doubleValue();
    }

    /**
     * 减法运算
     *
     * @param m1
     * @param m2
     * @return
     */
    public static double subtractDouble(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.subtract(p2).doubleValue();
    }

    /**
     * 乘法运算
     *
     * @param m1
     * @param m2
     * @return
     */
    public static double multiplyDouble(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.multiply(p2).doubleValue();
    }


    /**
     * 除法运算，保留小数点后scale位小数，超出部分舍弃
     *
     * @param m1
     * @param m2
     * @param scale
     * @return
     */
    public static double divideDouble(double m1, double m2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("精度错误");
        }
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.divide(p2, scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    public static void main(String[] args) {
        Long now = System.currentTimeMillis();
        String currScore = now + "7896";
        String currScoreStr = currScore;
        String currCount = currScoreStr.substring(currScoreStr.length() - 4, currScoreStr.length());
//        System.out.println(currCount);
//        System.out.println(Integer.valueOf(currCount));
    }
}
