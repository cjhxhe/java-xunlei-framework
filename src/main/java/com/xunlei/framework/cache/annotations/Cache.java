/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.annotations;

import com.xunlei.framework.cache.CacheLevel;
import com.xunlei.framework.cache.KeyBuilder;
import com.xunlei.framework.cache.impl.DefaultKeyBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被标注的类必须实现PropertyMapper接口，
 * 如果没有实现接口默认采用反射机制将结果集映射到实体Bean
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cache {

    /**
     * This is the primary key of prefix cache
     * default is class name
     */
    String value() default "";


    CacheLevel level() default CacheLevel.REDIS;


    Class<? extends KeyBuilder> keyBuilder() default DefaultKeyBuilder.class;

    /**
     * 失效时间，单位：秒
     * 一旦设置，该表缓存所有的Key都会自动创建有效期，包括单表Set和分组Key
     * -1 为永久
     */
    int expire() default -1;

}
