package com.xunlei.framework.support.transaction;

import java.util.Collection;

/**
 * 批量发送
 */
public interface RemoteSenderMorly<T> extends RemoteSender<T> {

    /**
     * 批量发送接口方法定义
     */
    boolean send(Collection<T> data) throws Exception;

}
