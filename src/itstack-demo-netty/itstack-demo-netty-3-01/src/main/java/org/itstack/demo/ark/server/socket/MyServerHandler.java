package org.itstack.demo.ark.server.socket;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.itstack.demo.ark.domain.Device;
import org.itstack.demo.ark.util.CacheUtil;
import org.itstack.demo.ark.util.MsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.itstack.demo.ark.util.CacheUtil.deviceGroup;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        String channelId = channel.id().toString();
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端。channelId：" + channelId);
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");

        //构建设备信息｛下位机、中继器、IO板卡｝
        Device device = new Device();
        device.setChannelId(channelId);
        device.setNumber(UUID.randomUUID().toString());
        device.setIp(channel.remoteAddress().getHostString());
        device.setPort(channel.remoteAddress().getPort());
        device.setConnectTime(new Date());
        //添加设备信息
        deviceGroup.put(channelId, device);
        CacheUtil.cacheClientChannel.put(channelId, channel);
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端断开链接：{}", ctx.channel().localAddress().toString());
        String channelId = ctx.channel().id().toString();
        //移除设备信息
        deviceGroup.remove(channelId);
        CacheUtil.cacheClientChannel.remove(channelId);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object objMsgJsonStr) throws Exception {
        //接收设备发来信息
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息内容：" + objMsgJsonStr);

        //转发消息到Ws
        CacheUtil.wsChannelGroup.writeAndFlush(new TextWebSocketFrame(objMsgJsonStr.toString()));
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String channelId = ctx.channel().id().toString();
        //移除设备信息
        deviceGroup.remove(channelId);
        CacheUtil.cacheClientChannel.remove(channelId);
        ctx.close();
        logger.error("异常信息：{}", cause.getMessage());
    }

}
