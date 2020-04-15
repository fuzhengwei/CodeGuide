package org.itstack.demo.rpc.provider.export;

import org.itstack.demo.rpc.provider.export.domain.Hi;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/7
 */
public interface HelloService {

    String hi();

    String say(String str);

    String sayHi(Hi hi);

}
