package com.xunlei.framework.common.http;

/**
 * Http连接池配置
 */
public class HttpPoolConfig {

    /**
     * 默认连接池数量
     */
    private int poolMaxTotal = 10;

    /**
     * 从连接池中获取连接超时时间
     */
    private int connectionRequestTimeout = 5000;

    /**
     * 连接服务器超时时间
     */
    private int connectTimeout = 5000;

    /**
     * 读取数据超时时间
     */
    private int socketTimeout = 5000;

    /**
     * 请求失败重试次数，默认不重试
     */
    private int retryCount = 0;

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public HttpPoolConfig setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
        return this;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public HttpPoolConfig setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public HttpPoolConfig setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public HttpPoolConfig setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public HttpPoolConfig setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }
}
