package com.xunlei.framework.validate.config;

import com.xunlei.framework.validate.XLConstraint;
import com.xunlei.framework.validate.impl.XLNotEmptyValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>不允许内容为空的验证器，主要验证</p>
 * <ul>
 *     <li>字符串是否有内容</li>
 *     <li>集合至少包含一项元素</li>
 *     <li>Map至少包含一项元素</li>
 *     <li>数组至少包含一项元素</li>
 * </ul>
 */
@XLConstraint(validatedBy = XLNotEmptyValidator.class)
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface XLNotEmpty {

    /**
     * 作为字符串的提示文本
     *
     * @return
     */
    String stringMessage() default "{label} cannot be empty";

    /**
     * 当作为集合的时候错误提示文本
     *
     * @return
     */
    String collectionMessage() default "{label} must contain at least one item";

    /**
     * 该配置会替换验证不合法消息的变量
     */
    String label() default "";

}
