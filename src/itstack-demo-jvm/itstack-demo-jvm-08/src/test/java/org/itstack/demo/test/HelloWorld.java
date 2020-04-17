package org.itstack.demo.test;

/**
 * -Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" E:\itstack\git\istack-demo\itstack-demo-jvm\itstack-demo-jvm-08\target\test-classes\org\itstack\demo\test\HelloWorld -verbose true -args 你好，java版虚拟机v1.0，欢迎你的到来。
 */
public class HelloWorld {

    public static void main(String[] args) {
        for (String str : args) {
            System.out.println(str);
        }
    }

}
