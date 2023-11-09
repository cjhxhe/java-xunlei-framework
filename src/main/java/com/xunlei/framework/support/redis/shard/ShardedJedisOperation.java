package com.xunlei.framework.support.redis.shard;

import com.xunlei.framework.common.util.MapUtils;
import com.xunlei.framework.support.redis.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.*;

import java.util.*;
import java.util.Map.Entry;


public class ShardedJedisOperation extends AbstractRedisOperation
        implements BeanFactoryAware, InitializingBean {

    static final String DEFAULT_SHARDED_JEDIS_FACTORY_BEAN_KEY = "shardedJedisPool";

    private String shardedJedisFactoryBeanKey = DEFAULT_SHARDED_JEDIS_FACTORY_BEAN_KEY;

    private ShardedJedisPool shardedJedisPool;

    private BeanFactory beanFactory;

    @Override
    public List<String> gets(final String... keys) {
        return exec0(new ShardedJedisCallback<List<String>>() {
            @Override
            public List<String> doCallback(ShardedJedis jedis) {
                List<String> response = new ArrayList<String>();
                for (String key : keys) {
                    response.add(jedis.get(key));
                }
                return response;
            }
        });
    }

    @Override
    public String sets(final String... keyvalues) {
        return exec0(new ShardedJedisCallback<String>() {
            @Override
            public String doCallback(ShardedJedis jedis) {
                Object[] args = Arrays.asList(keyvalues).toArray();
                final Map<String, Object> map = MapUtils.gmap(args);
                pipelined(new PiplineCallbackAdapter() {
                    @Override
                    public void doCallback(ShardedJedisPipeline pipeline) {
                        for (Entry<String, Object> entry : map.entrySet()) {
                            pipeline.set(entry.getKey(), entry.getValue().toString());
                        }
                    }
                });
                return new String();
            }
        });
    }

    @Override
    public Long del(final String... keys) {
        return exec0(new ShardedJedisCallback<Long>() {
            @Override
            public Long doCallback(ShardedJedis jedis) {
                pipelined(new PiplineCallbackAdapter() {
                    @Override
                    public void doCallback(ShardedJedisPipeline pipeline) {
                        for (String key : keys) {
                            pipeline.del(key);
                        }
                    }
                });
                return new Long(keys.length);
            }
        });
    }

    @Override
    public List<Object> transaction(TransactionCallback callback) {
        throw new UnsupportedOperationException("Sharded jedis doesn't support transactions");
    }

    @Override
    public List<Object> pipelined(final PiplineCallback callback) {
        return exec0(new ShardedJedisCallback<List<Object>>() {
            @Override
            public List<Object> doCallback(ShardedJedis jedis) {
                ShardedJedisPipeline sjp = jedis.pipelined();
                callback.doCallback(sjp);
                return sjp.syncAndReturnAll();
            }
        });
    }

    protected <T> T exec0(final ShardedJedisCallback<T> callback) {
        return exec(new RedisCallback<T>() {
            @Override
            public T doCallback(JedisCommands param) {
                return callback.doCallback((ShardedJedis) param);
            }
        });
    }

    @Override
    public <T> T exec(final RedisCallback<T> callback) {
        T t = null;
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            t = callback.doCallback(jedis);
            //shardedJedisPool.returnResource(jedis);
        } catch (Throwable e) {
            //shardedJedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        // handleTheReponseText(text);
        return t;
    }

    private interface ShardedJedisCallback<V> {
        V doCallback(ShardedJedis jedis);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            this.shardedJedisPool = beanFactory.getBean(shardedJedisFactoryBeanKey, ShardedJedisPool.class);
        } catch (BeansException be) {
            logger.error("[" + shardedJedisFactoryBeanKey + "] not in the spring container.");
            throw be;
        }
    }

    public void setShardedJedisFactoryBeanKey(String shardedJedisFactoryBeanKey) {
        this.shardedJedisFactoryBeanKey = shardedJedisFactoryBeanKey;
    }

    @Override
    public Object evalsha(String sha1, int keyCount, String... params) {
        throw new UnsupportedOperationException("Sharded jedis doesn't support evalsha command");
    }

    @Override
    public String scriptLoad(String script) {
        throw new UnsupportedOperationException("Sharded jedis doesn't support scriptLoad command");
    }

    @Override
    public Boolean scriptExists(String sha1) {
        throw new UnsupportedOperationException("Sharded jedis doesn't support scriptExists command");
    }

    @Override
    public Object eval(String script, int keyCount, String... params) {
        throw new UnsupportedOperationException("Sharded jedis doesn't support eval command");
    }

    @Override
    public String rename(final String oldKey, final String newKey) {
        throw new UnsupportedOperationException("Sharded jedis doesn't support rename command");
    }

    @Override
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        throw new UnsupportedOperationException("Sharded jedis doesn't support subscribe command");
    }

    @Override
    public Long publish(String channel, String message) {
        throw new UnsupportedOperationException("Sharded jedis doesn't support subscribe command");
    }

    @Override
    public Set<String> sdiff(String... keys) {
        throw new UnsupportedOperationException("Sharded jedis doesn't support sdiff command");
    }
}
