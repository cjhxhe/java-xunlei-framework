/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.sharding.transaction;


import com.xunlei.framework.support.lock.LockCallback;

/**
 * 对象锁接口定义
 */
public interface TransactionLock {

    /**
     * 锁竞争
     */
    boolean acquire() throws RuntimeException;

    /**
     * 锁释放
     */
    void release() throws RuntimeException;

    /**
     * 锁键值
     *
     * @return
     */
    Object getLockKey();

    <T> T waitAndExecute(LockCallback<T> lc) throws Exception;

}
