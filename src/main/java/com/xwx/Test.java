package com.xwx;

import com.spring.XwxApplicationContext;

/**
 * @author winsonxiao
 * @ClassName Test
 * @date 2021/10/8γ10:09 δΈε
 */
public class Test {

    public static void main(String[] args) {
        XwxApplicationContext xwxApplicationContext = new XwxApplicationContext(AppConfig.class);
        System.out.println(xwxApplicationContext.getBean("userService"));
    }
}
