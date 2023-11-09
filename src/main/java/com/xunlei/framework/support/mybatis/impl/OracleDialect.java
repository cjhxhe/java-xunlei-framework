package com.xunlei.framework.support.mybatis.impl;

import com.xunlei.framework.support.mybatis.Dialect;
import com.xunlei.framework.support.mybatis.Order;

import java.text.MessageFormat;

/**
 * Oracle方言
 */
public class OracleDialect implements Dialect {

    final String PAGING_SQL_TPL = "select * from  (select tmp0_.*,rownum rownum_ from ({0}) tmp0_) where rownum_ >= ? and rownum_ < ?";

    final String COUNTING_SQL_TPL = "SELECT COUNT(1) FROM ({0}) ";

    @Override
    public String getPagingSQL(String sql) {
        return MessageFormat.format(PAGING_SQL_TPL, sql);
    }

    @Override
    public String getCountingSQL(String sql) {
        return MessageFormat.format(COUNTING_SQL_TPL, sql);
    }

    @Override
    public Order[] order() {
        return new Order[]{Order.OFFSET, Order.LIMIT};
    }
}
