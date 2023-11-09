package com.xunlei.framework.support.redis.shard;

import com.xunlei.framework.common.util.NetUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * Redis Client for multiple servers,
 * <p>
 * 所有来自GenericObjectPoolConfig都可以通过setter method来设置
 *
 * @see GenericObjectPoolConfig
 */
public class ShardedJedisClientFactoryBean extends GenericObjectPoolConfig
        implements FactoryBean<ShardedJedisPool>, InitializingBean, DisposableBean {

    private ShardedJedisPool shardedJedisPool;

    private String connectionString;

    @Override
    public void destroy() throws Exception {
        if (shardedJedisPool != null) {
            shardedJedisPool.destroy();
        }
    }

    @Override
    public ShardedJedisPool getObject() throws Exception {
        return shardedJedisPool;
    }

    @Override
    public Class<?> getObjectType() {
        return ShardedJedisPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(connectionString, "Property 'connectionString' is required");
        List<URI> haps = NetUtils.fromURIStringArray(connectionString);
        List<JedisShardInfo> jsi = new ArrayList<>();
        for (URI uri : haps) {
            jsi.add(new JedisShardInfo(uri.getHost(), uri.getPort()));
        }
        shardedJedisPool = new ShardedJedisPool(this, jsi);
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

}
