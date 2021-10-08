package com.spring;

/**
 * @author winsonxiao
 * @ClassName XwxApplicationContext
 * @date 2021/10/8、5:29 下午
 */
public class XwxApplicationContext {

    private Class configClass;

    public XwxApplicationContext(Class configClass) {
        this.configClass = configClass;

        //解析配置类
        //ComponentScan注解-->扫描路径-->
    }

    public Object getBean(String beanName) {
        return null;
    }
}
