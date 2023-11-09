package com.xunlei.framework.common.util;

import java.util.*;

/**
 * 数组工具类
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

    /**
     * 将数组转成集合
     *
     * @param values
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(T[] values) {
        if (values == null) {
            return null;
        }
        if (values.length == 0) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>(values.length);
        for (T ele : values) {
            list.add(ele);
        }
        return list;
    }

    /**
     * 将数组转成Set集合
     *
     * @param values
     * @param <T>
     * @return
     */
    public static <T> Set<T> toSet(T[] values) {
        if (values == null) {
            return null;
        }
        if (values.length == 0) {
            return new HashSet<>();
        }
        Set<T> sets = new HashSet<>(values.length);
        for (T ele : values) {
            sets.add(ele);
        }
        return sets;

    }

    /**
     * 将字符串转成Integer数组
     *
     * @param value 多个值以逗号隔开
     * @return
     */
    public static Integer[] toIntArray(String value) {
        return ArrayUtils.toIntArray(value, ",");
    }

    /**
     * 将字符串转成Integer数组
     *
     * @param value          字符串
     * @param separatorChars 字符串分隔符
     * @return
     */
    public static Integer[] toIntArray(String value, String separatorChars) {
        if (StringUtils.isEmpty(value)) {
            return new Integer[0];
        }
        int index = 0;
        String[] values = StringUtils.split(value, separatorChars);
        Integer[] result = new Integer[values.length];
        for (String str : values) {
            result[index++] = StringUtils.isEmpty(str) ? null : new Integer(str);
        }
        return result;
    }

    /**
     * 将字符串转成Long数组
     *
     * @param value
     * @return
     */
    public static Long[] toLongArray(String value) {
        return ArrayUtils.toLongArray(value, ",");
    }

    /**
     * 将字符串转成Long数组
     *
     * @param value          字符串
     * @param separatorChars 字符串分隔符
     * @return
     */
    public static Long[] toLongArray(String value, String separatorChars) {
        if (StringUtils.isEmpty(value)) {
            return new Long[0];
        }
        int index = 0;
        String[] values = StringUtils.split(value, separatorChars);
        Long[] result = new Long[values.length];
        for (String str : values) {
            result[index++] = StringUtils.isEmpty(str) ? null : new Long(str);
        }
        return result;
    }

    /**
     * 将集合转成Long数组
     *
     * @param values
     * @return
     */
    public static Long[] toLongArray(Collection<?> values) {
        if (ListUtils.isEmpty(values)) {
            return null;
        }
        Long[] array = new Long[values.size()];
        int i = 0;
        Object value;
        for (Iterator<?> it = values.iterator(); it.hasNext(); ) {
            value = it.next();
            array[i++] = value == null ? null : new Long(value.toString());
        }
        return array;
    }

    /**
     * 将集合转成Integer数组
     *
     * @param values
     * @return
     */
    public static Integer[] toIntArray(Collection<?> values) {
        if (ListUtils.isEmpty(values)) {
            return null;
        }
        Integer[] array = new Integer[values.size()];
        int i = 0;
        Object value;
        for (Iterator<?> it = values.iterator(); it.hasNext(); ) {
            value = it.next();
            array[i++] = value == null ? null : new Integer(value.toString());
        }
        return array;
    }

    /**
     * 将字符串数组转成Integer数组
     *
     * @param values
     * @return
     */
    public static Integer[] toIntArray(String[] values) {
        if (ArrayUtils.isEmpty(values)) {
            return null;
        }
        Integer[] array = new Integer[values.length];
        int i = 0;
        for (String value : values) {
            array[i++] = value == null ? null : Integer.parseInt(value);
        }
        return array;
    }

    /**
     * 返回升序数组的第一个比value大的值
     */
    public static Integer getNext(Integer[] values, Integer value) {
        for (Integer integer : values) {
            if (integer > value) {
                return integer;
            }
        }
        return value;
    }
}
