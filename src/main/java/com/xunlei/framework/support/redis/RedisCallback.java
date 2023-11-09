package com.xunlei.framework.support.redis;

import redis.clients.jedis.JedisCommands;

/**
 * @param V 回调函数的返回值
 */
public interface RedisCallback<V> extends Callback<JedisCommands, V> {

}
