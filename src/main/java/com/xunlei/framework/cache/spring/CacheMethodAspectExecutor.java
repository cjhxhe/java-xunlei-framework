package com.xunlei.framework.cache.spring;

import com.xunlei.framework.cache.CacheManager;
import com.xunlei.framework.cache.DataProxyException;
import com.xunlei.framework.cache.Delegater;
import com.xunlei.framework.cache.annotations.Cache;
import com.xunlei.framework.cache.annotations.GroupStrategy;
import com.xunlei.framework.cache.annotations.MergingStrategy;
import com.xunlei.framework.cache.config.Configuration;
import com.xunlei.framework.cache.impl.CacheChainBuilder;
import com.xunlei.framework.cache.impl.CascadeCacheManager;
import com.xunlei.framework.cache.impl.DefaultDataProxy;
import com.xunlei.framework.common.util.ClassUtils;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;
import java.util.List;

public class CacheMethodAspectExecutor implements InitializingBean {

    /**
     * 需要注入的参数
     */
    private CacheChainBuilder chainBuilder;

    /**
     * 自动实例化的参数
     *
     * @see {@link CacheAspectExecutor#afterPropertiesSet()}
     */
    private DefaultDataProxy dataProxy;

    public CacheMethodAspectExecutor() {
    }

    public CacheMethodAspectExecutor(CacheChainBuilder chainBuilder) {
        this.chainBuilder = chainBuilder;
    }

    public Object insert(final MethodInvocation invocation, Object bean)
            throws Throwable {
        if (!checkHasCache(bean)) {
            return invocation.proceed();
        }
        return dataProxy.insert(bean, new CacheMethodAspectExecutor.MethodDefaultDelegater<Integer>(invocation));
    }

    public Object updateByPrimaryKey(final MethodInvocation invocation, Object bean)
            throws Throwable {
        if (!checkHasCache(bean)) {
            return invocation.proceed();
        }
        return dataProxy.updateByPrimaryKey(bean,
                new CacheMethodAspectExecutor.MethodDefaultDelegater<Integer>(invocation));
    }

    public Object updateByPrimaryKeySelective(final MethodInvocation invocation, Object bean)
            throws Throwable {
        if (!checkHasCache(bean)) {
            return invocation.proceed();
        }
        return dataProxy.updateByPrimaryKeySelective(bean,
                new CacheMethodAspectExecutor.MethodDefaultDelegater<Integer>(invocation));
    }

    public Object deleteByPrimaryKey(final MethodInvocation invocation, Object bean)
            throws Throwable {
        if (!checkHasCache(bean)) {
            return invocation.proceed();
        }
        return dataProxy.deleteByPrimaryKey(bean,
                new CacheMethodAspectExecutor.MethodDefaultDelegater<Integer>(invocation));
    }

    public Object selectByPrimaryKey(final MethodInvocation invocation, Object bean)
            throws Throwable {
        if (!checkHasCache(bean)) {
            return invocation.proceed();
        }
        return dataProxy.selectByPrimaryKey(bean, new CacheMethodAspectExecutor.MethodDefaultDelegater<Object>(
                invocation));
    }

    public <T> Object selectByGroupKey(MethodInvocation invocation, Object bean)
            throws Throwable {
        if (!checkHasCache(bean)) {
            return invocation.proceed();
        }
        Method method = invocation.getMethod();
        if (!method.isAnnotationPresent(GroupStrategy.class)) {
            return invocation.proceed();
        }
        GroupStrategy group = method.getAnnotation(GroupStrategy.class);
        return dataProxy.selectByGroupKey(bean, group.value(),
                new CacheMethodAspectExecutor.MethodDefaultDelegater<List<T>>(invocation));
    }

    public <T> Object selectAll(MethodInvocation invocation)
            throws Throwable {
        Class<?> clazz = ClassUtils.getGenericReturnType(invocation.getMethod(), 0);
        Object bean = clazz.newInstance();

        if (!checkHasCache(bean)) {
            return invocation.proceed();
        }

        return dataProxy.selectByGroupKey(bean, Configuration.DEFAULT_MINI_TABLE,
                new CacheMethodAspectExecutor.MethodDefaultDelegater<List<T>>(invocation));
    }

    @SuppressWarnings("unchecked")
    public Object selectMergingByPrimaryKey(MethodInvocation invocation,
                                            Object bean) throws Throwable {
        if (!checkHasCache(bean)) {
            return invocation.proceed();
        }
        Method method = invocation.getMethod();
        if (!method.isAnnotationPresent(MergingStrategy.class)) {
            return invocation.proceed();
        }
        Class<Object> dtoClass = (Class<Object>) method.getReturnType();
        return dataProxy.selectMergingByPrimaryKey(bean,
                new CacheMethodAspectExecutor.MethodDefaultDelegater<Object>(invocation), dtoClass);
    }

    private boolean checkHasCache(Object bean) {
        if (bean == null) {
            return false;
        }
        Cache cache = bean.getClass().getAnnotation(Cache.class);
        if (cache == null) {
            return false;
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (chainBuilder == null) {
            throw new IllegalArgumentException("chainBuilder["
                    + CacheChainBuilder.class + "] must be injected.");
        }

        CacheManager cm = new CascadeCacheManager(chainBuilder);
        this.dataProxy = new DefaultDataProxy(cm);
    }

    static class MethodDefaultDelegater<T> implements Delegater<T> {

        private MethodInvocation invocation;

        public MethodDefaultDelegater(MethodInvocation invocation) {
            this.invocation = invocation;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T execute() throws DataProxyException {
            try {
                Object o = invocation.proceed();
                if (o != null) {
                    return (T) o;
                }
                return null;
            } catch (Throwable e) {
                throw DataProxyException.newIt(e);
            }
        }

    }

    public void setChainBuilder(CacheChainBuilder chainBuilder) {
        this.chainBuilder = chainBuilder;
    }
}
