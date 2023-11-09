package com.xunlei.framework.support.transaction;

/**
 * 发送器接口定义
 */
public interface RemoteSender<T> {

    /**
     * 获取发送器提供商，用于DEBUG等
     *
     * @return
     */
    public String getProviderInfo();

}
