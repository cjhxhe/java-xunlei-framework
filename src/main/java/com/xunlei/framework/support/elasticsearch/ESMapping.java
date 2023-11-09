package com.xunlei.framework.support.elasticsearch;

import java.lang.annotation.*;

/**
 * 用来标记elasticsearch字段的映射类型
 *
 * @author qhluo
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ESMapping {

    /**
     * 字段类型
     *
     * @return
     */
    MappingType type() default MappingType.Auto;

    /**
     * 索引方式
     *
     * @return
     */
    IndexType index() default IndexType.Not_Analyzed;
}
