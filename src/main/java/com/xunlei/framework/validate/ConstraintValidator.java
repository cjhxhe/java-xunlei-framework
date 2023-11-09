package com.xunlei.framework.validate;

import java.lang.annotation.Annotation;

/**
 * 验证器接口定义，必须实现该接口才算有效的
 *
 * @param <A> 该验证器验证注解
 * @param <V> 被验证值的类型
 */
public interface ConstraintValidator<A extends Annotation, V> {

    /**
     * 验证属性值是否合法
     *
     * @param config    注解配置
     * @param fieldName 属性名称
     * @param value     当前验证的值
     * @param parent    上级对象
     * @return true合法，否则不合法
     */
    ValidResult isValid(A config, String fieldName, V value, Object parent) throws ValidException;

}
