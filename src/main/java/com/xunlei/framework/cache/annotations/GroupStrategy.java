/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.annotations;

import com.xunlei.framework.cache.config.Configuration;

import java.lang.annotation.*;

/**
 * 如果缓存实体类支持多分组的情况下，需要为分组接口方法指定分组名称
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface GroupStrategy {

    /**
     * 该属性的取值是缓存实体类的其中一个分组名称
     * 该参数是必须指定的参数
     */
    String value() default Configuration.DEFAULT_GROUP_NAME;

}
