package com.xunlei.framework.common.util;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

public class SpringContextUtils {

    private static ApplicationContext context;

    public static void initContext(ApplicationContext context) {
        SpringContextUtils.context = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        try {
            return context.getBean(clazz);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    public static Object getBean(String beanName) {
        try {
            return context.getBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        try {
            return context.getBean(beanName, clazz);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    public static <T> T getBean(ApplicationContext context, Class<T> clazz) {
        try {
            return context.getBean(clazz);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    public static Object getBean(ApplicationContext context, String beanName) {
        try {
            return context.getBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }
}
