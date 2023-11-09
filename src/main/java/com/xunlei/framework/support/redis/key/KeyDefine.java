package com.xunlei.framework.support.redis.key;

/**
 * Redis緩存Key定义接口
 */
public interface KeyDefine {

    KeyMode getKeyMode();

    String getKeyPrefix();

    TTLMode getTTLMode();

    Integer getSecondValue();

    DataMode getDataType();

    String getTTLDateStr();

}
