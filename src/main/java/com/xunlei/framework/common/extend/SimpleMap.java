package com.xunlei.framework.common.extend;

import com.alibaba.fastjson.JSONObject;
import com.xunlei.framework.common.util.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 对Map二次封装，方便使用
 */
public class SimpleMap implements Map<String, Object>, Cloneable, Serializable {

    private Map<String, Object> map;

    public SimpleMap() {
        this.map = new HashMap<>();
    }

    public static SimpleMap create(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof JSONObject) {
            SimpleMap data = new SimpleMap();
            data.putAll((JSONObject) object);
            return data;
        }
        if (object instanceof Map) {
            SimpleMap data = new SimpleMap();
            data.putAll((Map) object);
            return data;
        }
        throw new IllegalArgumentException("Parameter type not support，only support Map or JSONObject");
    }

    public SimpleMap(boolean ordered) {
        this(16, ordered);
    }

    public SimpleMap(int initialCapacity) {
        this(initialCapacity, false);
    }

    public SimpleMap(int initialCapacity, boolean ordered) {
        if (ordered) {
            this.map = new LinkedHashMap(initialCapacity);
        } else {
            this.map = new HashMap(initialCapacity);
        }
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.map.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return this.map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }

    public String getString(Object key) {
        Object value = get(key);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public int getIntValue(Object key) {
        Integer value = getInteger(key);
        return value == null ? 0 : value;
    }

    public Integer getInteger(Object key) {
        String value = getString(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return new Integer(value);
    }

    public Long getLongValue(Object key) {
        Long value = getLong(key);
        return value == null ? 0L : value;
    }

    public Long getLong(Object key) {
        String value = getString(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return new Long(value);
    }

    public double getDoubleValue(Object key) {
        Double value = getDouble(key);
        return value == null ? 0D : value;
    }

    public Double getDouble(Object key) {
        String value = getString(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return new Double(value);
    }

    public boolean getBooleanValue(Object key) {
        Boolean value = getBoolean(key);
        return value == null ? false : value;
    }

    public Boolean getBoolean(Object key) {
        String value = getString(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return "T".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value);
    }

    public SimpleMap getSimpleMap(Object key) {
        Map<String, Object> data = (Map<String, Object>) this.map.get(key);
        if (data == null) {
            return null;
        }
        return SimpleMap.create(data);
    }
}
