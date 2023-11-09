/*
 * @(#) ObjectRedDistributedLock.java 1.0.0 2017年7月3日 下午7:52:45
 */
package com.xunlei.framework.support.lock;

import com.xunlei.framework.sharding.transaction.TransactionLock;
import com.xunlei.framework.support.redis.RedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

/**
 * 布式锁实现基于Redis，这里主要在单机Redis做的实现。
 *
 * <p>实现的几个关键点：</p>
 * <ul>
 * 	<li>基于set nx ex，获得锁并且设置过期时间，如果程序以外终止，Lock会走超时流程，其它进程还是有机会获得锁的</li>
 *  <li>删除的对象必须只有锁持有者才能删除，Lua脚本做原子性删除，避免删错</li>
 * </ul>
 * <p>
 * 未解决的问题：
 * 1、如果业务执行时间超过了锁的有效期，会存在多个进程同时获得锁。
 * 2、如果Jedis挂了，Slave会立刻起来，但由于锁的信息没有及时同步给Slave Redis，其它进程也能获取到锁
 * <p>
 * 对于以上两个问题，尽量保证事务时间比redis锁更短，比如设置一个足够大的超时流程，比如60秒有效期
 * 第二个问题如果业务需要强一致性的锁，可以通过数据库行锁实现
 * <p>
 * 该类的提供是为了解决高效率，但容许业务在极端情况下出现不一致的情况。
 */
public class RedDistributedLockImpl implements TransactionLock {

    static final Logger logger = LoggerFactory.getLogger(RedDistributedLockImpl.class);
    // 仅当相等才能删除
    private final static String DEL_WHEN_EQUAL_LUA_SCRIPT = "if redis.call('get',KEYS[1]) == ARGV[1] then  return redis.call('del',KEYS[1])  else return 0 end";
    // lua脚本的SHA值
    private static String delWhenEqualSha;

    // 等待锁的时间:10秒
    private long timeout = 10L;
    // 锁自动释放时间：秒
    private long lockValidityTime = 60L;
    // 锁的KEY
    private String lockKey;
    // 需要外部传入
    private RedisTemplate lockRedisTemplate;

    // 持有锁时锁对应的值
    private String lockIdentityValue;
    // 是否持有用户锁
    private boolean hasLock = false;

    public RedDistributedLockImpl(RedisTemplate lockRedisTemplate, String lockKey) {
        this.lockRedisTemplate = lockRedisTemplate;
        this.lockKey = lockKey;
    }

    public RedDistributedLockImpl(RedisTemplate lockRedisTemplate, String lockKey, long waitLockTimeoutSeconds) {
        this(lockRedisTemplate, lockKey);
        this.timeout = waitLockTimeoutSeconds;
    }

    @Override
    public <T> T waitAndExecute(LockCallback<T> lc) throws LockException {
        try {
            if (this.acquire()) {
                return lc.doCallback();
            }
            return null;
        } finally {
            if (hasObjectLock()) {
                this.release();
            }
        }
    }

    @Override
    public boolean acquire() throws LockException {
        long startMs = System.currentTimeMillis();
        long expMs = startMs + timeout * 1000;
        lockIdentityValue = UUID.randomUUID().toString();
        Random r = new Random();
        // 保证在可内未获取到锁
        while (System.currentTimeMillis() < expMs) {
            // 通过set nxxx expx 保证key和失效时间是原子性的操作
            if ("OK".equalsIgnoreCase(lockRedisTemplate.set(lockKey,
                    lockIdentityValue, "nx", "ex", lockValidityTime))) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Getting the lock[{}] took {}ms", lockKey,
                            (System.currentTimeMillis() - startMs));
                }
                // 获得了锁
                hasLock = true;
                return true;
            }
            try {
                // 休眠一小会，继续抢锁
                // 休息时长在15 - 80ms
                Thread.sleep(15 + r.nextInt(65));
            } catch (InterruptedException ie) {
                throw new LockException(ie);
            }
        }
        throw new LockException("Get Lock Timeout Exception");
    }

    @Override
    public void release() {
        if (!hasObjectLock()) {
            return;
        }
        // 预先加载LUA脚本
        if (delWhenEqualSha == null) {
            delWhenEqualSha = lockRedisTemplate.scriptLoad(DEL_WHEN_EQUAL_LUA_SCRIPT);
        }
        // 使用原子性delWhenEqual删除自己开的锁
        Object obj = lockRedisTemplate.evalsha(delWhenEqualSha, 1, this.lockKey,
                this.lockIdentityValue);
        if (obj != null && ((Long) obj) == 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("Release the lock[{}] object", this.lockKey);
            }
        } else {
            logger.error(
                    "Release Lock[{}] failure, May be hold time is too long.",
                    this.lockKey);
        }
    }

    /**
     * 是否拥有对象锁
     */
    private boolean hasObjectLock() {
        return hasLock;
    }

    @Override
    public Object getLockKey() {
        return this.lockKey;
    }

    public void setLockRedisTemplate(RedisTemplate lockRedisTemplate) {
        this.lockRedisTemplate = lockRedisTemplate;
    }

}
