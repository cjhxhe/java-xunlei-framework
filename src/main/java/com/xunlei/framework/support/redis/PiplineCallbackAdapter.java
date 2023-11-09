package com.xunlei.framework.support.redis;

import redis.clients.jedis.Pipeline;

public abstract class PiplineCallbackAdapter implements PiplineCallback {
    @Override
    public void doCallback(Pipeline pipeline) {
        // do nothing
    }
}
