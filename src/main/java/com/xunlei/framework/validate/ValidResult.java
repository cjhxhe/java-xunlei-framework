/*!
 * Copyright 2018, Julun, Inc.
 */

package com.xunlei.framework.validate;

/**
 * 验证结果封装
 */
public class ValidResult {

    /* true代表通过，false代表失败 */
    private boolean pass;
    /* 当不通过时，错误提示内容 */
    private String message;

    private ValidResult(boolean pass, String message) {
        this.pass = pass;
        this.message = message;
    }

    /**
     * 构建一个验证通过的结果对象实例
     */
    public static ValidResult ok() {
        return new ValidResult(true, null);
    }

    /**
     * 构建一个验证失败的结果对象实例
     */
    public static ValidResult fail(String message) {
        return new ValidResult(false, message);
    }

    public boolean isPass() {
        return pass;
    }

    public String getMessage() {
        return message;
    }
}
