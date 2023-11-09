package com.xunlei.framework.common.util;

/**
 * 反射工具类
 */
public class ReflectUtils {

    /**
     * 反射创建实例对象
     */
    public static <T> T newInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (StringUtils.isBlank(className)) {
            return null;
        }
        return (T) Class.forName(className).newInstance();
    }

    public static String getGetterMethodName(String fieldName) {
        return ReflectUtils.getMethodName("get", fieldName);
    }

    public static String getSetterMethodName(String fieldName) {
        return ReflectUtils.getMethodName("set", fieldName);
    }

    private static String getMethodName(String prefix, String fieldName) {
        return prefix + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }
}
