package com.xunlei.framework.support.redis.jedis;

import com.xunlei.framework.common.util.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * Redis Client for standalone Server,
 * <p>
 * 所有来自GenericObjectPoolConfig都可以通过setter method来设置
 */
public class JedisClientFactoryBean extends JedisPoolConfig implements FactoryBean<JedisPool>, InitializingBean, DisposableBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool redisClient;

    private String connectionString;

    // 读取数据的等待时长，单位毫秒
    protected int timeout = 10000;

    private String redisType;
    private boolean isProdEnv;

    @Override
    public JedisPool getObject() throws Exception {
        return redisClient;
    }

    @Override
    public Class<?> getObjectType() {
        return JedisPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(connectionString, "Property 'connectionString' is required");
        List<URI> urls = NetUtils.fromURIStringArray(connectionString);
        if (urls.size() > 1) {
            throw new IllegalArgumentException("The class supports only a single server.");
        }
        // uri格式：redis://127.0.0.1:7378、redis://127.0.0.1:7378/10(指定数据库)、redis://:123456@127.0.0.1(指定密码)
        if (isProdEnv) {
            logger.info("Initialize {} redis：{}", this.redisType, "****");
        } else {
            logger.info("Initialize {} redis：{}", this.redisType, this.connectionString);
        }
        redisClient = new JedisPool(this, urls.get(0), timeout);
    }

    @Override
    public void destroy() throws Exception {
        if (redisClient != null) {
            redisClient.destroy();
        }
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setRedisType(String redisType) {
        this.redisType = redisType;
    }

    public void setProdEnv(boolean prodEnv) {
        this.isProdEnv = prodEnv;
    }
}
