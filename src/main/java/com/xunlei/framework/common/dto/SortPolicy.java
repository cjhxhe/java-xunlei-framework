package com.xunlei.framework.common.dto;

import com.xunlei.framework.common.enums.SortType;

/**
 * 排序策略定义
 */
public class SortPolicy {

    private String fieldName;

    private SortType sortType;

    public SortPolicy(String fieldName, SortType sortType) {
        this.fieldName = fieldName;
        this.sortType = sortType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public SortType getSortType() {
        return sortType;
    }
}
