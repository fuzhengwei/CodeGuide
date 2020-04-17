package org.itstack.demo.test;

import java.math.BigInteger;

public class ClassReaderTest {

    //取部分字节码：java.lang.String
    private static byte[] classData = {
            -54, -2, -70, -66, 0, 0, 0, 52, 2, 26, 3, 0, 0, -40, 0, 3, 0, 0, -37, -1, 3, 0, 0, -33, -1, 3, 0, 1, 0, 0, 8, 0,
            59, 8, 0, 83, 8, 0, 86, 8, 0, 87, 8, 0, 110, 8, 0, -83, 8, 0, -77, 8, 0, -49, 8, 0, -47, 1, 0, 3, 40, 41, 73, 1,
            0, 20, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 1, 0, 20, 40, 41,
            76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 3, 40, 41, 86, 1, 0, 3,
            40, 41, 90, 1, 0, 4, 40, 41, 91, 66, 1, 0, 4, 40, 41, 91, 67, 1, 0, 4, 40, 67, 41, 67, 1, 0, 21, 40, 68, 41, 76,
            106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 4, 40, 73, 41, 67, 1, 0, 4};

    public static void main(String[] args) {

        //classData是我们的字节码，第一是-54，因为byte取值范围是-128~+127，所以如果想看到和其他虚拟机一样的值，需要进行与运算。
        System.out.println("* byte字节码与运算原值(-54)换行后(-54 & 0x0FF)：" + (-54 & 0x0FF));

        //校验魔数
        readAndCheckMagic();

        //校验版本号
        readAndCheckVersion();

        //接下来会依次读取[可以参照java版本虚拟机代码]；constantPool、accessFlags、thisClassIdx、supperClassIdx、interfaces、fields、methods、attributes
    }

    /**
     * 校验魔数
     * <p>
     * 很多文件格式都会规定满足该格式的文件必须以某几个固定字节开头，这几个字节主要起到标识作用，叫作魔数(magic number)。
     * 例如；
     * PDF文件以4字节“%PDF”(0x25、0x50、0x44、0x46)开头，
     * ZIP文件以2字节“PK”(0x50、0x4B)开头
     * class文件以4字节“0xCAFEBABE”开头
     */
    private static void readAndCheckMagic() {
        System.out.println("\r\n------------ 校验魔数 ------------");
        //从class字节码中读取前四位
        byte[] magic_byte = new byte[4];
        System.arraycopy(classData, 0, magic_byte, 0, 4);

        //将4位byte字节转成16进制字符串
        String magic_hex_str = new BigInteger(1, magic_byte).toString(16);
        System.out.println("magic_hex_str：" + magic_hex_str);

        //byte_magic_str 是16进制的字符串，cafebabe，因为java中没有无符号整型，所以如果想要无符号只能放到更高位中
        long magic_unsigned_int32 = Long.parseLong(magic_hex_str, 16);
        System.out.println("magic_unsigned_int32：" + magic_unsigned_int32);

        //魔数比对，一种通过字符串比对，另外一种使用假设的无符号16进制比较。如果使用无符号比较需要将0xCAFEBABE & 0x0FFFFFFFFL与运算
        System.out.println("0xCAFEBABE & 0x0FFFFFFFFL：" + (0xCAFEBABE & 0x0FFFFFFFFL));

        if (magic_unsigned_int32 == (0xCAFEBABE & 0x0FFFFFFFFL)) {
            System.out.println("class字节码魔数无符号16进制数值一致校验通过");
        } else {
            System.out.println("class字节码魔数无符号16进制数值一致校验拒绝");
        }

    }

    /**
     * 校验版本号
     * <p>
     * 魔数之后是class文件的次版本号和主版本号，都是u2类型。假设某class文件的主版本号是M，次版本号是m，那么完整的版本号可以
     * 表示成“M.m”的形式。次版本号只在J2SE 1.2之前用过，从1.2开始基本上就没有什么用了(都是0)。主版本号在J2SE 1.2之前是45，
     * 从1.2开始，每次有大版本的Java版本发布，都会加1｛45、46、47、48、49、50、51、52｝
     */
    private static void readAndCheckVersion() {
        System.out.println("\r\n------------ 校验版本号 ------------");

        //从class字节码第4位开始读取，读取2位
        byte[] minor_byte = new byte[2];
        System.arraycopy(classData, 4, minor_byte, 0, 2);
        //将2位byte字节转成16进制字符串
        String minor_hex_str = new BigInteger(1, minor_byte).toString(16);
        System.out.println("minor_hex_str：" + minor_hex_str);
        //minor_unsigned_int32 转成无符号16进制
        int minor_unsigned_int32 = Integer.parseInt(minor_hex_str, 16);
        System.out.println("minor_unsigned_int32：" + minor_unsigned_int32);

        //从class字节码第6位开始读取，读取2位
        byte[] major_byte = new byte[2];
        System.arraycopy(classData, 6, major_byte, 0, 2);
        //将2位byte字节转成16进制字符串
        String major_hex_str = new BigInteger(1, major_byte).toString(16);
        System.out.println("major_hex_str：" + major_hex_str);
        //major_unsigned_int32 转成无符号16进制
        int major_unsigned_int32 = Integer.parseInt(major_hex_str, 16);
        System.out.println("major_unsigned_int32：" + major_unsigned_int32);

        System.out.println("版本号：" + major_unsigned_int32 + "." + minor_unsigned_int32);

    }

}
