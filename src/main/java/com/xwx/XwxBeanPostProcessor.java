package com.xwx;

import com.spring.BeanPostProcessor;
import com.spring.Component;

/**
 * 创建每个bean都会走这两个方法
 * @author winsonxiao
 * @ClassName xwxBeanPostProcessor
 * @date 2021/10/9、2:54 下午
 */
@Component
public class XwxBeanPostProcessor implements BeanPostProcessor {


    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化前");
        return null;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化后");
        return null;
    }
}
