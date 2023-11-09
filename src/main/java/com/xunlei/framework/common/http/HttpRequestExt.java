package com.xunlei.framework.common.http;

import java.util.Map;

/**
 * Http请求扩展参数
 */
public class HttpRequestExt {

    /**
     * 请求格式
     */
    private HttpMediaType mediaType = HttpMediaType.APPLICATION_JSON;

    /**
     * 请求头参数
     */
    private Map<String, Object> headers;

    public HttpRequestExt() {

    }

    public HttpRequestExt(Map<String, Object> headers) {
        this.headers = headers;
    }

    public HttpRequestExt(HttpMediaType mediaType, Map<String, Object> headers) {
        this.mediaType = mediaType;
        this.headers = headers;
    }

    public HttpMediaType getMediaType() {
        return mediaType;
    }

    public HttpRequestExt setMediaType(HttpMediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public HttpRequestExt setHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }
}
