package org.itstack.demo.rpc.provider.web;

import org.itstack.demo.rpc.provider.export.HelloService;
import org.itstack.demo.rpc.provider.export.domain.Hi;
import org.springframework.stereotype.Controller;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/7
 */
@Controller("helloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public String hi() {
        return "hi itstack rpc";
    }

    @Override
    public String say(String str) {
        return str;
    }

    @Override
    public String sayHi(Hi hi) {
        return hi.getUserName() + " say：" + hi.getSayMsg();
    }

}
