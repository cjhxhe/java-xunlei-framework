package com.xunlei.framework.cache.seriaziler;


@SuppressWarnings("serial")
public class SerializationException extends Exception {

    public SerializationException(String msg) {
        super(msg);
    }

    public SerializationException(String msg, Throwable e) {
        super(msg, e);
    }

}
