package org.itstack.demo.test;

/**
 * -Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" E:\itstack\git\istack-demo\itstack-demo-jvm\itstack-demo-jvm-10\target\test-classes\org\itstack\demo\test\HelloWorld -verbose
 */
public class HelloWorld {

    public static void main(String[] args) {
        throw new RuntimeException("自定义异常");
    }

}
