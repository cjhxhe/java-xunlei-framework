package com.xunlei.framework.support.redis;

import redis.clients.jedis.ShardedJedisPipeline;


/**
 * callback for redis sharedpipline
 */
public interface SharedPiplineCallback {

    void doCallback(ShardedJedisPipeline pipeline);

}
