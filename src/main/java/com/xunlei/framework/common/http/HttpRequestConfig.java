package com.xunlei.framework.common.http;

/**
 * Http请求配置
 */
public class HttpRequestConfig {

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

    public HttpRequestConfig() {

    }

    public HttpRequestConfig(int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public HttpRequestConfig setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public HttpRequestConfig setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public HttpRequestConfig setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public HttpRequestConfig setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }
}
