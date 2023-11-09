package com.xunlei.framework.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 枚举工具类
 */
public class EnumUtils {


    /**
     * 通过枚举的索引获取到枚举类型
     */
    public static <T> T byOrdinal(Integer ordinal, Class<?> clazz) {
        Enum[] values = (Enum[]) clazz.getEnumConstants();
        for (Enum constant : values) {
            if (ordinal == constant.ordinal()) {
                return (T) constant;
            }
        }
        return null;
    }

    /**
     * 通过枚举的名称获取枚举类型
     */
    public static <T> T byName(String name, Class<?> clazz) {
        Enum[] values = (Enum[]) clazz.getEnumConstants();
        for (Enum constant : values) {
            if (constant.name().equalsIgnoreCase(name)) {
                return (T) constant;
            }
        }
        return null;
    }

    /**
     * 根据ID获取枚举类型，匹配的是自定义的ID值
     *
     * @param id    枚举自定义的ID数字
     * @param clazz 实现了IDEnum接口的Class枚举类
     * @param <T>   必须实现 IDEnum 接口
     * @return 如果没有找到，则返回null
     */
    public static <T extends IDEnum> T byId(String id, Class<T> clazz) {
        if (id != null) {
            T[] enums = clazz.getEnumConstants();
            for (T en : enums) {
                if (en.getId() != null && en.getId().equals(id)) {
                    return en;
                }
            }
        }
        return null;
    }

    /**
     * 根据多个ID获取枚举列表
     *
     * @param ids   多个id，以逗号隔开
     * @param clazz 实现了IDEnum接口的Class枚举类
     * @param <T>   必须实现 IDEnum 接口
     * @return 如果没有找到，返回空集合
     */
    public static <T extends IDEnum> List<T> byIds(String ids, Class<T> clazz) {
        if (StringUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<T> typeList = new ArrayList<>();
        List<String> idList = Arrays.asList(StringUtils.split(ids, ","));
        T[] enums = clazz.getEnumConstants();
        for (T en : enums) {
            if (idList.contains(en.getId())) {
                typeList.add(en);
            }
        }
        return typeList;
    }

    /**
     * 将枚举的ID拼成字符串
     *
     * @param enums
     * @param <T>
     * @return
     */
    public static <T extends IDEnum> String joinIds(T[] enums) {
        if (enums == null || enums.length == 0) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        for (T en : enums) {
            ids.add(en.getId());
        }
        return StringUtils.join(ids, ",");
    }

    /**
     * 将枚举字符串转成ID字符串
     *
     * @param namesStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends IDEnum> String toIds(String namesStr, Class<T> clazz) {
        if (StringUtils.isEmpty(namesStr)) {
            return null;
        }
        String[] names = StringUtils.split(namesStr, ",");
        List<String> ids = new ArrayList<>();
        for (String name : names) {
            T t = EnumUtils.byName(name, clazz);
            if (t != null) {
                ids.add(t.getId());
            }
        }
        if (ids.size() == 0) {
            return null;
        }
        return StringUtils.join(ids, ",");

    }

    /**
     * 自定义ID内容的枚举接口
     */
    public interface IDEnum {

        String getId();

        String getDesc();

        default boolean ignore() {
            return false;
        }
    }

}
