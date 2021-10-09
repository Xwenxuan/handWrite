package com.spring;

/**
 * @author winsonxiao
 * @ClassName BeanPostProcessor
 * @date 2021/10/9、2:52 下午
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName);

    Object postProcessAfterInitialization(Object bean, String beanName);
}
