package org.itstack.demo.rpc.provider.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-config.xml")
public class ProviderTest {

    @Test
    public void init() throws InterruptedException {
         while (true){
             Thread.sleep(5000);
         }
    }

}
