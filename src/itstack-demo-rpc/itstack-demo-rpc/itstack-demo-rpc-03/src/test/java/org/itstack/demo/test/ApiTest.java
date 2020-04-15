package org.itstack.demo.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class ApiTest {

    public static void main(String[] args) {
        String[] configs = {"itstack-rpc-center.xml", "itstack-rpc-provider.xml", "itstack-rpc-consumer.xml"};
        new ClassPathXmlApplicationContext(configs);
    }

}
