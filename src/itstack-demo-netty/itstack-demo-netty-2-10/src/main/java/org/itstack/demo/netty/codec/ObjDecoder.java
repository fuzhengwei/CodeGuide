package org.itstack.demo.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.itstack.demo.netty.domain.protocol.PacketClazzMap;
import org.itstack.demo.netty.util.SerializationUtil;

import java.util.List;

/**
 * 解码器
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class ObjDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte command = in.readByte();  //读取指令
        byte[] data = new byte[dataLength - 1]; //指令占了一位，剔除掉
        in.readBytes(data);
        out.add(SerializationUtil.deserialize(data, PacketClazzMap.packetTypeMap.get(command)));
    }

}
