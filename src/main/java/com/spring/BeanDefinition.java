package com.spring;

/**
 * @author winsonxiao
 * @ClassName BeanDefinition
 * @date 2021/10/9γ10:35 δΈε
 */
public class BeanDefinition {

    private Class clazz;
    private String scope;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
