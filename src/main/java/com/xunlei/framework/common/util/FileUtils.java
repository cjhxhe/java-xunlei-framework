package com.xunlei.framework.common.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件处理工具类
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static final Integer DEFAULT_CONNECT_TIMEOUT = 3000;
    private static final Integer DEFAULT_READ_TIMEOUT = 10000;

    /**
     * 下载文件
     *
     * @param fileUrl
     * @return
     */
    public static InputStream download(String fileUrl) {
        return FileUtils.download(fileUrl, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 下载文件
     *
     * @param fileUrl
     * @param readTimeout
     * @return
     */
    public static InputStream download(String fileUrl, Integer readTimeout) {
        return FileUtils.download(fileUrl, DEFAULT_CONNECT_TIMEOUT, readTimeout);
    }

    /**
     * 下载文件
     *
     * @param fileUrl
     * @param connectTimeout
     * @param readTimeout
     * @return
     */
    public static InputStream download(String fileUrl, Integer connectTimeout, Integer readTimeout) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            return connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
