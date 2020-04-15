package org.itstak.demo.ark.test;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.itstack.demo.ark.domain.InfoProtocol;
import org.itstack.demo.ark.domain.msgobj.Feedback;
import org.itstack.demo.ark.domain.msgobj.QueryInfoReq;
import org.itstack.demo.ark.util.MsgUtil;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 模拟一台下位机
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class ApiTest {
                                      
    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            final Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    // 基于换行符号
                    channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    // 解码转String，注意调整自己的编码格式GBK、UTF-8
                    channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
                    // 解码转String，注意调整自己的编码格式GBK、UTF-8
                    channel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                    // 在管道中添加我们自己的接收数据实现方法
                    channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object objMsgJsonStr) throws Exception {

                            InfoProtocol msg = MsgUtil.getMsg(objMsgJsonStr.toString());

                            Integer msgType = msg.getMsgType();

                            if (2 != msgType) return;

                            String queryInfoReqStr = msg.getMsgObj().toString();
                            QueryInfoReq queryInfoReq = JSON.parseObject(queryInfoReqStr, QueryInfoReq.class);

                            Integer stateType = queryInfoReq.getStateType();

                            Feedback feedback = null;
                            //查询温度信息
                            if (1 == stateType) {
                                feedback = new Feedback(ctx.channel().id().toString(), 1, "温度信息：" + (double) (Math.random() * 100) + "°C");
                            }
                            //查询光谱数据
                            else if (2 == stateType) {
                                feedback = new Feedback(ctx.channel().id().toString(), 2, "光谱数据：" + (int) (Math.random() * 100) + "-" + (int)(Math.random() * 100) + "-" + (int)(Math.random() * 100) + "-" + (int)(Math.random() * 100));
                            }

                            InfoProtocol infoProtocol = new InfoProtocol();
                            infoProtocol.setChannelId(ctx.channel().id().toString());
                            infoProtocol.setMsgType(3);
                            infoProtocol.setMsgObj(feedback);

                            ctx.writeAndFlush(JSON.toJSON(infoProtocol) + "\r\n");

                        }
                    });
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", 7397).sync();
            f.channel().closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
