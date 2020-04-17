package org.itstack.demo.test;

/**
 * -Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" E:\itstack\git\istack-demo\itstack-demo-jvm\itstack-demo-jvm-07\target\test-classes\org\itstack\demo\test\HelloWorld verbose true
 * -Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" E:\itstack\git\istack-demo\itstack-demo-jvm\itstack-demo-jvm-07\target\test-classes\org\itstack\demo\test\HelloWorld
 * lload_0
 * lconst_1
 * lcmp
 * ifgt 7
 * lload_0
 * lreturn
 * lload_0
 * lconst_1
 * lsub
 * invokestatic org/itstack/demo/test/HelloWorld/fibonacci(J)J
 * lload_0
 * ldc2_w 2
 * lsub
 * invokestatic org/itstack/demo/test/HelloWorld/fibonacci(J)J
 * ladd
 * lreturn
 */
public class HelloWorld {

    public static void main(String[] args) {
        long x = fibonacci(10);
        System.out.println(x);
    }

    //斐波那契数列（Fibonacci sequence）
    private static long fibonacci(long n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

}
