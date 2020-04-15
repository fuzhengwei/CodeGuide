package org.itstack.demo.test.service.impl;

import org.itstack.demo.test.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
@Service("helloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public void echo() {
        System.out.println("hi itstack demo rpc");
    }

}
