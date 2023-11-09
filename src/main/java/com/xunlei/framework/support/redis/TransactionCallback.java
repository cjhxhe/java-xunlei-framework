package com.xunlei.framework.support.redis;

import redis.clients.jedis.Transaction;


/**
 * callback for redis transaction
 */
public interface TransactionCallback {

    void doCallback(Transaction trans);

}
