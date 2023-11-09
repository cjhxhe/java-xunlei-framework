package com.xunlei.framework.sharding.transaction;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;

/**
 * Creates a {@code RoutingManagedTransactionFactory}.
 */
public class RoutingManagedTransactionFactory extends SpringManagedTransactionFactory {

    @Override
    public Transaction newTransaction(DataSource dataSource,
                                      TransactionIsolationLevel level, boolean autoCommit) {
        return new RoutingSpringManagedTransaction(dataSource);
    }

}
