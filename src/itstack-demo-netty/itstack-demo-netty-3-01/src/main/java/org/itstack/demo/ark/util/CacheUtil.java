package org.itstack.demo.ark.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.itstack.demo.ark.domain.Device;
import org.itstack.demo.ark.domain.ServerInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class CacheUtil {

    //用于存放用户Channel信息，也可以建立map结构模拟不同的消息群
    public static ChannelGroup wsChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 缓存服务信息
    public static Map<Integer, ServerInfo> serverInfoMap = Collections.synchronizedMap(new HashMap<Integer, ServerInfo>());
    // 缓存用户cacheClientChannel；channelId -> Channel
    public static Map<String, Channel> cacheClientChannel = Collections.synchronizedMap(new HashMap<String, Channel>());
    // 设备组
    public static Map<String, Device> deviceGroup = Collections.synchronizedMap(new HashMap<String, Device>());

}
