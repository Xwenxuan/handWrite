package com.xwx;

import com.spring.XwxApplicationContext;

/**
 * @author winsonxiao
 * @ClassName Test
 * @date 2021/10/8、10:09 上午
 */
public class Test {

    public static void main(String[] args) {
        XwxApplicationContext xwxApplicationContext = new XwxApplicationContext(AppConfig.class);
        System.out.println(xwxApplicationContext.getBean("userService"));
    }
}
