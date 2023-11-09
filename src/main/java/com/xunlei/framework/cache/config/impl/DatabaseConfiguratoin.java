/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.config.impl;

import com.xunlei.framework.cache.ConfigurationException;
import com.xunlei.framework.cache.config.CacheBean;

/**
 * 实现从数据库读取缓存配置信息
 */
public class DatabaseConfiguratoin extends AbstractConfiguration {

    @Override
    public CacheBean parserConfiguration(Class<?> type)
            throws ConfigurationException {
        throw new UnsupportedOperationException();
    }

}
