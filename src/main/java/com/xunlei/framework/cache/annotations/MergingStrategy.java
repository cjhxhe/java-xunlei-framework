/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.annotations;

import java.lang.annotation.*;

/**
 * 合并注解配置, 起到标示方法的作用
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface MergingStrategy {

}


