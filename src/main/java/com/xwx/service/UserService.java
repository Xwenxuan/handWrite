package com.xwx.service;

import com.spring.Autowired;
import com.spring.Component;

/**
 * @author winsonxiao
 * @ClassName UserService
 * @date 2021/10/8γ5:43 δΈε
 */
@Component("userService")
public class UserService {

    @Autowired
    private OrderService orderService;
}
