package org.itstack.demo.netty.util;

import io.netty.channel.Channel;
import org.itstack.demo.netty.domain.ServerInfo;
import org.itstack.demo.netty.server.NettyServer;

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

    // 缓存channel
    public static Map<String, Channel> cacheChannel = Collections.synchronizedMap(new HashMap<String, Channel>());

    // 缓存服务信息
    public static Map<Integer, ServerInfo> serverInfoMap = Collections.synchronizedMap(new HashMap<Integer, ServerInfo>());

    // 缓存服务端
    public static Map<Integer, NettyServer> serverMap = Collections.synchronizedMap(new HashMap<Integer, NettyServer>());

}
