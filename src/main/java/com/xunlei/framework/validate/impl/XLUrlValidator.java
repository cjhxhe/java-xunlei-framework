/*!
 * Copyright 2018, Julun, Inc.
 */

package com.xunlei.framework.validate.impl;

import com.xunlei.framework.common.util.MsgUtils;
import com.xunlei.framework.common.util.StringUtils;
import com.xunlei.framework.validate.ConstraintValidator;
import com.xunlei.framework.validate.ValidResult;
import com.xunlei.framework.validate.config.XLUrl;

import java.util.regex.Pattern;


public class XLUrlValidator implements ConstraintValidator<XLUrl, String> {

    static final Pattern PATTERN = Pattern.compile("^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(" +
            "([A-Za-z0-9-~]+).)+" +
            "([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$");

    @Override
    public ValidResult isValid(XLUrl config, String fieldName, String url, Object parent) {
        if (StringUtils.isEmpty(url)) {
            return ValidResult.ok();
        }
        String label = config.label();
        if (StringUtils.isEmpty(label)) {
            label = "参数" + fieldName;
        }
        if (!PATTERN.matcher(url).matches()) {
            return ValidResult.fail(MsgUtils.formatTpl(config.patternMessage(), "label", label));
        }

        return ValidResult.ok();
    }
}
