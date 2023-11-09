/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache;

/**
 * 实现该接口完成数据持久化操作
 */
public interface Delegater<T> {

    T execute() throws DataProxyException;
}
