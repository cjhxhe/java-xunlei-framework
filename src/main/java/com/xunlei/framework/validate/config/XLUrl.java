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

    String emptyMessage() default "{label} cannot be empty";

    String patternMessage() default "{label} is not a valid pattern";

    String label() default "";

}
