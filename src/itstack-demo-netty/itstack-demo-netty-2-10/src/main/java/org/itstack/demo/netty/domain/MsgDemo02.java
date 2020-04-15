package org.itstack.demo.netty.domain;

import org.itstack.demo.netty.domain.protocol.Command;
import org.itstack.demo.netty.domain.protocol.Packet;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MsgDemo02 extends Packet {

    private String channelId;
    private String demo02;

    public MsgDemo02(String channelId, String demo02) {
        this.channelId = channelId;
        this.demo02 = demo02;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDemo02() {
        return demo02;
    }

    public void setDemo02(String demo02) {
        this.demo02 = demo02;
    }

    @Override
    public Byte getCommand() {
        return Command.Demo02;
    }
}
