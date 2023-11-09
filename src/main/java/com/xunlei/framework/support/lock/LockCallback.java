package com.xunlei.framework.support.lock;


/**
 * 用户回调函数接口定义，确保所有存在锁问题的业务都应在回调函数中实现
 */
public interface LockCallback<T> {

    T doCallback() throws RuntimeException;

}
