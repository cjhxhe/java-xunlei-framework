package com.xunlei.framework.validate.config;

import com.xunlei.framework.validate.XLConstraint;
import com.xunlei.framework.validate.impl.XLLengthValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>内容长度验证器，支持以下数据类型的验证：</p>
 * <ul>
 *     <li>字符串长度</li>
 *     <li>集合长度</li>
 *     <li>Map长度</li>
 *     <li>数组长度</li>
 * </ul>
 */
@XLConstraint(validatedBy = XLLengthValidator.class)
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface XLLength {

    int min() default -1;

    int max() default -1;

    String label() default "";

    String minMessage() default "{label} at least {min} char(s)";

    String maxMessage() default "{label} at most {max} char(s)";

    String minMaxMessage() default "{label} should be {min} ~ {max} char(s)";

    String eqMessage() default "{label} should be {min} char(s)";
}
