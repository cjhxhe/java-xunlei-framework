package com.xunlei.framework.rule.impl;

import com.xunlei.framework.common.util.MapUtils;
import com.xunlei.framework.rule.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * The Rule for java class code
 */
public class JavaRuleEngine implements RuleEngine {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private RuleContext ruleContext;

    public JavaRuleEngine() {
    }

    @Override
    public <T> T eval(Rule rule) throws RuleException {
        return __eval(rule, MapUtils.gmap());
    }

    @Override
    public <T> T eval(Rule rule, Object... dataMap) throws RuleException {
        if (dataMap.length == 1 && dataMap[0] == null) {
            return eval(rule);
        }
        return __eval(rule, MapUtils.gmap(dataMap));
    }

    @SuppressWarnings("unchecked")
    public <T> T __eval(Rule rule, Map<String, Object> local) throws RuleException {
        RuleExecutor re = RuleClassManager.getInstance().get(rule);
        Object o = re.eval(ruleContext, new RuleContext(local));
        if (o == null) {
            return null;
        }
        return (T) o;
    }

    @Override
    public void setContext(RuleContext context) {
        this.ruleContext = context;
    }

    @Override
    public boolean verifySyntax(Rule rule) throws RuleException {
        return RuleClassManager.getInstance().verifyRuleSyntax(rule);
    }

}
