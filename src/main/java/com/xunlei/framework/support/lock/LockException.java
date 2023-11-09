/*!
 * Copyright 2018, Julun, Inc.
 */

package com.xunlei.framework.support.lock;

/**
 * Lock Exception Define
 */
public class LockException extends RuntimeException {

    public LockException(String msg) {
        super(msg);
    }

    public LockException(Throwable e) {
        super(e);
    }

    /**
     * 消息内容如果有参数，格式为："hello {}, {}"
     */
    public LockException(String message, Throwable e) {
        super(message, e);
    }

}
