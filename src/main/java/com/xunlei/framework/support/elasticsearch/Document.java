package com.xunlei.framework.support.elasticsearch;


import java.lang.annotation.*;

/**
 * 标识要持久化到Elasticsearch的域对象。
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Document {

    /**
     * 索引名称，默认使用类名
     *
     * @return
     */
    String value() default "";

    /**
     * 索引名称，默认使用类名
     *
     * @return
     */
    String index() default "";

    /**
     * 唯一标识属性名称
     *
     * @return
     */
    String keyProperty();
}
