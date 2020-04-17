package org.itstack.demo.jvm.classfile;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public class ClassReader {

    private byte[] data;
    private int cursor;       //游标；读取后记录位置

    public ClassReader(byte[] data) {
        this.data = data;
    }

    public int readU1ToInt() {
        return ReaderUtil.byteToInt(new byte[]{data[cursor++]});
    }

    public int readU2ToInt() {
        return ReaderUtil.byteToInt(new byte[]{data[cursor++], data[cursor++]});
    }

    public int readU4ToInt() {
        return ReaderUtil.byteToInt(new byte[]{data[cursor++], data[cursor++], data[cursor++], data[cursor++]});
    }

    public float readU4ToFloat() {
        byte[] bytes = readBytes(4);
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    //16进制字符串
    public String readU4ToHexStr() {
        return ReaderUtil.byteToHexString((new byte[]{data[cursor++], data[cursor++], data[cursor++], data[cursor++]}));
    }

    public long read2U4ToLong() {
        byte[] bytes = readBytes(8);
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getLong();
    }

    public double read2U4Double() {
        byte[] bytes = readBytes(8);
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getDouble();
    }

    public int[] readUInt16s() {
        int count = readU2ToInt();
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = readU2ToInt();
        }
        return result;
    }

    public void back(int n) {
        this.cursor -= n;
    }

    public byte[] readBytes(int len) {
        if (cursor + len >= data.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        byte[] dataCopy = Arrays.copyOfRange(this.data, cursor, cursor + len);
        cursor += len;
        return dataCopy;
    }

    static class ReaderUtil {

        static int byteToInt(byte[] codes) {
            String str = byteToHexString(codes);
            return Integer.valueOf(str, 16);
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

}
