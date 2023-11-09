package com.xunlei.framework.sharding.routing;

/**
 * 数据源路由变量绑定器
 */
public class RoutingContextHolder {

    private static final ThreadLocal<RoutingContext> contextHolder =
            new ThreadLocal<RoutingContext>();

    public static void setRoutingContext(RoutingContext context) {
        contextHolder.set(context);
    }

    public static RoutingContext getRoutingContext() {
        return contextHolder.get();
    }

    public static void clearRoutingContext() {
        contextHolder.remove();
    }
}
