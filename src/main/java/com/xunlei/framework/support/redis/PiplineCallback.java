package com.xunlei.framework.support.redis;

import redis.clients.jedis.Pipeline;

/**
 * callback for redis pipline
 */
public interface PiplineCallback {

    void doCallback(Pipeline pipeline);

}
