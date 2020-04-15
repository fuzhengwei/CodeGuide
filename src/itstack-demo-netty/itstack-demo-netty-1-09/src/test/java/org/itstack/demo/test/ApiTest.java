package org.itstack.demo.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class ApiTest {

    public static void main(String[] args) {

        System.out.println("hi 微信公众号：bugstack虫洞栈");

        ByteBuf in = Unpooled.buffer(5);
        byte[] bytes = {2, 50, 104, 105, 3};
        in.writeBytes(bytes);
        in.readBytes(1);
        ByteBuf byteBuf = in.readBytes(1);

        System.out.println(byteBuf.toString(Charset.forName("GBK")));
    }

    public void test_03() {
        ByteBuf in = Unpooled.buffer(5);
        byte[] bytes = {104, 105, 0x02, 104, 105, 0x03, 104};
        in.writeBytes(bytes);

        //可读数据大小
        int size = in.readableBytes();

        //读取到byte
        byte[] data = new byte[size];
        in.readBytes(data);

        int beginIdx = 0, endIdx = 0;
        for (int i = 0; i < size; i++) {
            if (data[i] == 0x02) {
                beginIdx = i;
                continue;
            }
            if (data[i] == 0x03) {
                endIdx = i;
                break;
            }
        }

        ByteBuf copy = in.copy(beginIdx + 1, endIdx - beginIdx - 1);
        int i = copy.readableBytes();
        byte[] data2 = new byte[i];
        copy.readBytes(data2);
        System.out.println(Arrays.toString(data2));

        //置位
        in.resetReaderIndex();
        in.readBytes(endIdx + 1);

        //余下数据
        int i2 = in.readableBytes();
        byte[] data3 = new byte[i2];
        in.readBytes(data3);
        System.out.println(Arrays.toString(data3));
    }

    public void test_01() {
        ByteBuf in = Unpooled.buffer(5);
        byte[] bytes = {104, 105, 0x02, 104, 105, 0x03, 104};
        in.writeBytes(bytes);

        while (true) {
            int i = in.readerIndex();
            in.markReaderIndex();
            if (in.readByte() == 0x02) {
                break;
            }
            //未读到包头，略过一个字节
            in.resetReaderIndex();
            in.readByte();
        }

        //假定我们的数据长度是2
        ByteBuf byteBuf = in.readBytes(2);
        int size = byteBuf.readableBytes();
        byte[] data = new byte[size];
        byteBuf.readBytes(data);
        in.readByte();
        System.out.println(Arrays.toString(data));

        //余下数据
        int i = in.readableBytes();
        byte[] data2 = new byte[i];
        in.readBytes(data2);
        System.out.println(Arrays.toString(data2));
    }

    public void test_02() {
         /* int size = in.readableBytes();

        byte[] data = new byte[size];
        in.readBytes(data);

        byte begin = data[0];     //开始符02
        byte end = data[size - 1];//结束符03

        //无开始符，只有结束符，数据丢包
        if (begin != 0x02 && end == 0x03){
            System.out.println("公众号：bugstack虫洞栈 提示；byteBuf数据，无开始符，只有结束符，数据丢包。");
            channelHandlerContext.writeAndFlush("error");
            return; //直接返回，不置位指针
        }
        //有开始符，无结束符号，数据半包。置位指针，接收余下数据
        if (begin != 0x02 || end != 0x03) {
            in.resetReaderIndex();
            System.out.println("公众号：bugstack虫洞栈 提示；byteBuf数据，有开始符，无结束符号，数据半包。置位指针，接收余下数据。");
            return;
        }
        //数据完整，解析处理
        System.out.println(JSON.toJSONString(data));
        //越过02 03位
        ByteBuf copy = in.copy(1, size - 1);
        //转换
        String msg = copy.toString(Charset.forName("GBK"));
        //填充
        out.add(msg);*/
    }

}
