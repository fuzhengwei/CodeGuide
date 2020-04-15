package org.itstack.demo.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLException;
import java.io.File;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class NettyServer {

    public static void main(String[] args) throws SSLException {
        new NettyServer().bing(7399);
    }

    private void bing(int port) throws SSLException {

        //引入SSL安全验证
        File certChainFile = new File("E:\\itstack\\GIT\\itstack.org\\itstack-demo-netty\\itstack-demo-netty-2-13\\src\\main\\java\\org\\itstack\\demo\\netty\\ssl\\server\\server.crt");
        File keyFile = new File("E:\\itstack\\GIT\\itstack.org\\itstack-demo-netty\\itstack-demo-netty-2-13\\src\\main\\java\\org\\itstack\\demo\\netty\\ssl\\server\\pkcs8_server.key");
        File rootFile = new File("E:\\itstack\\GIT\\itstack.org\\itstack-demo-netty\\itstack-demo-netty-2-13\\src\\main\\java\\org\\itstack\\demo\\netty\\ssl\\server\\ca.crt");
        SslContext sslCtx = SslContextBuilder.forServer(certChainFile, keyFile).trustManager(rootFile).clientAuth(ClientAuth.REQUIRE).build();

        //配置服务端NIO线程组
        EventLoopGroup parentGroup = new NioEventLoopGroup(1); //NioEventLoopGroup extends MultithreadEventLoopGroup Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)    //非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer(sslCtx));
            ChannelFuture f = b.bind(port).sync();
            System.out.println("itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈 | 获取专题源码}");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }

    }

}
