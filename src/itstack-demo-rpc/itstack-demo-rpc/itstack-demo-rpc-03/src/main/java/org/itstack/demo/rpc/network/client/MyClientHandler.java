package org.itstack.demo.rpc.network.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.itstack.demo.rpc.network.future.SyncWriteFuture;
import org.itstack.demo.rpc.network.future.SyncWriteMap;
import org.itstack.demo.rpc.network.msg.Response;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        Response msg = (Response) obj;
        String requestId = msg.getRequestId();
        SyncWriteFuture future = (SyncWriteFuture) SyncWriteMap.syncKey.get(requestId);
        if (future != null) {
            future.setResponse(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
