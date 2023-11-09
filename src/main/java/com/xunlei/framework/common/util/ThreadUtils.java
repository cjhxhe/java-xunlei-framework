package com.xunlei.framework.common.util;

/**
 * 线程操作工具类
 */
public class ThreadUtils {

    public static void call(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
