package com.xunlei.framework.rule.impl;

import com.xunlei.framework.rule.Rule;
import com.xunlei.framework.rule.RuleContext;
import com.xunlei.framework.rule.RuleEngine;
import com.xunlei.framework.rule.RuleException;


public class UnsupportRuleEngine implements RuleEngine {

    @Override
    public <T> T eval(Rule rule) throws RuleException {
        return throwe();
    }

    @Override
    public void setContext(RuleContext context) {
        // do nothing
    }

    @Override
    public <T> T eval(Rule rule, Object... dataMap) throws RuleException {
        return throwe();
    }


    private <T> T throwe() throws RuleException {
        throw new RuleException("The engine does not support!");
    }

    @Override
    public boolean verifySyntax(Rule rule) throws RuleException {
        return throwe();
    }
}
