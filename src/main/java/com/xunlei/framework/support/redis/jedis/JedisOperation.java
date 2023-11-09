package com.xunlei.framework.support.redis.jedis;

import com.xunlei.framework.support.redis.AbstractRedisOperation;
import com.xunlei.framework.support.redis.PiplineCallback;
import com.xunlei.framework.support.redis.RedisCallback;
import com.xunlei.framework.support.redis.TransactionCallback;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Set;


public class JedisOperation extends AbstractRedisOperation {

    private JedisPool jedisPool;

    public JedisOperation(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public List<String> gets(final String... keys) {
        return exec0(new JedisCallback<List<String>>() {
            @Override
            public List<String> doCallback(Jedis jedis) {
                return jedis.mget(keys);
            }
        });
    }

    @Override
    public String sets(final String... keyvalues) {
        return exec0(new JedisCallback<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.mset(keyvalues);
            }
        });
    }

    @Override
    public Long del(final String... keys) {
        return exec0(new JedisCallback<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.del(keys);
            }
        });
    }

    @Override
    public List<Object> transaction(final TransactionCallback callback) {
        return exec0(new JedisCallback<List<Object>>() {
            @Override
            public List<Object> doCallback(Jedis jedis) {
                Transaction trans = jedis.multi();
                callback.doCallback(trans);
                return trans.exec();
            }
        });
    }

    @Override
    public List<Object> pipelined(final PiplineCallback callback) {
        return exec0(new JedisCallback<List<Object>>() {
            @Override
            public List<Object> doCallback(Jedis jedis) {
                Pipeline pipeline = jedis.pipelined();
                callback.doCallback(pipeline);
                return pipeline.syncAndReturnAll();
            }
        });
    }

    protected <T> T exec0(final JedisCallback<T> callback) {
        return exec(new RedisCallback<T>() {
            @Override
            public T doCallback(JedisCommands param) {
                return callback.doCallback((Jedis) param);
            }
        });
    }

    @Override
    public <T> T exec(final RedisCallback<T> callback) {
        T t = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            t = callback.doCallback(jedis);
            // 执行没有问题的连接返回到池里
            //jedisPool.returnResource(jedis);
        } catch (Throwable e) {
            // 只要的是异常就视为错误的资源连接，有点暴力
            //jedisPool.returnBrokenResource(jedis);
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        // handleTheReponseText(text);
        return t;
    }

    private interface JedisCallback<V> {
        V doCallback(Jedis jedis);
    }

    @Override
    public Object evalsha(final String sha1, final int keyCount, final String... params) {
        return exec0(new JedisCallback<Object>() {
            @Override
            public Object doCallback(Jedis jedis) {
                return jedis.evalsha(sha1, keyCount, params);
            }
        });
    }

    @Override
    public String scriptLoad(final String script) {
        return exec0(new JedisCallback<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.scriptLoad(script);
            }
        });
    }

    @Override
    public Boolean scriptExists(final String sha1) {
        return exec0(new JedisCallback<Boolean>() {
            @Override
            public Boolean doCallback(Jedis jedis) {
                return jedis.scriptExists(sha1);
            }
        });
    }

    @Override
    public Object eval(final String script, final int keyCount, final String... params) {
        return exec0(new JedisCallback<Object>() {
            @Override
            public Object doCallback(Jedis jedis) {
                return jedis.eval(script, keyCount, params);
            }
        });
    }

    @Override
    public String rename(final String oldKey, final String newKey) {
        return exec0(new JedisCallback<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.rename(oldKey, newKey);
            }
        });
    }

    @Override
    public void subscribe(final JedisPubSub jedisPubSub, final String... channels) {
        exec0(new JedisCallback<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                jedis.subscribe(jedisPubSub, channels);
                return null;
            }
        });
    }

    @Override
    public Long publish(final String channel, final String message) {
        return exec0(new JedisCallback<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.publish(channel, message);
            }
        });
    }

    @Override
    public Set<String> sdiff(String... keys) {
        return exec0(new JedisCallback<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.sdiff(keys);
            }
        });
    }
}

