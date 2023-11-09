/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache;

/**
 * 缓存异常定义
 */
@SuppressWarnings("serial")
public class CacheException extends Exception {

    public static CacheException throwe(String msg)
            throws CacheException {
        throw new CacheException(msg);
    }

    public static CacheException throwe(String msg, Throwable e)
            throws CacheException {
        throw new CacheException(msg, e);
    }

    public CacheException() {
        super();
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable e) {
        super(message, e);
    }

    public CacheException(Throwable e) {
        super(e);
    }
}
