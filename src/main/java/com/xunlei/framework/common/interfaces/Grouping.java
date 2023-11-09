/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.common.interfaces;


import com.xunlei.framework.common.util.ListUtils;

/**
 * 分组接口定义，
 * <p>
 * {@link ListUtils#grouping(java.util.List)}
 */
public interface Grouping<K, T> {


    public K getGroupingKey(T element);

}
