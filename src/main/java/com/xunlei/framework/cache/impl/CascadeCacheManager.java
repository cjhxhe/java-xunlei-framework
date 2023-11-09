/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.impl;

import com.xunlei.framework.cache.CacheException;
import com.xunlei.framework.cache.ModInfo;
import com.xunlei.framework.cache.config.CacheBean;
import com.xunlei.framework.cache.config.GroupBean;
import com.xunlei.framework.cache.storage.ICache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存的级联管理, 主要维护组的级联关系
 */
public class CascadeCacheManager extends CacheManagerImpl {
    Logger logger = LoggerFactory.getLogger(getClass());

    public CascadeCacheManager() {
    }

    public CascadeCacheManager(CacheChainBuilder cacheChain) {
        super(cacheChain);
    }

    @Override
    public String insert(Object bean) throws CacheException {
        final String cacheKey = super.insert(bean);
        if (cacheKey == null) {
            return cacheKey;
        }
        // check has more groups
        Map<String, List<GroupBean>> groups = getGroups(bean);
        if (groups == null || groups.size() == 0) {
            return cacheKey;
        }
        doCascadeOps(cacheKey, bean, null, CascadeOperation.getInsertOps(groups));
        return cacheKey;
    }

    /**
     * 目前所有的修改操作，都通过删除来完成
     */
    @Override
    public String updateByPrimaryKey(Object newBean) throws CacheException {
        return this.deleteByPrimaryKey(newBean);
    }

    /**
     * @see #updateByPrimaryKey(Object) 排除分组属性值为null的分组
     */
    @Override
    public ModInfo updateByPrimaryKeySelective(Object newBean) throws CacheException {
        String cacheKey = this.deleteByPrimaryKey(newBean);
        return new ModInfo(cacheKey, null, null, newBean, null);
    }

    /**
     * <p>删除缓存数据并清除分组信息</p>
     * <p>如果存在分组，此时就会从缓存中查找到这些值，触发额外的1次Socket</p>
     */
    @Override
    public String deleteByPrimaryKey(Object bean) throws CacheException {
        // check groups & get default group cacheKey by parameter bean
        Map<String, List<GroupBean>> groups = getGroups(bean);
        if (groups == null) {
            return super.deleteByPrimaryKey(bean);
        }
        // exist groups
        Object oldBean = this.selectByPrimaryKey(bean);
        // remove
        final String cacheKey = super.deleteByPrimaryKey(bean);
        if (cacheKey != null && groups.size() > 0) {
            // 修复单条缓存已删除后，如果不重新加载，分组缓存无法生效的问题
            List<CascadeOperation> cascades = CascadeOperation.getDeleteOps(groups);
            if (cascades.size() == 0) {
                return cacheKey;
            }
            ICache cache = buildCache(bean);
            for (CascadeOperation op : cascades) {
                cache.del(buildGroupCacheKey(bean, op.getGroup()));
            }
            return cacheKey;
        }
        if (cacheKey == null || oldBean == null) {
            return cacheKey;
        }
        // cascade
        doCascadeOps(cacheKey, bean, oldBean, CascadeOperation.getDeleteOps(groups));
        return cacheKey;
    }

    /**
     * 级联操作实现
     */
    protected void doCascadeOps(String cacheKey, Object newBean, Object oldBean,
                                List<CascadeOperation> cascades) throws CacheException {
        if (cascades == null || cascades.size() == 0) {
            return;
        }
        ICache cache = buildCache(newBean);
        for (CascadeOperation op : cascades) {
            try {
                String group = op.getGroup();
                switch (op.getOperation()) {
                    case DELETE:
                        // 这里直接删除分组缓存，触发下一次的查找
                        // 之前的版本是删除成员
                        cache.del(buildGroupCacheKey(oldBean, group));
                        break;
                    case DELETE_INSERT:
                        cache.delMembers(buildGroupCacheKey(oldBean, group), cacheKey);
                        // 执行完不跳出，继续执行INSERT CASE
                    case INSERT:
                        // 修复all缓存删除后，只插入一条缓存的bug
                        cache.del(buildGroupCacheKey(newBean, group));
//                        String groupKey = buildGroupCacheKey(newBean, group);
//                        if (existGroup(cache, groupKey)) {
//                            cache.addMembers(groupKey, cacheKey);
//                        }
                        break;
                    default:
                        break;
                }
            } catch (CacheException e) {
                logger.error("Cascade operation failed", e);
            }
        }
    }

    private boolean existGroup(ICache cache, String groupKey) throws CacheException {
        Set<String> mems = cache.getMembers(groupKey);
        return mems == null || mems.size() == 0 ? false : true;
    }

    private Map<String, List<GroupBean>> getGroups(Object bean) throws CacheException {
        CacheBean cacheBean = getCacheBean(bean);
        Map<String, List<GroupBean>> groups = cacheBean.getGroups();
        if (groups == null || groups.size() == 0) {
            return null;
        }
        return groups;
    }
}
