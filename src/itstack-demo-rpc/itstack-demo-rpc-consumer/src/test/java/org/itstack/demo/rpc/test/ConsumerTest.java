package org.itstack.demo.rpc.test;

import com.alibaba.fastjson.JSON;
import org.itstack.demo.rpc.provider.export.HelloService;
import org.itstack.demo.rpc.provider.export.domain.Hi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-config.xml")
public class ConsumerTest {

    @Resource(name = "helloService")
    private HelloService helloService;

    @Test
    public void test() {
        
        String hi = helloService.hi();
        System.out.println("测试结果：" + hi);

        String say = helloService.say("hello world");
        System.out.println("测试结果：" + say);

        Hi hiReq = new Hi();
        hiReq.setUserName("付栈");
        hiReq.setSayMsg("付可敌国，栈无不胜");
        String hiRes = helloService.sayHi(hiReq);

        System.out.println("测试结果：" + hiRes);
    }

}
