package org.itstack.demo.netty.server;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.stream.ChunkedStream;
import io.netty.util.ReferenceCountUtil;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈 | 欢迎关注并获取专题&源码
 * Create by fuzhengwei on 2019
 */
public class MyServerChunkHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        //内容验证
        if (!(msg instanceof ByteBuf)) {
            super.write(ctx, msg, promise);
            return;
        }
        //获取Byte
        ByteBuf buf = (ByteBuf) msg;
        byte[] data = this.getData(buf);
        //写入流中
        ByteInputStream in = new ByteInputStream();
        in.setBuf(data);
        //消息分块；10个字节，测试过程中可以调整
        ChunkedStream stream = new ChunkedStream(in, 10);
        //管道消息传输承诺
        ChannelProgressivePromise progressivePromise = ctx.channel().newProgressivePromise();
        progressivePromise.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
            }
            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("消息发送成功 success");
                    promise.setSuccess();
                } else {
                    System.out.println("消息发送失败 failure：" + future.cause());
                    promise.setFailure(future.cause());
                }
            }
        });
        ReferenceCountUtil.release(msg);
        ctx.write(stream, progressivePromise);
    }

    //获取Byte
    private byte[] getData(ByteBuf buf) {
        if (buf.hasArray()) {
            return buf.array().clone();
        }
        byte[] data = new byte[buf.readableBytes() - 1];
        buf.readBytes(data);
        return data;
    }

}
