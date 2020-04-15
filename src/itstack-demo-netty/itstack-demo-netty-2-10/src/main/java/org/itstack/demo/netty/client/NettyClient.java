package org.itstack.demo.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.itstack.demo.netty.domain.MsgDemo01;
import org.itstack.demo.netty.domain.MsgDemo02;
import org.itstack.demo.netty.domain.MsgDemo03;
import org.itstack.demo.netty.util.MsgUtil;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class NettyClient {

    public static void main(String[] args) {
        new NettyClient().connect("127.0.0.1", 7397);
    }

    private void connect(String inetHost, int inetPort) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new MyChannelInitializer());
            ChannelFuture f = b.connect(inetHost, inetPort).sync();
            System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}");

            //测试消息，分别发放demo01、demo02、demo03
            f.channel().writeAndFlush(MsgUtil.buildMsgDemo01(f.channel().id().toString(),"你好，消息体MsgDemo01，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，欢迎关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsgDemo02(f.channel().id().toString(),"你好，消息体MsgDemo02，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，欢迎关注我获取案例源码。"));
            f.channel().writeAndFlush(MsgUtil.buildMsgDemo03(f.channel().id().toString(),"你好，消息体MsgDemo03，我是https://bugstack.cn博主，付政委。这是我的公众号<bugstack虫洞栈>，欢迎关注我获取案例源码。"));

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
