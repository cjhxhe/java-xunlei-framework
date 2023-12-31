/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用该标记当某个属性不需要被缓存时
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Transient {

}
