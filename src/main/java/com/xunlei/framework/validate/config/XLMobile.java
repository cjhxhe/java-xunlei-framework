package com.xunlei.framework.validate.config;

import com.xunlei.framework.validate.XLConstraint;
import com.xunlei.framework.validate.impl.XLMobileValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 可以匹配字符串类型的手机号码，也可以匹配数字类型的手机号码
 */
@XLConstraint(validatedBy = XLMobileValidator.class)
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface XLMobile {

    String lengthMessage() default "mobile length is incorrect";

    String patternMessage() default "mobile pattern is incorrect";

}
