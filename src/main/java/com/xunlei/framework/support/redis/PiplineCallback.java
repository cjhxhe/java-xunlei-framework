package com.xunlei.framework.support.redis;

import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedisPipeline;


/**
 * callback for redis pipline
 */
public interface PiplineCallback {

    void doCallback(Pipeline pipeline);

    void doCallback(ShardedJedisPipeline pipeline);

}
