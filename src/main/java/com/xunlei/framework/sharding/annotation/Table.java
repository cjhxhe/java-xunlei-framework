package com.xunlei.framework.sharding.annotation;

import java.lang.annotation.*;

/**
 * 该注解的用途主要配置分库对应的表名，通常标注在Mapper或DAO Class定义上
 * 该注解支持作用于被标注目标的子类
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Table {


    /**
     * 表名，大小写不敏感
     *
     * @return
     */
    String value() default "";

    /**
     * 数据库名
     *
     * @return
     */
    String schema() default "";


    /**
     * 切分所使用的属性名称，暂无用途，保留元属性
     *
     * @return
     */
    String shardBy() default "";

}
