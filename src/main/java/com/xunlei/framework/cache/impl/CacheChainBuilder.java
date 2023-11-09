/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.impl;

import com.xunlei.framework.cache.CacheLevel;
import com.xunlei.framework.cache.config.CacheBean;
import com.xunlei.framework.cache.storage.*;
import com.xunlei.framework.cache.transaction.BufferedCache;
import com.xunlei.framework.cache.transaction.BufferedCacheRemoteSender;
import com.xunlei.framework.support.redis.RedisTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import static org.springframework.util.Assert.notNull;

/**
 * 缓存链构造器
 */
public class CacheChainBuilder implements InitializingBean {

    /**
     * 依赖外部注入
     */
    private RedisTemplate redisTemplate;

    private ICache jvmCache;
    private ICache redisCache;
    private ICache jvmToRedisCache;

    /**
     * 是否需要事务
     */
    private boolean transaction = true;
    private BufferedCacheRemoteSender remoteSender;

    public void initialize() {
        // 支持事务时，创建缓存发送器
        if (transaction) {
            remoteSender = new BufferedCacheRemoteSender(redisTemplate);
        }

        jvmCache = buildJvmCache();
        redisCache = buildRedisCache();
        jvmToRedisCache = buildJvmToRedisCache();
    }

    public ICache buildCache(CacheBean cacheBean) {
        if (cacheBean == null) {
            return null;
        }
        switch (cacheBean.getLevel()) {
            case JVM:
                return jvmCache;
            case REDIS:
                return redisCache;
            case JVM_TO_REDIS:
                return jvmToRedisCache;
        }
        return null;
    }

    /**
     * Build Local Jvm Cache
     *
     * @return
     */
    private ICache buildJvmCache() {
        JVMCache cache = new JVMCache();

        if (transaction) {
            BufferedCache buffer = new BufferedCache(cache, remoteSender, CacheLevel.JVM);
            return setStandardDecorators(buffer);
        }
        return setStandardDecorators(cache);
    }

    /**
     * Build Redis Cache
     *
     * @return
     */
    private ICache buildRedisCache() {
        RedisCache cache = new RedisCache(redisTemplate);

        if (transaction) {
            BufferedCache buffer = new BufferedCache(cache, remoteSender, CacheLevel.REDIS);
            return setStandardDecorators(buffer);
        }

        return setStandardDecorators(cache);
    }

    /**
     * Build Redis Cache
     *
     * @return
     */
    private ICache buildJvmToRedisCache() {
        ICache redisCache = new RedisCache(redisTemplate);
        ICache jvmCache = new JVMCache(redisCache);

        if (transaction) {
            BufferedCache buffer = new BufferedCache(jvmCache, remoteSender, CacheLevel.JVM_TO_REDIS);
            return setStandardDecorators(buffer);
        }

        return setStandardDecorators(jvmCache);
    }

    private ICache setStandardDecorators(ICache chains) {
        if (LoggerFactory.getLogger(LoggingCache.class).isDebugEnabled()) {
            chains = new LoggingCache(chains);
        }
        return new CheckParameterCache(chains);
    }

    public ICache getJvmCache() {
        return jvmCache;
    }

    public ICache getRedisCache() {
        return redisCache;
    }

    public ICache getJvmToRedisCache() {
        return jvmToRedisCache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(redisTemplate, "Property 'redisTemplate' is required");

        //notNull(updateCommandProducer, "Property 'updateCommandProducer' is required");

        this.initialize();
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setTransaction(boolean transaction) {
        this.transaction = transaction;
    }
}