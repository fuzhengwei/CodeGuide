package org.itstack.demo.rpc.config;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class ConsumerConfig<T> {

    private String nozzle; //接口
    private String alias;  //别名

    //动态代理链接
    protected synchronized T refer() {

        System.out.format("消费者信息=> [接口：%s] [别名：%s] \r\n", nozzle, alias);

        return null;
    }

}
