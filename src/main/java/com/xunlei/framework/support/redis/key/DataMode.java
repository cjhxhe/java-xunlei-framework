package com.xunlei.framework.support.redis.key;


/**
 * Redis现有支持的数据类型枚举
 */
public enum DataMode {
    Incr,
    Decr,
    String,
    Hash,
    List,
    Set,
    Sortedset,
    Pubsub
}
