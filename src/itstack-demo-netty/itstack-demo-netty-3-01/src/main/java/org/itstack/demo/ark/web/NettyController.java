package org.itstack.demo.ark.web;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import org.itstack.demo.ark.domain.Device;
import org.itstack.demo.ark.domain.EasyResult;
import org.itstack.demo.ark.domain.InfoProtocol;
import org.itstack.demo.ark.domain.ServerInfo;
import org.itstack.demo.ark.server.socket.NettyServer;
import org.itstack.demo.ark.util.CacheUtil;
import org.itstack.demo.ark.util.MsgUtil;
import org.itstack.demo.ark.util.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
@Controller
public class NettyController {

    private Logger logger = LoggerFactory.getLogger(NettyController.class);

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/queryNettyServerList")
    @ResponseBody
    public Collection<ServerInfo> queryNettyServerList() {
        try {
            Collection<ServerInfo> serverInfos = CacheUtil.serverInfoMap.values();
            logger.info("查询服务端列表。{}", JSON.toJSONString(serverInfos));
            return serverInfos;
        } catch (Exception e) {
            logger.info("查询服务端列表失败。", e);
            return null;
        }
    }

    @RequestMapping("/queryDeviceList")
    @ResponseBody
    public Collection<Device> queryDeviceList() {
        try {
            Collection<Device> deviceInfos = CacheUtil.deviceGroup.values();
            logger.info("查询设备链接列表。{}", JSON.toJSONString(deviceInfos));
            return deviceInfos;
        } catch (Exception e) {
            logger.info("查询设备链接列表失败。", e);
            return null;
        }
    }

    @RequestMapping("/doSendMsg")
    @ResponseBody
    public EasyResult doSendMsg(String reqStr) {
        try {
            logger.info("向设备发送信息[可以通过另外一个Web服务调用本接口发送信息]：{}", reqStr);
            InfoProtocol infoProtocol = MsgUtil.getMsg(reqStr);
            String channelId = infoProtocol.getChannelId();
            Channel channel = CacheUtil.cacheClientChannel.get(channelId);
            channel.writeAndFlush(MsgUtil.buildMsg(infoProtocol));
            return EasyResult.buildSuccessResult();
        } catch (Exception e) {
            logger.error("向设备发送信息失败：{}", reqStr, e);
            return EasyResult.buildErrResult(e);
        }
    }

}
