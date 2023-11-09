package com.xunlei.framework.validate.config;

import com.xunlei.framework.validate.XLConstraint;
import com.xunlei.framework.validate.impl.XLUrlValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@XLConstraint(validatedBy = XLUrlValidator.class)
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface XLUrl {

    String emptyMessage() default "{label}不能为空";

    String patternMessage() default "{label}不是一个合法的网址";

    String label() default "";

}
