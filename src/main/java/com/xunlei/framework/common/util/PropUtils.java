package com.xunlei.framework.common.util;

import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropUtils {

    public static String getString(Properties props, String key) {
        Object value = props.get(key);
        if (value == null) {
            return null;
        }
        return StringUtils.trim(value.toString());
    }

    public static int getInt(Properties props, String key) {
        return NumberUtils.toInt(getString(props, key));
    }

    public static long getLong(Properties props, String key) {
        return NumberUtils.toLong(getString(props, key));
    }

    public static boolean getBoolean(Properties props, String key) {
        return "true".equals(getString(props, key));
    }

    public static <T> T getEnum(Properties props, String key, Class<T> clazz) {
        String value = PropUtils.getString(props, key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        T[] types = clazz.getEnumConstants();
        for (T type : types) {
            if (type.toString().equals(value.toUpperCase())) {
                return type;
            }
        }
        return null;
    }

    public static String getString(Properties props, String prefix, String key) {
        if (props.containsKey(prefix + key)) {
            key = prefix + key;
        }
        return PropUtils.getString(props, key);
    }

    public static int getInt(Properties props, String prefix, String key) {
        if (props.containsKey(prefix + key)) {
            key = prefix + key;
        }
        return PropUtils.getInt(props, key);
    }

    public static long getLong(Properties props, String prefix, String key) {
        if (props.containsKey(prefix + key)) {
            key = prefix + key;
        }
        return PropUtils.getLong(props, key);
    }

    public static boolean getBoolean(Properties props, String prefix, String key) {
        if (props.containsKey(prefix + key)) {
            key = prefix + key;
        }
        return PropUtils.getBoolean(props, key);
    }

    /**
     * 加载多个本地配置文件，后面的覆盖前面的
     *
     * @param sources
     * @return 返回合并之后的属性对象
     */
    public static Properties loadProperties(String... sources) {
        Properties pros = new Properties();
        try {
            ResourcePropertySource rps;
            for (String source : sources) {
                rps = new ResourcePropertySource(source);
                pros.putAll(rps.getSource());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pros;
    }

    /**
     * 返回  小对象相对大对象的改变属性
     *
     * @param soure  小对象
     * @param target 大对象
     * @return
     */
    public static List<String> getChangedProp(Map<String, Object> soure, Map<String, Object> target) {
        List<String> list = new ArrayList<>();
        for (String str : soure.keySet()) {
            if (!(null != soure.get(str) && soure.get(str).equals(target.get(str)))) {
                list.add(str);
            }
        }
        return list;
    }
}
