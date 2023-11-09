package com.xunlei.framework.common.dto;

import com.xunlei.framework.common.enums.SortType;

/**
 * 自定义排序
 *
 * @param <T>
 */
public interface SortFast<T> {

    /**
     * 排序属性字段名
     */
    String[] sortField();

    /**
     * 排序属性升序和降序值，返回的长度应该和排序属性名一致
     */
    SortType[] sortType();

    /**
     * 排序字段值
     */
    Object sortValue(String fieldName, T bean);
}
