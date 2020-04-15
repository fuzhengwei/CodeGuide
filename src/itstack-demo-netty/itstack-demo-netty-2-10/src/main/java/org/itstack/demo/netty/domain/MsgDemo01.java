package org.itstack.demo.netty.domain;


import org.itstack.demo.netty.domain.protocol.Command;
import org.itstack.demo.netty.domain.protocol.Packet;

/**
 * 消息协议
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MsgDemo01 extends Packet {

    private String channelId;
    private String demo01;

    public MsgDemo01(String channelId, String demo01) {
        this.channelId = channelId;
        this.demo01 = demo01;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDemo01() {
        return demo01;
    }

    public void setDemo01(String demo01) {
        this.demo01 = demo01;
    }

    @Override
    public Byte getCommand() {
        return Command.Demo01;
    }

}
