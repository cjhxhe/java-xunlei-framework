package com.xunlei.framework.rule;

import com.xunlei.framework.common.util.MapUtils;
import com.xunlei.framework.rule.impl.AroundRuleEngine;
import com.xunlei.framework.rule.impl.JavaRuleEngine;
import com.xunlei.framework.rule.impl.UnsupportRuleEngine;
import com.xunlei.framework.rule.js.JavaScriptRuleEngine;

/**
 * 规则引擎的代理实现，通常使用该类实现规则，而不直接使用规则引擎
 */
public class RuleEngineProxy {

    private Rule rule;
    private RuleContext context;

    public RuleEngineProxy(Rule rule) {
        this(rule, new RuleContext(MapUtils.gmap()));
    }

    public RuleEngineProxy(Rule rule, RuleContext context) {
        this.rule = rule;
        this.context = context;
    }

    public void setContext(RuleContext context) {
        this.context = context;
    }

    public <T> T eval() throws RuleException {
        RuleEngine engine = newEngineImpl(rule.getLanguage());
        engine.setContext(context);
        return engine.eval(rule);
    }

    public <T> T eval(Object... dataMap) throws RuleException {
        RuleEngine engine = newEngineImpl(rule.getLanguage());
        engine.setContext(context);
        return engine.eval(rule, dataMap);
    }

    public boolean verifySyntax() throws RuleException {
        RuleEngine engine = newEngineImpl(rule.getLanguage());
        engine.setContext(context);
        return engine.verifySyntax(rule);
    }

    private RuleEngine newEngineImpl(Language lang) {
        RuleEngine delegate = null;
        switch (lang) {
            case JAVA:
            case GROOVY:
                delegate = new JavaRuleEngine();
                break;
            case JAVASCRIPT:
                delegate = new JavaScriptRuleEngine();
                break;
        }
        if (delegate != null) {
            return new AroundRuleEngine(delegate);
        } else {
            return new UnsupportRuleEngine();
        }
    }

}
