package com.xunlei.framework.cache.transaction;

import com.xunlei.framework.common.util.IPUtils;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Client Id 生成器， 确保所有JVM进程的唯一性
 */
public class ClientIdGenerator implements InitializingBean {

    public static String clientId;

    /**
     * 获取当前IP地址及进程号
     *
     * @return
     */
    protected String genClientId() throws UnknownHostException {
        InetAddress addr = IPUtils.getLocalHostLANAddress();
        String ip = addr.getHostAddress();
        Integer pid = IPUtils.getCurrentPid();
        return ip + "@" + pid;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (clientId == null) {
            clientId = genClientId();
        }
    }

    /**
     * 如果在获取不到当前机器IP及进程号的情况下，则需要配置该属性，来确保当前JVM进程的唯一性
     */
    public void setClientId(String customClientId) {
        if (ClientIdGenerator.clientId == null) {
            ClientIdGenerator.clientId = customClientId;
        }
    }

    public static String getClientId() {
        return clientId;
    }
}
