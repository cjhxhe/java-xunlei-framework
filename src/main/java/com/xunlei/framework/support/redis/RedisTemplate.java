package com.xunlei.framework.support.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xunlei.framework.common.util.StringUtils;
import com.xunlei.framework.support.redis.jedis.JedisOperation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.params.ZParams;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.resps.Tuple;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Template for Redis
 */
public abstract class RedisTemplate implements InitializingBean, RedisOperation, BeanFactoryAware {

    private RedisOperation redisOperation;
    private BeanFactory beanFactory;

    public abstract String redisBeanName();

    public <T> T getObject(final String key) {
        String value = get(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return JSONObject.parseObject(value, new TypeReference<T>() {
        }.getType());
    }

    public <T> T getObject(final String key, Type clazz) {
        String value = get(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return JSONObject.parseObject(value, clazz);
    }

    @Override
    public String get(final String key) {
        return redisOperation.get(key);
    }

    @Override
    public List<String> gets(final String... keys) {
        return redisOperation.gets(keys);
    }

    @Override
    public String sets(final String... keyvalues) {
        return redisOperation.sets(keyvalues);
    }

    @Override
    public String set(final String key, final int seconds, final String value) {
        return redisOperation.set(key, seconds, value);
    }

    @Override
    public String set(final String key, final String value) {
        return redisOperation.set(key, value);
    }

    public String setObject(final String key, final Object value) {
        if (value == null) {
            return null;
        }
        return set(key, JSON.toJSONString(value));
    }

    public String setObject(final String key, final int seconds, final Object value) {
        if (value == null) {
            return null;
        }
        return set(key, seconds, JSON.toJSONString(value));
    }

    @Override
    public Long incr(final String key) {
        return redisOperation.incr(key);
    }

    @Override
    public Long incrby(final String key, final Long increment) {
        return redisOperation.incrby(key, increment);
    }

    @Override
    public Long decr(final String key) {
        return redisOperation.decr(key);
    }

    @Override
    public Long decrby(final String key, final Long decrement) {
        return redisOperation.decrby(key, decrement);
    }

    @Override
    public Long del(String... keys) {
        return redisOperation.del(keys);
    }

    @Override
    public boolean exists(String key) {
        return redisOperation.exists(key);
    }

    @Override
    public Long lrem(String key, Long count, String value) {
        return redisOperation.lrem(key, count, value);
    }

    @Override
    public Long rpush(String key, String... values) {
        return redisOperation.rpush(key, values);
    }

    @Override
    public List<String> lrange(String key, Long start, Long stop) {
        return redisOperation.lrange(key, start, stop);
    }

    @Override
    public String lset(String key, Long index, String value) {
        return redisOperation.lset(key, index, value);
    }

    @Override
    public List<Object> transaction(final TransactionCallback callback) {
        return redisOperation.transaction(callback);
    }

    @Override
    public List<Object> pipelined(final PiplineCallback callback) {
        return redisOperation.pipelined(callback);
    }

    public void setRedisOperation(RedisOperation redisOperation) {
        this.redisOperation = redisOperation;
    }

    @Override
    public String hmset(String key, Map<String, String> fieldValues) {
        return redisOperation.hmset(key, fieldValues);
    }

    @Override
    public Long hincrBy(String key, String field, Long increment) {
        return redisOperation.hincrBy(key, field, increment);
    }

    @Override
    public Double hincrByFloat(String key, String field, Double increment) {
        return redisOperation.hincrByFloat(key, field, increment);
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        return redisOperation.hmget(key, fields);
    }

    @Override
    public String hget(String key, String field) {
        return redisOperation.hget(key, field);
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return redisOperation.hgetAll(key);
    }

    @Override
    public Set<String> hkeys(String key) {
        return redisOperation.hkeys(key);
    }

    @Override
    public Long hlen(String key) {
        return redisOperation.hlen(key);
    }

    @Override
    public Long hdel(String key, String... fields) {
        return redisOperation.hdel(key, fields);
    }

    @Override
    public boolean hset(String key, String field, String value) {
        return redisOperation.hset(key, field, value);
    }

    @Override
    public boolean hexists(String key, String field) {
        return redisOperation.hexists(key, field);
    }

    @Override
    public Long sadd(String key, String... members) {
        return redisOperation.sadd(key, members);
    }

    @Override
    public Long scard(String key) {
        return redisOperation.scard(key);
    }

    @Override
    public Set<String> smembers(String key) {
        return redisOperation.smembers(key);
    }

    @Override
    public boolean sismember(String key, String member) {
        return redisOperation.sismember(key, member);
    }

    @Override
    public Long srem(String key, String... values) {
        return redisOperation.srem(key, values);
    }

    @Override
    public List<String> srandmember(String key, Integer count) {
        return redisOperation.srandmember(key, count);
    }

    @Override
    public Set<String> sinter(String... keys) {
        return redisOperation.sinter(keys);
    }

    @Override
    public Long expire(String key, Integer seconds) {
        return redisOperation.expire(key, seconds);
    }

    @Override
    public Long expireAt(String key, long unixTime) {
        return redisOperation.expireAt(key, unixTime);
    }

    @Override
    public Long persist(String key) {
        return redisOperation.persist(key);
    }

    @Override
    public Long ttl(String key) {
        return redisOperation.ttl(key);
    }

    @Override
    public Long zadd(String key, Double score, String member) {
        return redisOperation.zadd(key, score, member);
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMember) {
        return redisOperation.zadd(key, scoreMember);
    }

    @Override
    public Long zrem(String key, String... member) {
        return redisOperation.zrem(key, member);
    }

    @Override
    public Long zremrangeByRank(String key, long start, long stop) {
        return redisOperation.zremrangeByRank(key, start, stop);
    }

    @Override
    public Long zremrangeByScore(String key, double min, double max) {
        return redisOperation.zremrangeByScore(key, min, max);
    }

    @Override
    public Long zremrangeByScore(String key, String min, String max) {
        return redisOperation.zremrangeByScore(key, min, max);
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        return redisOperation.zincrby(key, score, member);
    }

    @Override
    public Long zrank(String key, String member) {
        return redisOperation.zrank(key, member);
    }

    @Override
    public Long zrevrank(String key, String member) {
        return redisOperation.zrevrank(key, member);
    }

    @Override
    public Long zcount(String key, double min, double max) {
        return redisOperation.zcount(key, min, max);
    }

    @Override
    public Long zcard(String key) {
        return redisOperation.zcard(key);
    }

    @Override
    public Double zscore(String key, String member) {
        return redisOperation.zscore(key, member);
    }

    @Override
    public List<String> zrangeByIndex(String key, long start, long end) {
        return redisOperation.zrangeByIndex(key, start, end);
    }

    @Override
    public List<String> zrangeByScore(String key, double min, double max) {
        return redisOperation.zrangeByScore(key, min, max);
    }

    @Override
    public List<String> zrevrangeByIndex(String key, long start, long end) {
        return redisOperation.zrevrangeByIndex(key, start, end);
    }

    @Override
    public List<String> zrevrangeByScore(String key, double max, double min) {
        return redisOperation.zrevrangeByScore(key, max, min);
    }

    @Override
    public List<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return redisOperation.zrangeByScore(key, min, max, offset, count);
    }

    @Override
    public List<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return redisOperation.zrevrangeByScore(key, max, min, offset, count);
    }

