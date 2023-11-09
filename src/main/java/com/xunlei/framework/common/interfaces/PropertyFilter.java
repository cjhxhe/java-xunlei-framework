/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.common.interfaces;

/**
 * 属性过滤器接口定义
 */
public interface PropertyFilter<T> {
    boolean isFilter(T t);
}
