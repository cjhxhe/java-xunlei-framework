package com.xunlei.framework.validate.config;

import com.xunlei.framework.validate.XLConstraint;
import com.xunlei.framework.validate.impl.XLRangeValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>数值范围验证</p>
 */
@XLConstraint(validatedBy = XLRangeValidator.class)
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface XLRange {

    int min() default Integer.MIN_VALUE;

    int max() default Integer.MAX_VALUE;

    String label() default "";

    String minMessage() default "{label} cannot be less than {min}";

    String maxMessage() default "{label} cannot be greater than {max}";

    String eqMessage() default "{label} should be {min}";
}
