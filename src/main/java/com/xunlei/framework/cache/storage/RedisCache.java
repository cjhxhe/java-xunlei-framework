/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.storage;

import com.xunlei.framework.cache.CacheException;
import com.xunlei.framework.support.redis.RedisTemplate;

import java.util.List;
import java.util.Set;

/**
 * Redis Cache Local Api
 */
public class RedisCache implements ICache {

    private RedisTemplate redisTemplate;

    enum Response {OK}

    public RedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean set(String cacheKey, String value)
            throws CacheException {
        String res = redisTemplate.set(cacheKey, value);
        if (Response.valueOf(res) == Response.OK) {
            return true;
        }
        logger.warn(res);
        return false;
    }

    public boolean setex(String cacheKey, int seconds, String value)
            throws CacheException {
        String res = redisTemplate.set(cacheKey, seconds, value);
        if (Response.valueOf(res) == Response.OK) {
            return true;
        }
        logger.warn(res);
        return false;
    }

    @Override
    public List<String> gets(String... cacheKey) throws CacheException {
        return redisTemplate.gets(cacheKey);
    }

    @Override
    public boolean sets(String... keyvalues) throws CacheException {
        redisTemplate.sets(keyvalues);
        return true;
    }

    @Override
    public boolean mod(String cacheKey, String value)
            throws CacheException {
        return set(cacheKey, value);
    }

    @Override
    public Long del(String cacheKey) throws CacheException {
        return redisTemplate.del(cacheKey);
    }

    @Override
    public String get(String cacheKey) throws CacheException {
        return redisTemplate.get(cacheKey);
    }

    @Override
    public Long addMembers(String groupKey, String... entityCacheKeys)
            throws CacheException {
        return redisTemplate.sadd(groupKey, entityCacheKeys);
    }

    @Override
    public Long delMembers(String groupKey, String... entityCacheKeys)
            throws CacheException {
        return redisTemplate.srem(groupKey, entityCacheKeys);
    }

    @Override
    public Set<String> getMembers(String groupKey) throws CacheException {
        return redisTemplate.smembers(groupKey);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Long expire(String key, Integer seconds) throws CacheException {
        return redisTemplate.expire(key, seconds);
    }
}
