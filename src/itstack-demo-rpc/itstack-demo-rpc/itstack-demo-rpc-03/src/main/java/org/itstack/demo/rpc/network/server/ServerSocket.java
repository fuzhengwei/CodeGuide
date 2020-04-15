package org.itstack.demo.rpc.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.itstack.demo.rpc.domain.LocalServerInfo;
import org.itstack.demo.rpc.network.codec.RpcDecoder;
import org.itstack.demo.rpc.network.codec.RpcEncoder;
import org.itstack.demo.rpc.network.msg.Request;
import org.itstack.demo.rpc.network.msg.Response;
import org.itstack.demo.rpc.util.NetUtil;
import org.springframework.context.ApplicationContext;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class ServerSocket implements Runnable {

    private ChannelFuture f;

    private transient ApplicationContext applicationContext;

    public ServerSocket(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    public boolean isActiveSocketServer() {
        try {
            if (f != null) {
                return f.channel().isActive();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new RpcDecoder(Request.class),
                                    new RpcEncoder(Response.class),
                                    new MyServerHandler(applicationContext));
                        }
                    });

            //启动初始端口
            int port = 22201;
            while (NetUtil.isPortUsing(port)) {
                port++;
            }
            LocalServerInfo.LOCAL_HOST = NetUtil.getHost();
            LocalServerInfo.LOCAL_PORT = port;
            //注册服务
            this.f = b.bind(port).sync();
            this.f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

}
