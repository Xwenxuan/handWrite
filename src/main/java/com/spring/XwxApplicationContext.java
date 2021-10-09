package com.spring;

import com.sun.tools.javac.util.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author winsonxiao
 * @ClassName XwxApplicationContext
 * @date 2021/10/8、5:29 下午
 */
public class XwxApplicationContext {

    private Class configClass;

    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<BeanPostProcessor>();
    public XwxApplicationContext(Class configClass) {
        this.configClass = configClass;
        //扫描
        scan(configClass);

        for(String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")) {
                Object bean = createBean(beanDefinition, beanName);
                singletonObjects.put(beanName, bean);
            }
        }

    }

    public Object createBean(BeanDefinition beanDefinition, String beanName) {
        Class clazz =  beanDefinition.getClazz();
        try {
            Object o = clazz.getDeclaredConstructor().newInstance();
            //依赖注入 Autowired
            for(Field declaredField : clazz.getDeclaredFields()) {
                if(declaredField.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(declaredField.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(o,bean);
                }
            }

            //初始化前干的是
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                beanPostProcessor.postProcessBeforeInitialization(o, beanName);
            }
            //初始化

            //BeanPostProcessor 初始化前后可以做的事情
            //初始化后干的事
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                beanPostProcessor.postProcessAfterInitialization(o,beanName);
            }
            return o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
    private void scan(Class configClass) {
        //解析配置类
        //ComponentScan注解-->扫描路径-->扫描
        ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value(); //获取到扫描路径

        //扫描
        ClassLoader classLoader = XwxApplicationContext.class.getClassLoader();
        URL resource =  classLoader.getResource(path.replace('.','/'));
        File file = new File(resource.getFile());
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File f : files) {
                if(f.getName().endsWith(".class")) {
                    String className = path + "." + f.getName().replace(".class", "");
                    try {
                        Class<?> clazz = classLoader.loadClass(className);
                        if(clazz.isAnnotationPresent(Component.class)) {

                            if(BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                //判断两个类型是否相等 或者继承关系
                                try {
                                    BeanPostProcessor instance = (BeanPostProcessor) clazz.getDeclaredConstructor().newInstance();
                                    beanPostProcessorList.add(instance);
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                }
                            }
                            //如果当前类有Component注解 则交给spring管理 表示是Bean对象
                            // 判断是单例Bean还是原型Bean
                            //BeanDefinition
                            Component componentAnnotation = clazz.getAnnotation(Component.class);
                            String beanName = componentAnnotation.value();
                            if(beanName == null || beanName.equals("")) {
                                beanName = clazz.getName();
                            }
                            BeanDefinition beanDefinition = new BeanDefinition();
                            if(clazz.isAnnotationPresent(Scope.class)) {
                                //如果有Scope注解
                                Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                 beanDefinition.setScope(scopeAnnotation.value());
                            } else {
                                //默认单例模式
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinition.setClazz(clazz);
                            beanDefinitionMap.put(beanName,beanDefinition);

                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Object getBean(String beanName) {
        if(beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")) {
                return singletonObjects.get(beanName);
            }else {
                Object bean = createBean(beanDefinition,beanName);
                return bean;
            }
        }
        return null;
    }
}
