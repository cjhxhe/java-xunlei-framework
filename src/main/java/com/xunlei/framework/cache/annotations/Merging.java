/*
 * Copyright © 2014 YAOCHEN Corporation, All Rights Reserved
 */
package com.xunlei.framework.cache.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Merging {

    /**
     * target class
     */
    Class<? extends Object> value();

    /**
     * 引用Class
     *
     * @return
     */
    Class<?>[] refrenceClass();

}
