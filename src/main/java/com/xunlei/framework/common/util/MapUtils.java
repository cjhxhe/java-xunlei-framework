/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class MapUtils {

    // desc
    private static final String MAP_CLASS_KEY = "class";

    static {
    }


    /**
     * 比较要修改的map如果
     *
     * @param source
     * @param mod
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<String, Object> overrideExcludeNull(
            Map<String, Object> source, Map<String, Object> mod) {
        if (source == null || mod == null) {
            return null;
        }
        clearNullEntry(mod);

        Map<String, Object> target = new HashMap(source);
        target.putAll(mod);
        return target;
    }

    /**
     * Remove the class from the hashmap entry
     *
     * @param map
     * @return
     */
    public static <K, V> V clearClassEntry(Map<K, V> map) {
        if (map == null) {
            return null;
        }
        return map.remove(MAP_CLASS_KEY);
    }

    /**
     * remove entry when the value eq null
     *
     * @param map
     */
    public static <K, V> void clearNullEntry(Map<K, V> map) {
        if (map == null) {
            return;
        }
        for (Iterator<Entry<K, V>> ite = map.entrySet().iterator(); ite
                .hasNext(); ) {
            if (ite.next().getValue() == null) {
                ite.remove();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> gmap(Object... args) {
        if (args.length == 1 && args[0] instanceof Map) {
            return (Map<String, Object>) args[0];
        }

        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("Number of parameters is not correct");
        }

        Map<String, Object> targetMap = new HashMap<String, Object>();
        for (int i = 0; i < args.length; i++) {
            targetMap.put(args[i].toString(), args[++i]);
        }

        return targetMap;
    }

    public static Integer getInteger(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        return new Integer(value.toString());
    }

    public static String getString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        return value.toString();

    }

    /**
     * 对象转Map，包含自己类对象和父类对象
     *
     * @param obj 要转的对象
     */
    public static Map<String, String> toMap(Object obj) {
        Map<String, String> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    // 字段值为空，跳过
                    Object valueObj = field.get(obj);
                    if (valueObj == null) {
                        continue;
                    }
                    map.put(field.getName(), valueObj.toString());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            // 递归查询上层
            clazz = clazz.getSuperclass();
        }
        return map;
    }
}
