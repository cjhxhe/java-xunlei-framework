/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.impl;

import com.xunlei.framework.cache.CacheException;
import com.xunlei.framework.cache.PropertiesMerging;
import com.xunlei.framework.common.util.CglibUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认实现的规则，如果Map中存在同名的属性则会Override上一次赋值的属性
 */
public class OverridePropertiesMerging implements PropertiesMerging {

    @Override
    public Object merge(Object dto, List<Map<String, Object>> props) throws CacheException {
        Map<String, Object> properties = new HashMap<String, Object>();
        for (Map<String, Object> map : props)
            properties.putAll(map);

        try {
            CglibUtils.populate(properties, dto);
        } catch (Exception e) {
            throw new CacheException("Unable to merge the attribute value", e);
        }
        return dto;
    }
}
