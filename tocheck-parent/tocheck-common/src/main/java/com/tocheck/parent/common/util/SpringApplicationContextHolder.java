package com.tocheck.parent.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author pangliang
 * @create 2017-05-23 17:54
 **/
public class SpringApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    public static Object getSpringBean(String beanName) {
        return applicationContext == null ? null : applicationContext.getBean(beanName);
    }


    public static Object getSpringBean(Class c) {
        return applicationContext == null ? null : applicationContext.getBean(c);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
