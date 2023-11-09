package com.xunlei.framework.validate.impl;

import com.xunlei.framework.common.util.MsgUtils;
import com.xunlei.framework.common.util.StringUtils;
import com.xunlei.framework.validate.ConstraintValidator;
import com.xunlei.framework.validate.ValidException;
import com.xunlei.framework.validate.ValidResult;
import com.xunlei.framework.validate.config.XLRange;

/**
 * 数值范围验证器
 */
public class XLRangeValidator implements ConstraintValidator<XLRange, Object> {

    @Override
    public ValidResult isValid(XLRange config, String fieldName, Object value, Object parent) throws ValidException {
        if (value == null) {
            return ValidResult.ok();
        }
        String label = config.label();
        if (StringUtils.isEmpty(label)) {
            label = "参数" + fieldName;
        }
        int val = (int) value;
        if (val < config.min()) {
            String msg = MsgUtils.formatTpl(config.minMessage(), "label", label, "min", config.min());
            return ValidResult.fail(msg);
        }
        if (val > config.max()) {
            String msg = MsgUtils.formatTpl(config.maxMessage(), "label", label, "max", config.max());
            return ValidResult.fail(msg);
        }
        if (config.min() == config.max()) {
            if (val != config.min()) {
                String msg = MsgUtils.formatTpl(config.eqMessage(), "label", label, "min", config.min());
                return ValidResult.fail(msg);
            }
        }
        return ValidResult.ok();
    }
}
