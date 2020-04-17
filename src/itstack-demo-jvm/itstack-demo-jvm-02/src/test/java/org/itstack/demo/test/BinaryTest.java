package org.itstack.demo.test;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BinaryTest {

    public static void main(String[] args) {


        byte[] array = {-1, -1, -1, -128};

        String code16 = binary(array, 16);
        System.out.println(code16);

        long val = Long.parseLong(code16, 16);
        System.out.println(val);

        int i = array[3] | array[2] << 8 | array[1] << 16 | array[0] << 24;
        System.out.println(i);

        int anInt = ByteBuffer.wrap(array).order(ByteOrder.BIG_ENDIAN).getInt();
        System.out.println(anInt);


//        System.out.println(Integer.parseInt("0xca", 2));
//        System.out.println(Character.digit(0xca, 2));


    }

    public int getUnsignedByte(byte data) {      //将data字节型数据转换为0~255 (0xFF 即BYTE)。
        return data & 0x0FF;
    }

    public int getUnsignedByte(short data) {      //将data字节型数据转换为0~65535 (0xFFFF 即 WORD)。
        return data & 0x0FFFF;
    }

    public long getUnsignedIntt(int data) {     //将int数据转换为0~4294967295 (0xFFFFFFFF即DWORD)。
        return data & 0x0FFFFFFFFL;
    }

    /**
     * 将byte[]转为各种进制的字符串
     *
     * @param bytes byte[]
     * @param radix 基数可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    private static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    static String byteToHexString(byte[] codes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : codes) {
            int value = b & 0xFF;
            String strHex = Integer.toHexString(value);
            if (strHex.length() < 2) {
                strHex = "0" + strHex;
            }
            sb.append(strHex);
        }
        return sb.toString();
    }


}
