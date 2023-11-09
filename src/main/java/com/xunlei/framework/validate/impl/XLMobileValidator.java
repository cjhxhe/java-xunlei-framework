package com.xunlei.framework.validate.impl;

import com.xunlei.framework.common.util.StringUtils;
import com.xunlei.framework.validate.ConstraintValidator;
import com.xunlei.framework.validate.ValidResult;
import com.xunlei.framework.validate.config.XLMobile;

import java.util.regex.Pattern;

/**
 * @see XLMobile
 */
public class XLMobileValidator implements ConstraintValidator<XLMobile, Object> {

    static final Pattern MOIBILE = Pattern.compile("^\\d{11}$");

    @Override
    public ValidResult isValid(XLMobile config, String fieldName, Object value, Object parent) {
        if (value == null || StringUtils.isEmpty(value.toString())) {
            return ValidResult.ok();
        }
        // 根据运营商进行匹配
        String mb = value.toString();
        if (MOIBILE.matcher(mb).matches()) {
            return ValidResult.ok();
        }
        return ValidResult.fail(config.patternMessage());
    }
}
