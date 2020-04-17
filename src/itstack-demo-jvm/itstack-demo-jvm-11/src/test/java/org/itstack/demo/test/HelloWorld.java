package org.itstack.demo.test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * byte 取值范围
 * +127 = [0][1][1][1][1][1][1][1]
 * -128 = [1][0][0][0][0][0][0][0]
 * <p>
 * 有符号
 * -120 = [1][1][1][1][1][0][0][0]
 * 无符号（增位） 136 = 256 - 120
 * 136 = [0][0][0][0][0][0][0][0][1][0][0][0][1][0][0][0]
 * <p>
 * 输出二进制：new BigInteger("-120", 10).toString(2))
 */
public class HelloWorld {

    public static void main(String[] args) {

        byte[] val = {-120};

        BigInteger bigInteger = new BigInteger(1, val);

        //无符号（增位）
        String str_hex = bigInteger.toString(16);
        System.out.println(Integer.parseInt(str_hex, 16));

        //有符号
        System.out.println(bigInteger.byteValue());

    }

    public static void test() {
        final String text = "字符串转16进制";
        final byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        StringBuilder strs = new StringBuilder();
        for (byte b : bytes) {
            int val = b & 0xff;  //也可以用new BigInteger(1, new byte[]{textByte[0]}).toString(16)
            strs.append("/x").append(Integer.toHexString(val));
        }
        System.out.println(strs.toString());
        /*
         * 测试结果：
         * / xe3/ x80/ x90/ xe4/ xb8/ xaa/ xe6/ x8a/ xa4/ xe3/ x80/ x91/ xe6/ xb2/ x99/ xe5/ xae/ xa3/ xe6/ x97/ xa0/ xe7/ xa1/ x85/ xe6/ xb2/ xb9/ xe6/ x8a/ xa4/ xe5/ x8f/ x91/ xe7/ xb4/ xa0
         */
    }

}
