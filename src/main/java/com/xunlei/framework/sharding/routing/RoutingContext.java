package com.xunlei.framework.sharding.routing;

/**
 * 数据源路由上下文
 */
public class RoutingContext {

    private String className;
    private String tableSchema;
    private String tableName;

    public RoutingContext(String className, String tableSchema, String tableName) {
        this.className = className;
        this.tableSchema = tableSchema;
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
