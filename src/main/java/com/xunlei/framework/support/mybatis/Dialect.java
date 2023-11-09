package com.xunlei.framework.support.mybatis;

/**
 * SQL方言接口
 */
public interface Dialect {

    String getPagingSQL(String sql);


    String getCountingSQL(String sql);


    Order[] order();
}
