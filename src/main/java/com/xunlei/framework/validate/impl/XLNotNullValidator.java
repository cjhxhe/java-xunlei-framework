package com.xunlei.framework.validate.impl;

import com.xunlei.framework.common.util.MsgUtils;
import com.xunlei.framework.common.util.StringUtils;
import com.xunlei.framework.validate.ConstraintValidator;
import com.xunlei.framework.validate.ValidResult;
import com.xunlei.framework.validate.config.XLNotNull;

/**
 * @see XLNotNull
 */
public class XLNotNullValidator implements ConstraintValidator<XLNotNull, Object> {

    @Override
    public ValidResult isValid(XLNotNull config, String fieldName, Object value, Object parent) {

        String label = config.label();
        if (StringUtils.isEmpty(label)) {
            label = "参数" + fieldName;
        }
        if (value == null) {
            String msg = MsgUtils.formatTpl(config.message(), "label", label);
            return ValidResult.fail(msg);
        }

        return ValidResult.ok();
    }
}
