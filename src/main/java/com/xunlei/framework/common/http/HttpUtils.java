package com.xunlei.framework.common.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

/**
 * HTTP 请求工具类
 */
public class HttpUtils {

    public static final Integer CONNECTION_TIMEOUT = 30000;

    public static String get(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = HttpClients.createDefault().execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        }
        return null;
    }

    public static String get(String url, Map<String, String> headers) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        // 设置请求头
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpGet.setHeader(header.getKey(), header.getValue());
            }
        }
        HttpResponse response = HttpClients.createDefault().execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        }
        return null;
    }

    public static String post(String url, Map<String, String> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<>();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        HttpResponse response = HttpClients.createDefault().execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        }
        return null;
    }

    public static String post(String url, String paramStr) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (paramStr != null) {
            StringEntity entity = new StringEntity(paramStr, "utf-8");
            entity.setContentEncoding("utf-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }
        HttpResponse response = HttpClients.createDefault().execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        }
        return null;
    }

    public static String post(String url, String paramStr, Map<String, String> headers) throws IOException {
        return post(url, paramStr, headers, CONNECTION_TIMEOUT);
    }

    public static String post(String url, String paramStr, Map<String, String> headers, Integer timeout) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (paramStr != null) {
            StringEntity entity = new StringEntity(paramStr, "utf-8");
            entity.setContentEncoding("utf-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }
        // 设置请求头
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }
        httpPost.setConfig(RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build());
        HttpResponse response = HttpClients.createDefault().execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(post("localhost:8080", new HashMap<>()));
    }
}
