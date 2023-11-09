package com.xunlei.framework.support.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 抽象缓存操作类实现，实现了单机或分布式集群环境下共有的操作，
 * 在单机和集群环境有差异的操作留给子类去实现
 */
public abstract class AbstractRedisOperation implements RedisOperation {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String get(final String key) {
        return exec(new RedisCallback<String>() {
            @Override
            public String doCallback(JedisCommands jedis) {
                return jedis.get(key);
            }
        });
    }

    @Override
    public String set(final String key, final int seconds, final String value) {
        return exec(new RedisCallback<String>() {
            @Override
            public String doCallback(JedisCommands jedis) {
                return jedis.setex(key, seconds, value);
            }
        });
    }

    @Override
    public String set(final String key, final String value, final String nxxx,
                      final String expx, final long time) {
        return exec(new RedisCallback<String>() {
            @Override
            public String doCallback(JedisCommands jedis) {
                return jedis.set(key, value, nxxx, expx, time);
            }
        });
    }

    @Override
    public String set(final String key, final String value) {
        return exec(new RedisCallback<String>() {
            @Override
            public String doCallback(JedisCommands jedis) {
                return jedis.set(key, value);
            }
        });
    }

    @Override
    public Long expire(final String key, final Integer seconds) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.expire(key, seconds);
            }
        });
    }

    @Override
    public Long expireAt(final String key, final long unixTime) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.expireAt(key, unixTime);
            }
        });
    }

    @Override
    public Long persist(final String key) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.persist(key);
            }
        });
    }

    @Override
    public Long ttl(final String key) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.ttl(key);
            }
        });
    }

    @Override
    public Long incr(final String key) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.incr(key);
            }
        });

    }

    @Override
    public Long incrby(final String key, final Long increment) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.incrBy(key, increment);
            }
        });
    }

    @Override
    public Long decr(final String key) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.decr(key);
            }
        });
    }

    @Override
    public Long decrby(final String key, final Long decrement) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.decrBy(key, decrement);
            }
        });
    }

    @Override
    public boolean exists(final String key) {
        return exec(new RedisCallback<Boolean>() {
            @Override
            public Boolean doCallback(JedisCommands jedis) {
                return jedis.exists(key);
            }
        });
    }

    @Override
    public Long lrem(final String key, final Long count, final String value) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.lrem(key, count, value);
            }
        });
    }

    @Override
    public Long rpush(final String key, final String... values) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.rpush(key, values);
            }
        });
    }

    @Override
    public Long lpush(final String key, final String... values) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.lpush(key, values);
            }
        });
    }

    @Override
    public String lpop(final String key) {
        return exec(new RedisCallback<String>() {
            @Override
            public String doCallback(JedisCommands jedis) {
                return jedis.lpop(key);
            }
        });
    }

    @Override
    public String rpop(final String key) {
        return exec(new RedisCallback<String>() {
            @Override
            public String doCallback(JedisCommands jedis) {
                return jedis.rpop(key);
            }
        });
    }

    @Override
    public List<String> lrange(final String key, final Long start, final Long end) {
        return exec(new RedisCallback<List<String>>() {
            @Override
            public List<String> doCallback(JedisCommands jedis) {
                return jedis.lrange(key, start, end);
            }
        });
    }

    @Override
    public String lset(final String key, final Long index, final String value) {
        return exec(new RedisCallback<String>() {
            @Override
            public String doCallback(JedisCommands jedis) {
                return jedis.lset(key, index, value);
            }
        });
    }

    @Override
    public String ltrim(final String key, final Long start, final Long end) {
        return exec(new RedisCallback<String>() {
            @Override
            public String doCallback(JedisCommands jedis) {
                return jedis.ltrim(key, start, end);
            }
        });
    }

    @Override
    public Long llen(final String key) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.llen(key);
            }
        });
    }

    @Override
    public String lindex(final String key, final Long index) {
        return exec(new RedisCallback<String>() {
            @Override
            public String doCallback(JedisCommands jedis) {
                return jedis.lindex(key, index);
            }
        });
    }

    @Override
    public String hmset(final String key, final Map<String, String> fieldValues) {
        return exec(new RedisCallback<String>() {
            @Override
            public String doCallback(JedisCommands jedis) {
                return jedis.hmset(key, fieldValues);
            }
        });
    }

    @Override
    public Long hincrBy(final String key, final String field, final Long increment) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.hincrBy(key, field, increment);
            }
        });
    }

    @Override
    public Double hincrByFloat(final String key, final String field, final Double increment) {
        return exec(new RedisCallback<Double>() {
            @Override
            public Double doCallback(JedisCommands jedis) {
                return jedis.hincrByFloat(key, field, increment);
            }
        });
    }

    @Override
    public List<String> hmget(final String key, final String... fields) {
        return exec(new RedisCallback<List<String>>() {
            @Override
            public List<String> doCallback(JedisCommands jedis) {
                return jedis.hmget(key, fields);
            }
        });
    }

    @Override
    public String hget(final String key, final String field) {
        return exec(new RedisCallback<String>() {
            @Override
            public String doCallback(JedisCommands jedis) {
                return jedis.hget(key, field);
            }
        });
    }

    @Override
    public Map<String, String> hgetAll(final String key) {
        return exec(new RedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doCallback(JedisCommands jedis) {
                return jedis.hgetAll(key);
            }
        });
    }

    @Override
    public Set<String> hkeys(final String key) {
        return exec(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doCallback(JedisCommands jedis) {
                return jedis.hkeys(key);
            }
        });
    }

    @Override
    public Long hlen(final String key) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.hlen(key);
            }
        });
    }

    @Override
    public Long hdel(final String key, final String... fields) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.hdel(key, fields);
            }
        });
    }

    @Override
    public boolean hset(final String key, final String field, final String value) {
        return exec(new RedisCallback<Boolean>() {
            @Override
            public Boolean doCallback(JedisCommands jedis) {
                return jedis.hset(key, field, value) > 0;
            }
        });
    }

    @Override
    public boolean hexists(final String key, final String field) {
        return exec(new RedisCallback<Boolean>() {
            @Override
            public Boolean doCallback(JedisCommands jedis) {
                return jedis.hexists(key, field);
            }
        });
    }

    @Override
    public Long sadd(final String key, final String... members) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.sadd(key, members);
            }
        });
    }

    @Override
    public Long scard(final String key) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.scard(key);
            }
        });
    }

    @Override
    public Set<String> smembers(final String key) {
        return exec(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doCallback(JedisCommands jedis) {
                return jedis.smembers(key);
            }
        });
    }

    @Override
    public boolean sismember(final String key, final String member) {
        return exec(new RedisCallback<Boolean>() {
            @Override
            public Boolean doCallback(JedisCommands jedis) {
                return jedis.sismember(key, member);
            }

        });
    }

    @Override
    public Long srem(final String key, final String... members) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.srem(key, members);
            }
        });
    }

    @Override
    public List<String> srandmember(final String key, final Integer count) {
        return exec(new RedisCallback<List<String>>() {
            @Override
            public List<String> doCallback(JedisCommands jedis) {
                return jedis.srandmember(key, count);
            }
        });
    }

    @Override
    public Set<String> sinter(final String... keys) {
        return exec(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doCallback(JedisCommands jedis) {
                if (jedis instanceof Jedis) {
                    return ((Jedis) jedis).sinter(keys);
                }
                if (jedis instanceof ShardedJedis) {
                    throw new UnsupportedOperationException("分库缓存不支持该方法");
                }
                return null;
            }
        });
    }

    @Override
    public Long zadd(final String key, final Double score, final String member) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.zadd(key, score, member);
            }
        });
    }

    @Override
    public Long zadd(final String key, final Map<String, Double> scoreMembers) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.zadd(key, scoreMembers);
            }
        });
    }

    @Override
    public Long zrem(final String key, final String... member) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.zrem(key, member);
            }
        });
    }

    @Override
    public Long zremrangeByRank(final String key, final long start, final long stop) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.zremrangeByRank(key, start, stop);
            }
        });
    }

    @Override
    public Long zremrangeByScore(final String key, final double min, final double max) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.zremrangeByScore(key, min, max);
            }
        });
    }

    @Override
    public Long zremrangeByScore(final String key, final String min, final String max) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.zremrangeByScore(key, min, max);
            }
        });
    }

    @Override
    public Double zincrby(final String key, final double score, final String member) {
        return exec(new RedisCallback<Double>() {
            @Override
            public Double doCallback(JedisCommands jedis) {
                return jedis.zincrby(key, score, member);
            }
        });
    }

    @Override
    public Long zrank(final String key, final String member) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.zrank(key, member);
            }
        });
    }

    @Override
    public Long zrevrank(final String key, final String member) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.zrevrank(key, member);
            }
        });
    }

    @Override
    public Long zcount(final String key, final double min, final double max) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.zcount(key, min, max);
            }
        });
    }

    @Override
    public Long zcard(final String key) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.zcard(key);
            }
        });
    }

    @Override
    public Double zscore(final String key, final String member) {
        return exec(new RedisCallback<Double>() {
            @Override
            public Double doCallback(JedisCommands jedis) {
                return jedis.zscore(key, member);
            }
        });
    }

    @Override
    public Set<String> zrangeByIndex(final String key, final long start, final long end) {
        return exec(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doCallback(JedisCommands jedis) {
                return jedis.zrange(key, start, end);
            }
        });
    }

    @Override
    public Set<String> zrangeByScore(final String key, final double min, final double max) {
        return exec(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doCallback(JedisCommands jedis) {
                return jedis.zrangeByScore(key, min, max);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScore(final String key, final double min, final double max) {
        return exec(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doCallback(JedisCommands jedis) {
                return jedis.zrangeByScoreWithScores(key, min, max);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScore(final String key, final double min, final double max, final int offset, final int count) {
        return exec(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doCallback(JedisCommands jedis) {
                return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByIndex(final String key, final long start, final long end) {
        return exec(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doCallback(JedisCommands jedis) {
                return jedis.zrevrange(key, start, end);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
        return exec(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doCallback(JedisCommands jedis) {
                return jedis.zrevrangeByScore(key, max, min);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScore(final String key, final double min, final double max) {
        return exec(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doCallback(JedisCommands jedis) {
                return jedis.zrevrangeByScoreWithScores(key, min, max);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScore(final String key, final double max, final double min, final int offset, final int count) {
        return exec(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doCallback(JedisCommands jedis) {
                return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Set<String> zrangeByScore(final String key, final double min, final double max, final int offset,
                                     final int count) {
        return exec(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doCallback(JedisCommands jedis) {
                return jedis.zrangeByScore(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByScore(final String key, final double max, final double min,
                                        final int offset, final int count) {
        return exec(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doCallback(JedisCommands jedis) {
                return jedis.zrevrangeByScore(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeWithScores(final String key, final long start, final long end) {
        return exec(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doCallback(JedisCommands jedis) {
                return jedis.zrangeWithScores(key, start, end);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long end) {
        return exec(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doCallback(JedisCommands jedis) {
                return jedis.zrevrangeWithScores(key, start, end);
            }
        });
    }

    @Override
    public Long zunionstore(final String dstkey, final String... sets) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                if (jedis instanceof Jedis) {
                    return ((Jedis) jedis).zunionstore(dstkey, sets);
                }
                if (jedis instanceof ShardedJedis) {
                    throw new UnsupportedOperationException("分库缓存不支持该方法");
                }
                return null;
            }
        });
    }

    @Override
    public Long zunionstore(final String dstkey, final ZParams params, final String... sets) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                if (jedis instanceof Jedis) {
                    return ((Jedis) jedis).zunionstore(dstkey, params, sets);
                }
                if (jedis instanceof ShardedJedis) {
                    throw new UnsupportedOperationException("分库缓存不支持该方法");
                }
                return null;
            }
        });
    }

    @Override
    public Long zinterstore(final String dstkey, final String... sets) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                if (jedis instanceof Jedis) {
                    return ((Jedis) jedis).zinterstore(dstkey, sets);
                }
                if (jedis instanceof ShardedJedis) {
                    throw new UnsupportedOperationException("分库缓存不支持该方法");
                }
                return null;
            }
        });
    }

    @Override
    public Long zinterstore(final String dstkey, final ZParams params, final String... sets) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                if (jedis instanceof Jedis) {
                    return ((Jedis) jedis).zinterstore(dstkey, params, sets);
                }
                if (jedis instanceof ShardedJedis) {
                    throw new UnsupportedOperationException("分库缓存不支持该方法");
                }
                return null;
            }
        });
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor) {
        return exec(new RedisCallback<ScanResult<Tuple>>() {
            @Override
            public ScanResult<Tuple> doCallback(JedisCommands jedis) {
                return jedis.zscan(key, cursor);
            }
        });
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        return exec(new RedisCallback<ScanResult<Tuple>>() {
            @Override
            public ScanResult<Tuple> doCallback(JedisCommands jedis) {
                return jedis.zscan(key, cursor, params);
            }
        });
    }

    @Override
    public Long pfadd(String key, String... values) {
        return exec(new RedisCallback<Long>() {
            @Override
            public Long doCallback(JedisCommands jedis) {
                return jedis.pfadd(key, values);
            }
        });
    }

    /**
     * @param callback
     * @return
     */
    public abstract <T> T exec(final RedisCallback<T> callback);
}
