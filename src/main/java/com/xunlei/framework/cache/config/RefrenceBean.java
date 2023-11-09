/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.config;

import com.xunlei.framework.cache.annotations.Refrence;
import com.xunlei.framework.common.interfaces.GroupingKey;

public class RefrenceBean implements GroupingKey<Class<?>> {

    private String fieldName;
    private Class<?> target;
    private String mapping;

    public RefrenceBean(String fieldName, Refrence ref) {
        this(fieldName, ref.value(), ref.mapping());
    }

    public RefrenceBean(String fieldName, Class<?> target, String mapping) {
        this.fieldName = fieldName;
        this.target = target;
        this.mapping = mapping;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class<?> getTarget() {
        return target;
    }

    public void setTarget(Class<?> target) {
        this.target = target;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    @Override
    public Class<?> getGroupingKey() {
        return this.target;
    }
}
