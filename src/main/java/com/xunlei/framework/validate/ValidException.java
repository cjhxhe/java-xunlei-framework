package com.xunlei.framework.validate;

/**
 * 验证器异常
 */
public class ValidException extends RuntimeException {

    public ValidException(Exception e) {
        super(e);
    }

    public ValidException(String msg, Exception e) {
        super(msg, e);
    }

    public ValidException(String msg) {
        super(msg);
    }
}
