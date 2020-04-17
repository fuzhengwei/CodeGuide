package org.itstack.demo.test;

/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 * <p>
 * VM options：
 * -javaagent:E:\itstack\GIT\itstack.org\itstack-demo-agent\itstack-demo-agent-02\target\itstack-demo-agent-02-1.0.0-SNAPSHOT.jar=testargs
 */
public class ApiTest {

    public static void main(String[] args) throws Exception {
        ApiTest apiTest = new ApiTest();
        apiTest.echoHi();

        /****************************************************
         * 测试结果
         * this is my agent：testargs
         * transform: [org.itstack.demo.test.ApiTest]
         * hi agent
         * method:[echoHi] cost:[195纳秒]
         ****************************************************/
    }

    private void echoHi() throws Exception {
        System.out.println("hi agent");
    }

}
