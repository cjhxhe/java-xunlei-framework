package com.xunlei.framework.validate;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证器标志类，所以自定义的验证器注解都应该在注解类上标注注解
 */
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface XLConstraint {

    /**
     * 注解需要指定实现的验证器类
     */
    Class<? extends ConstraintValidator<?, ?>> validatedBy();
}
