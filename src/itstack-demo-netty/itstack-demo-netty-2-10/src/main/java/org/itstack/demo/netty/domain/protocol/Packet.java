package org.itstack.demo.netty.domain.protocol;

/**
 * 协议包
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public abstract class Packet {

    /**
     * 获取协议指令
     * @return 返回指令值
     */
    public abstract Byte getCommand();

}
