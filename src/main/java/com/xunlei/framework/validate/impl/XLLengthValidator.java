/*!
 * Copyright 2018, Julun, Inc.
 */

package com.xunlei.framework.validate.impl;

import com.xunlei.framework.common.util.MsgUtils;
import com.xunlei.framework.common.util.StringUtils;
import com.xunlei.framework.validate.ConstraintValidator;
import com.xunlei.framework.validate.ValidException;
import com.xunlei.framework.validate.ValidResult;
import com.xunlei.framework.validate.config.XLLength;

import java.util.Collection;
import java.util.Map;

/**
 * @see XLLength
 */
public class XLLengthValidator implements ConstraintValidator<XLLength, Object> {

    @Override
    public ValidResult isValid(XLLength config, String fieldName, Object value,
                               Object parent) throws ValidException {
        // 先验证是否为null
        if (value == null) {
            return ValidResult.ok();
        }

        // 最大长度
        int max = config.max() < 0 ? Integer.MAX_VALUE : config.max();
        int min = config.min() < 0 ? 0 : config.min();
        int len = 0;

        if (value instanceof String) {
            // 字符串类型
            len = ((String) value).trim().length();
        } else if (value instanceof Collection) {
            // 集合类型
            len = ((Collection) value).size();
        } else if (value instanceof Map) {
            // Map类型
            len = ((Map) value).size();
        } else if (value.getClass().isArray()) {
            // 数组
            len = ((Object[]) value).length;
        } else {
            throw new ValidException("该注解" + config + "不支持" + value.getClass() + "类型");
        }

        if (len < min || len > max) {
            return newMessageResult(config, fieldName);
        }

        return ValidResult.ok();
    }

    private ValidResult newMessageResult(XLLength config, String fieldName) {
        String msg = null;

        String label = config.label();
        if (StringUtils.isEmpty(label)) {
            label = "参数" + fieldName;
        }

        if (config.min() >= 0 && config.max() >= 0) {
            if (config.min() == config.max()) {
                msg = MsgUtils.formatTpl(config.eqMessage(),
                        "label", label,
                        "min", config.min());
            } else {
                msg = MsgUtils.formatTpl(config.minMaxMessage(),
                        "label", label,
                        "min", config.min(),
                        "max", config.max());
            }
        } else if (config.min() >= 0) {
            msg = MsgUtils.formatTpl(config.minMessage(),
                    "label", label,
                    "min", config.min());
        } else if (config.max() >= 0) {
            msg = MsgUtils.formatTpl(config.maxMessage(),
                    "label", label,
                    "max", config.max());
        }

        return ValidResult.fail(msg);
    }
}
