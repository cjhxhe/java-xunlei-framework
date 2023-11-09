package com.xunlei.framework.cache.transaction;

import com.xunlei.framework.cache.storage.JVMCache;
import com.xunlei.framework.support.redis.RedisTemplate;
import com.xunlei.framework.support.transaction.RemoteSenderMorly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 缓存批量发送器，将缓存一次性写入目标服务器
 */
public class BufferedCacheRemoteSender implements RemoteSenderMorly<Command> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String PROVIDER = "EASYOOO_CACHE";

    /**
     * 依赖外部注入
     */
    private RedisTemplate redisTemplate;

    /**
     * 本地缓存引用，for lock
     */
    private final JVMCache jvmCacheObj = new JVMCache();

    public BufferedCacheRemoteSender(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getProviderInfo() {
        return PROVIDER;
    }

    @Override
    public boolean send(Collection<Command> commands) throws Exception {
        // 提取出JVM本地命令及redis缓存命令
        Collection<Command> jvmCommands = new ArrayList<>();
        Collection<Command> redisCommands = new ArrayList<>();
        for (Command cmd : commands) {
            switch (cmd.getLevel()) {
                case JVM:
                    jvmCommands.add(cmd);
                    break;
                case REDIS:
                    redisCommands.add(cmd);
                    break;
                case JVM_TO_REDIS:
                    jvmCommands.add(cmd);
                    redisCommands.add(cmd);
                    break;
                default:
                    break;
            }
        }

        // send redis cache
        DefaultRedisTransactionCallback drtc = new DefaultRedisTransactionCallback(
                redisCommands);

        // lock cache object

        int jSize = jvmCommands.size(), rRize = redisCommands.size();

        if (jSize > 0 && rRize > 0) {
            synchronized (jvmCacheObj.getCacheObject()) {
                synchronized (redisTemplate) {
                    try {
                        redisTemplate.transaction(drtc);
                    } catch (UnsupportedOperationException e) {
                        redisTemplate.pipelined(drtc);
                    }
                }
            }
        } else if (jSize == 0 && rRize > 0) {
            synchronized (redisTemplate) {
                try {
                    redisTemplate.transaction(drtc);
                } catch (UnsupportedOperationException e) {
                    redisTemplate.pipelined(drtc);
                }
            }
        } else {
            logger.info("No command to submit.");
        }
        return true;
    }
}