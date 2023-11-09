/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.sharding.transaction;

import org.springframework.transaction.TransactionException;

/**
 * 分布式事务锁异常定义
 */
public class TransactionLockException extends TransactionException {

    private static final long serialVersionUID = 1L;

    public TransactionLockException(String msg) {
        super(msg);
    }

    public TransactionLockException(String msg, Throwable e) {
        super(msg, e);
    }

    public TransactionLockException(Throwable e) {
        super("Transaction Lock Exception", e);
    }

}
