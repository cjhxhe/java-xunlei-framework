package com.xunlei.framework.rule.impl;

import com.xunlei.framework.rule.Rule;
import com.xunlei.framework.rule.RuleContext;
import com.xunlei.framework.rule.RuleEngine;
import com.xunlei.framework.rule.RuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AroundRuleEngine implements RuleEngine {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private RuleEngine delegate;

    public AroundRuleEngine(RuleEngine delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> T eval(Rule rule) throws RuleException {
        return delegate.eval(rule, new Object[]{});
    }

    @Override
    public <T> T eval(Rule rule, Object... dataMap) throws RuleException {
        check(rule);
        return delegate.eval(rule, dataMap);
    }

    private boolean check(Rule rule) throws RuleException {
        if (rule == null || rule.getRuleText() == null ||
                "".equals(rule.getRuleText())) {
            throw new RuleException("The rules can't be empty");
        }
        return true;
    }

    @Override
    public void setContext(RuleContext context) {
        delegate.setContext(context);
    }

    @Override
    public boolean verifySyntax(Rule rule) throws RuleException {
        return delegate.verifySyntax(rule);
    }

}
