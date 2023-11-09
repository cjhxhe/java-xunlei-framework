package com.xunlei.framework.sharding.routing;


import com.xunlei.framework.sharding.annotation.Table;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 数据源路由拦截器
 */
public class RoutingContextInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
        Class<?> clazz = methodInvocation.getMethod().getDeclaringClass();
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new Exception("未定义表名，无法路由数据源");
        }
        Table table = clazz.getAnnotation(Table.class);
        return RoutingContextExecutor.doProxy(clazz.getName(), table.schema(), table.value(), new RoutingCallback() {
            @Override
            public Object doCallback() throws Throwable {
                return methodInvocation.proceed();
            }
        });
    }
}
