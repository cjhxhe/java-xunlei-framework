/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.config;

import com.xunlei.framework.cache.CacheLevel;
import com.xunlei.framework.cache.KeyBuilder;

import java.util.List;
import java.util.Map;

public class CacheBean extends Basic {

    private Map<Class<?>, MergingBean> mergings;

    private List<KeyPropertyBean> keyPropertyList;

    private Map<String, List<GroupBean>> groups;

    private List<ModelField> fields;

    private boolean miniTable = Boolean.FALSE;

    // 失效时间
    private int expire;

    public CacheBean() {
        super();
    }

    public CacheBean(Class<?> xbean, String prefix,
                     Class<? extends KeyBuilder> keyBuilder, CacheLevel level, boolean miniTable) {
        super(xbean, prefix, keyBuilder, level);
        this.miniTable = miniTable;
    }

    public Map<Class<?>, MergingBean> getMergings() {
        return mergings;
    }

    public void setMergings(Map<Class<?>, MergingBean> mergings) {
        this.mergings = mergings;
    }

    public List<KeyPropertyBean> getKeyPropertyList() {
        return keyPropertyList;
    }

    public void setKeyPropertyList(List<KeyPropertyBean> keyPropertyList) {
        this.keyPropertyList = keyPropertyList;
    }

    public Map<String, List<GroupBean>> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, List<GroupBean>> groups) {
        this.groups = groups;
    }

    public List<ModelField> getFields() {
        return fields;
    }

    public void setFields(List<ModelField> fields) {
        this.fields = fields;
    }

    public boolean isMiniTable() {
        return miniTable;
    }

    public void setMiniTable(boolean miniTable) {
        this.miniTable = miniTable;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

}
