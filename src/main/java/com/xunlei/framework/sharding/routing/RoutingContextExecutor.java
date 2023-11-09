package com.xunlei.framework.sharding.routing;

/**
 * 数据源路由上下文执行器
 */
public class RoutingContextExecutor {

    public static Object doProxy(String className, String tableName, RoutingCallback callback) throws Throwable {
        return RoutingContextExecutor.doProxy(className, null, tableName, callback);
    }

    public static Object doProxy(String className, String tableSchema, String tableName, RoutingCallback callback) throws Throwable {
        RoutingContext context = new RoutingContext(className, tableSchema, tableName);
        try {
            RoutingContextHolder.setRoutingContext(context);
            return callback.doCallback();
        } finally {
            RoutingContextHolder.clearRoutingContext();
        }
    }
}
