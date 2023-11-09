package com.xunlei.framework.common.http;

import com.alibaba.fastjson.JSONObject;
import com.xunlei.framework.common.util.JsonUtils;
import com.xunlei.framework.common.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP连接池管理类
 */
public class HttpPoolManager {

    /**
     * 请求失败重试次数
     */
    private int retryCount;
    /**
     * 请求配置
     */
    private RequestConfig defaultRequestConfig;
    /**
     * 连接池管理对象
     */
    private PoolingHttpClientConnectionManager connManager;

    private HttpPoolManager() {

    }

    private HttpPoolManager(HttpPoolConfig config) {
        this.retryCount = config.getRetryCount();

        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory
                .getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory
                .getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        connManager = new PoolingHttpClientConnectionManager(registry);
        connManager.setMaxTotal(config.getPoolMaxTotal());
        // 只有一个路由，每个路由最大连接数和连接池一致
        connManager.setDefaultMaxPerRoute(config.getPoolMaxTotal());

        // 初始化默认请求配置
        defaultRequestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(config.getConnectionRequestTimeout())
                .setConnectTimeout(config.getConnectTimeout())
                .setSocketTimeout(config.getSocketTimeout())
                .build();
    }

    /**
     * 构建连接池
     *
     * @return
     */
    public static HttpPoolManager builder() {
        return HttpPoolManager.builder(null);
    }

    /**
     * 构建连接池
     *
     * @param poolMaxTotal 连接池数量
     * @return
     */
    public static HttpPoolManager builder(int poolMaxTotal) {
        return HttpPoolManager.builder((new HttpPoolConfig().setPoolMaxTotal(poolMaxTotal)));
    }

    /**
     * 构建连接池
     *
     * @param config 连接池配置
     * @return
     */
    public static HttpPoolManager builder(HttpPoolConfig config) {
        if (config == null) {
            config = new HttpPoolConfig();
        }
        return new HttpPoolManager(config);
    }

    /**
     * Post请求
     *
     * @param url    请求地址
     * @param object map或表单对象
     * @return
     * @throws Exception
     */
    public String post(String url, Object object) throws Exception {
        return post(url, object, null, null);
    }

    /**
     * Post请求
     *
     * @param url    请求地址
     * @param object map或表单对象
     * @param ext    扩展配置
     * @return
     * @throws Exception
     */
    public String post(String url, Object object, HttpRequestExt ext) throws Exception {
        return post(url, object, ext, null);
    }

    /**
     * Post请求
     *
     * @param url    请求地址
     * @param object map或表单对象
     * @param config 请求配置
     * @return
     * @throws Exception
     */
    public String post(String url, Object object, HttpRequestConfig config) throws Exception {
        return post(url, object, null, config);
    }

    /**
     * Post请求
     *
     * @param url    请求地址
     * @param object map或表单对象
     * @param config 请求配置
     * @return
     * @throws Exception
     */
    public String post(String url, Object object, HttpRequestExt ext, HttpRequestConfig config) throws Exception {
        if (ext == null) {
            ext = new HttpRequestExt();
        }
        HttpEntity entity = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            setPostParams(httpPost, object, ext.getMediaType());
            setRequestHeader(httpPost, ext.getHeaders());
            setHttpProxy(httpPost);
            CloseableHttpResponse response = getHttpClient(config).execute(httpPost);
            entity = response.getEntity();
            return EntityUtils.toString(entity, "utf-8");
        } finally {
            EntityUtils.consume(entity);
        }
    }

    /**
     * Get请求
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public String get(String url) throws Exception {
        return get(url, null, null);
    }

    /**
     * Get请求
     *
     * @param url 请求地址
     * @param ext 请求扩展配置
     * @return
     * @throws Exception
     */
    public String get(String url, HttpRequestExt ext) throws Exception {
        return get(url, ext, null);
    }

    /**
     * Get请求
     *
     * @param url    请求地址
     * @param config 请求配置
     * @return
     * @throws Exception
     */
    public String get(String url, HttpRequestConfig config) throws Exception {
        return get(url, null, config);
    }

    /**
     * Get请求
     *
     * @param url    请求地址
     * @param ext    请求扩展配置
     * @param config 请求配置
     * @return
     * @throws Exception
     */
    public String get(String url, HttpRequestExt ext, HttpRequestConfig config) throws Exception {
        if (ext == null) {
            ext = new HttpRequestExt();
        }
        HttpEntity entity = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            setRequestHeader(httpGet, ext.getHeaders());
            setHttpProxy(httpGet);
            CloseableHttpResponse response = getHttpClient(config).execute(httpGet);
            entity = response.getEntity();
            return EntityUtils.toString(entity, "utf-8");
        } finally {
            EntityUtils.consume(entity);
        }
    }

    /**
     * 从连接池中获取连接
     *
     * @return
     */
    private CloseableHttpClient getHttpClient(HttpRequestConfig config) {
        int retryCount = this.retryCount;
        RequestConfig requestConfig = this.defaultRequestConfig;
        if (config != null) {
            retryCount = config.getRetryCount();
            requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(config.getConnectionRequestTimeout())
                    .setConnectTimeout(config.getConnectTimeout())
                    .setSocketTimeout(config.getSocketTimeout())
                    .build();
        }
        HttpClientBuilder builder = HttpClients.custom();
        if (retryCount > 0) {
            builder.setRetryHandler(new DefaultHttpRequestRetryHandler(retryCount, false));
        }
        return builder.setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connManager)
                .build();
    }

    /**
     * 设置请求参数
     *
     * @param httpPost
     * @param object
     * @param mediaType
     * @throws UnsupportedEncodingException
     */
    private void setPostParams(HttpPost httpPost, Object object, HttpMediaType mediaType) throws UnsupportedEncodingException {
        if (object == null) {
            return;
        }
        if (HttpMediaType.APPLICATION_JSON == mediaType) {
            String jsonStr = JSONObject.toJSONString(object);
            httpPost.setEntity(new StringEntity(jsonStr, ContentType.create("application/json", "utf-8")));
            return;
        }
        if (HttpMediaType.APPLICATION_FORM_URLENCODED == mediaType) {
            Map<String, Object> params;
            if (object instanceof Map) {
                params = (Map<String, Object>) object;
            } else {
                params = JsonUtils.toJsonMap(object);
            }
            List<NameValuePair> nvps = new ArrayList<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            return;
        }
    }

    /**
     * 设置请求头参数
     *
     * @param request
     * @param headerMap
     */
    private void setRequestHeader(HttpRequest request, Map<String, Object> headerMap) {
        if (headerMap == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            request.addHeader(entry.getKey(), entry.getValue().toString());
        }
    }

    /**
     * 根据系统变量设置是否否需要开启代理
     *
     * @param requestBase
     */
    private void setHttpProxy(HttpRequestBase requestBase) {
        String host = System.getProperty("http.proxyHost");
        String port = System.getProperty("http.proxyPort");
        if (StringUtils.isEmpty(host) || StringUtils.isEmpty(port)) {
            return;
        }
        HttpHost proxy = new HttpHost(host, Integer.parseInt(port), "http");
        requestBase.setConfig(RequestConfig.custom().setProxy(proxy).build());
    }
}
