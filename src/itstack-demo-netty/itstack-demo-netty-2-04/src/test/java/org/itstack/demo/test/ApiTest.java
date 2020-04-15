package org.itstack.demo.test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class ApiTest {

    /**
     * “r”：以只读的方式打开，调用该对象的任何write（写）方法都会导致IOException异常
     * “rw”：以读、写方式打开，支持文件的读取或写入。若文件不存在，则创建
     * “rws”：以读、写方式打开，与“rw”不同的是，还要对文件内容的每次更新都同步更新到潜在的存储设备中去。这里的“s”表示synchronous（同步）的意思
     * “rwd”：以读、写方式打开，与“rw”不同的是，还要对文件内容的每次更新都同步更新到潜在的存储设备中去。使用“rwd”模式仅要求将文件的内容更新到存储设备中，而使用“rws”模式除了更新文件的内容，还要更新文件的元数据（metadata），因此至少要求1次低级别的I/O操作
     * <p>
     * https://docs.oracle.com/javase/7/docs/api/java/io/RandomAccessFile.html
     */
    public static void main(String[] args) throws IOException {
        test("C:\\Users\\fuzhengwei\\Desktop\\测试传输文件1.txt");
        test("C:\\Users\\fuzhengwei\\Desktop\\netty-file.zip");
    }

    /**
     * 读取文件与notepad++ 比对
     *
     * @param fileUrl
     * @throws IOException
     */
    private static void test(String fileUrl) throws IOException {
        File file = new File(fileUrl);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        randomAccessFile.seek(0);

        byte[] bytes = new byte[1024];
        int byteRead = randomAccessFile.read(bytes);

        System.out.println(fileUrl);
        System.out.println("读取文件长度：" + byteRead);
        for (byte b : bytes) {
            System.out.print(new BigInteger(1, new byte[]{b}).toString(16) + " ");
        }
        System.out.println("\r\n");
    }

}
