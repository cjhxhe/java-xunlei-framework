package com.xunlei.framework.cache.annotations;

import com.xunlei.framework.cache.KeyBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记实体类一张"迷你表"，意味着这是一张数据量不大的表，
 * 缓存管理器将会缓存一个KEY，包含所有实体数据缓存的KEY，
 * 这个KEY将会以Cache的名称加上mini的标记
 *
 * @see KeyBuilder
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MiniTable {


}
