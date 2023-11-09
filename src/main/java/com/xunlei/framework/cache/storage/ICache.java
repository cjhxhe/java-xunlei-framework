/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.storage;

import com.xunlei.framework.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Cache interface definition
 */
public interface ICache {

    static Logger logger = LoggerFactory.getLogger(ICache.class);

    boolean set(String cacheKey, String value)
            throws CacheException;

    boolean setex(String cacheKey, int seconds, String value)
            throws CacheException;

    boolean mod(String cacheKey, String value)
            throws CacheException;

    Long del(String cacheKey) throws CacheException;

    String get(String cacheKey) throws CacheException;

    List<String> gets(String... cacheKey) throws CacheException;

    boolean sets(String... keyvalues) throws CacheException;

    Long addMembers(String groupKey, String... entityCacheKeys)
            throws CacheException;

    Long delMembers(String groupKey, String... entityCacheKeys)
            throws CacheException;

    Set<String> getMembers(String groupKey) throws CacheException;

    /**
     * Gets the current cache object length
     *
     * @return
     */
    int getSize();

    /**
     * 失效时间设置
     *
     * @param key
     * @param seconds 毫秒
     * @return
     * @throws CacheException
     */
    Long expire(String key, Integer seconds) throws CacheException;

}
