package org.itstack.demo.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.itstack.demo.netty.domain.MsgDemo02;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MsgDemo02Handler extends SimpleChannelInboundHandler<MsgDemo02> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgDemo02 msg) throws Exception {
        System.out.println("\r\n> msg handler ing ...");
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收消息的处理器：" + this.getClass().getName());
        System.out.println("channelId：" + msg.getChannelId());
        System.out.println("消息内容：" + msg.getDemo02());
    }
    
}
