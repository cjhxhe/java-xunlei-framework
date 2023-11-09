/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xunlei.framework.common.extend.SimpleMap;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JSON 工具类
 */
public final class JsonUtils {

    final static SerializerFeature[] STANDARD_FEATURES = new SerializerFeature[]{
            SerializerFeature.DisableCircularReferenceDetect
    };

    private static final FastJsonUtil FJU = new FastJsonUtil();

    /**
     * TODO 目前因为fastjson丢复杂的json时，使用了"$ref"无法正常解析，因此这里禁用了这个优化
     * 如果fastjson修复了，则应该立即启用
     *
     * @param object
     * @return
     */
    public static String seriazileAsString(Object object) {
        return FJU.seriazileAsString(object, STANDARD_FEATURES);
    }

    /**
     * 标准的序列化接口
     */
    public static String seriazileAsStringWithStandard(Object object) {
        return FJU.seriazileAsString(object, STANDARD_FEATURES);
    }

    public static <T> T deserializeAsObject(String jsonString, Type clazz) {
        return FJU.deserializeAsObject(jsonString, clazz);
    }

    public static List deserializeAsList(String jsonString, Class clazz) {
        return FJU.deserializeAsList(jsonString, clazz);
    }

    /**
     * 将一个对象，转换成对象
     *
     * @param obj 只能是Object或者Map，不能是数组类型
     * @return
     */
    public static Map<String, Object> toJsonMap(Object obj) {
        if (obj == null)
            return null;
        String objString = JSON.toJSONString(obj);
        return JSON.parseObject(objString, getType());
    }

    /**
     * 将一个对象转成SimpleMap
     *
     * @param obj
     * @return
     */
    public static SimpleMap toSimpleMap(Object obj) {
        Map<String, Object> map = JsonUtils.toJsonMap(obj);
        if (map == null) {
            return null;
        }
        return SimpleMap.create(map);
    }

    /**
     * 将JSON字符串转成SimpleMap
     *
     * @param jsonString
     * @return
     */
    public static SimpleMap toSimpleMap(String jsonString) {
        Map<String, Object> map = JsonUtils.toJsonMap(jsonString);
        if (map == null) {
            return null;
        }
        return SimpleMap.create(map);
    }

    /**
     * 判断字符串是否为json格式
     *
     * @param s
     * @return
     */
    public static boolean isJson(String s) {
        try {
            JSON.parse(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Map<String, Object> toJsonMap(String s) {
        if (s == null)
            return null;
        return JSON.parseObject(s, getType());
    }

    public static String getJsonString(String jsonStr, String key) {
        String regex = "\"" + key + "\":\"(.*?)\\\"";
        return JsonUtils.getJsonValue(jsonStr, Pattern.compile(regex));
    }

    public static Integer getJsonInteger(String jsonStr, String key) {
        String value = getJsonNumber(jsonStr, key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Integer.parseInt(value);
    }

    public static Long getJsonLong(String jsonStr, String key) {
        String value = getJsonNumber(jsonStr, key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Long.parseLong(value);
    }

    public static Double getJsonDouble(String jsonStr, String key) {
        String value = getJsonNumber(jsonStr, key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Double.parseDouble(value);
    }

    public static String getJsonValue(String jsonStr, Pattern pattern) {
        Matcher matcher = pattern.matcher(jsonStr.replace(": ", ":"));
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String getJsonNumber(String jsonStr, String key) {
        String regex = "\"" + key + "\":([0-9.]+)";
        return JsonUtils.getJsonValue(jsonStr, Pattern.compile(regex));
    }

    private static Type getType() {
        return new TypeReference<Map<String, Object>>() {
        }.getType();
    }


    /**
     * 扩展fastjson序列化
     */
    private static class FastJsonUtil {

        /**
         * java-object as json-string
         *
         * @param object
         * @return
         */
        public String seriazileAsString(Object object, SerializerFeature... features) {
            if (object == null) {
                return "";
            }
            try {
                return JSON.toJSONString(object, features);
            } catch (Exception ex) {
                throw new RuntimeException("Could not write JSON: "
                        + ex.getMessage(), ex);
            }
        }

        /**
         * json-string to java-object
         *
         * @param jsonString
         * @return
         */
        public <T> T deserializeAsObject(String jsonString, Type clazz) {
            if (jsonString == null || clazz == null) {
                return null;
            }
            try {
                return JSON.parseObject(jsonString, clazz);
            } catch (Exception ex) {
                throw new RuntimeException("Could not write JSON: "
                        + ex.getMessage(), ex);
            }
        }

        /**
         * json-string to java-list
         *
         * @param jsonString
         * @return
         */
        public List<?> deserializeAsList(String jsonString, Class<?> clazz) {
            if (jsonString == null || clazz == null) {
                return null;
            }
            try {
                return JSONArray.parseArray(jsonString, clazz);
            } catch (Exception ex) {
                throw new RuntimeException("Could not write JSON: "
                        + ex.getMessage(), ex);
            }
        }

    }
}