    @Override
    public List<Tuple> zrangeWithScores(String key, long start, long end) {
        return redisOperation.zrangeWithScores(key, start, end);
    }

    @Override
    public List<Tuple> zrangeByScoreWithScore(String key, double min, double max) {
        return redisOperation.zrangeByScoreWithScore(key, min, max);
    }

    @Override
    public List<Tuple> zrangeByScoreWithScore(String key, double min, double max, int offset, int count) {
        return redisOperation.zrangeByScoreWithScore(key, min, max, offset, count);
    }

    @Override
    public List<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return redisOperation.zrevrangeWithScores(key, start, end);
    }

    @Override
    public List<Tuple> zrevrangeByScoreWithScore(String key, double min, double max) {
        return redisOperation.zrevrangeByScoreWithScore(key, min, max);
    }

    @Override
    public List<Tuple> zrevrangeByScoreWithScore(String key, double max, double min, int offset, int count) {
        return redisOperation.zrevrangeByScoreWithScore(key, max, min, offset, count);
    }

    @Override
    public Long zunionstore(String dstkey, String... sets) {
        return redisOperation.zunionstore(dstkey, sets);
    }

    @Override
    public Long zunionstore(String dstkey, ZParams params, String... sets) {
        return redisOperation.zunionstore(dstkey, params, sets);
    }

    @Override
    public Long zinterstore(String dstkey, String... sets) {
        return redisOperation.zinterstore(dstkey, sets);
    }

    @Override
    public Long zinterstore(String dstkey, ZParams params, String... sets) {
        return redisOperation.zinterstore(dstkey, params, sets);
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor) {
        return redisOperation.zscan(key, cursor);
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        return redisOperation.zscan(key, cursor, params);
    }

    @Override
    public Long lpush(String key, String... values) {
        return redisOperation.lpush(key, values);
    }

    @Override
    public String lpop(String key) {
        return redisOperation.lpop(key);
    }

    @Override
    public String rpop(String key) {
        return redisOperation.rpop(key);
    }

    @Override
    public String ltrim(String key, Long start, Long stop) {
        return redisOperation.ltrim(key, start, stop);
    }

    @Override
    public Long llen(String key) {
        return redisOperation.llen(key);
    }

    @Override
    public String lindex(String key, Long index) {
        return redisOperation.lindex(key, index);
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time) {
        return redisOperation.set(key, value, nxxx, expx, time);
    }

    @Override
    public Object evalsha(String sha1, int keyCount, String... params) {
        return redisOperation.evalsha(sha1, keyCount, params);
    }

    @Override
    public String scriptLoad(String script) {
        return redisOperation.scriptLoad(script);
    }

    @Override
    public Boolean scriptExists(String sha1) {
        return redisOperation.scriptExists(sha1);
    }

    @Override
    public Object eval(String script, int keyCount, String... params) {
        return redisOperation.eval(script, keyCount, params);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String redisBeanName = this.redisBeanName();
        if (redisBeanName == null) {
            return;
        }
        JedisPool jedisPool = this.beanFactory.getBean(redisBeanName, JedisPool.class);
        this.redisOperation = new JedisOperation(jedisPool);
    }

    @Override
    public String rename(String oldKey, String newKey) {
        return redisOperation.rename(oldKey, newKey);
    }

    @Override
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        redisOperation.subscribe(jedisPubSub, channels);
    }

    @Override
    public Long pfadd(String key, String... values) {
        return redisOperation.pfadd(key, values);
    }

    @Override
    public Long pfcount(String key) {
        return redisOperation.pfadd(key);
    }

    @Override
    public Long publish(String channel, String message) {
        return redisOperation.publish(channel, message);
    }
}
