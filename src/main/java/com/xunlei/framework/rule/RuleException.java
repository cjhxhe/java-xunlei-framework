package com.xunlei.framework.rule;


public class RuleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RuleException() {
        super();
    }

    public RuleException(String message) {
        super(message);
    }

    public RuleException(String message, Throwable e) {
        super(message, e);
    }

    public RuleException(Throwable e) {
        super(e);
    }

}
