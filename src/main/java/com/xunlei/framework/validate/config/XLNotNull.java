package com.xunlei.framework.validate.config;

import com.xunlei.framework.validate.XLConstraint;
import com.xunlei.framework.validate.impl.XLNotNullValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证字段不能为 Null，允许任何字段设置为该字段
 */
@XLConstraint(validatedBy = XLNotNullValidator.class)
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface XLNotNull {

    String message() default "{label}不允许为空";

    /**
     * 该配置会替换验证不合法消息的变量
     */
    String label() default "";

}
