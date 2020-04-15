package org.itstack.demo.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import org.itstack.demo.netty.domain.MsgAgreement;
import org.itstack.demo.netty.domain.UserChannelInfo;
import org.itstack.demo.netty.service.ExtServerService;
import org.itstack.demo.netty.util.CacheUtil;
import org.itstack.demo.netty.util.MsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

    private ExtServerService extServerService;

    public MyServerHandler(ExtServerService extServerService) {
        this.extServerService = extServerService;
    }

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端。channelId：" + channel.id());
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");

        //保存用户信息
        UserChannelInfo userChannelInfo = new UserChannelInfo(channel.localAddress().getHostString(), channel.localAddress().getPort(), channel.id().toString(), new Date());
        extServerService.getRedisUtil().pushObj(userChannelInfo);
        CacheUtil.cacheChannel.put(channel.id().toString(), channel);
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        ctx.writeAndFlush(MsgUtil.buildMsg(channel.id().toString(), str));

    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开链接" + ctx.channel().localAddress().toString());
        extServerService.getRedisUtil().remove(ctx.channel().id().toString());
        CacheUtil.cacheChannel.remove(ctx.channel().id().toString(), ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object objMsgJsonStr) throws Exception {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息内容：" + objMsgJsonStr);

        MsgAgreement msgAgreement = MsgUtil.json2Obj(objMsgJsonStr.toString());

        String toChannelId = msgAgreement.getToChannelId();
        //判断接收消息用户是否在本服务端
        Channel channel = CacheUtil.cacheChannel.get(toChannelId);
        if (null != channel) {
            channel.writeAndFlush(MsgUtil.obj2Json(msgAgreement));
            return;
        }
        //如果为NULL则接收消息的用户不在本服务端，需要push消息给全局
        logger.info("接收消息的用户不在本服务端，PUSH！");
        extServerService.push(msgAgreement);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        extServerService.getRedisUtil().remove(ctx.channel().id().toString());
        CacheUtil.cacheChannel.remove(ctx.channel().id().toString(), ctx.channel());
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }

}
