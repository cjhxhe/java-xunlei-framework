/*!
 * Copyright 2018, Julun, Inc.
 */

package com.xunlei.framework.validate.impl;

import com.xunlei.framework.common.util.MsgUtils;
import com.xunlei.framework.common.util.StringUtils;
import com.xunlei.framework.validate.ConstraintValidator;
import com.xunlei.framework.validate.ValidResult;
import com.xunlei.framework.validate.config.XLNotEmpty;

import java.util.Collection;
import java.util.Map;

/**
 * @see XLNotEmpty
 */
public class XLNotEmptyValidator implements ConstraintValidator<XLNotEmpty, Object> {

    @Override
    public ValidResult isValid(XLNotEmpty config, String fieldName, Object value, Object parent) {
        String label = config.label();
        if (StringUtils.isEmpty(label)) {
            label = "参数" + fieldName;
        }
        // 先验证是否为null
        if (value == null) {
            return newStringMessageResult(config, label);
        }

        if (value instanceof String) {
            // 字符串类型
            if (((String) value).trim().length() == 0) {
                return newStringMessageResult(config, label);
            }
        } else if (value instanceof Collection) {
            // 集合类型
            if (((Collection) value).size() == 0) {
                return newCollectionMessageResult(config, label);
            }
        } else if (value instanceof Map) {
            // Map类型
            if (((Map) value).size() == 0) {
                return newCollectionMessageResult(config, label);
            }
        } else if (value.getClass().isArray()) {
            // 数组类型
            if (((Object[]) value).length == 0) {
                return newCollectionMessageResult(config, label);
            }
        }

        return ValidResult.ok();
    }


    private ValidResult newStringMessageResult(XLNotEmpty config, String label) {
        String msg = MsgUtils.formatTpl(config.stringMessage(), "label", label);
        return ValidResult.fail(msg);
    }

    private ValidResult newCollectionMessageResult(XLNotEmpty config, String label) {
        String msg = MsgUtils.formatTpl(config.collectionMessage(), "label", label);
        return ValidResult.fail(msg);
    }

    public static void main(String[] args) {
        // TODO 数组待测试
        Object obj = new String[]{"a", "b", "c"};

        if (((Object[]) obj).length == 0) {
            System.out.println("数组长度为0");
        } else {
            System.out.println("数组长度不为0");
        }
    }

}
