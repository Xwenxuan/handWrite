package com.spring;

/**
 * @author winsonxiao
 * @ClassName BeanPostProcessor
 * @date 2021/10/9γ2:52 δΈε
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName);

    Object postProcessAfterInitialization(Object bean, String beanName);
}
