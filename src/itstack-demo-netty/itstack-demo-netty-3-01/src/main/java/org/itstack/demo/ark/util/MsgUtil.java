package org.itstack.demo.ark.util;

import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.itstack.demo.ark.domain.InfoProtocol;
import org.itstack.demo.ark.domain.msgobj.Text;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
public class MsgUtil {

    public static String buildMsg(InfoProtocol infoProtocol) {
        return JSON.toJSONString(infoProtocol) + "\r\n";
    }

    public static InfoProtocol getMsg(String str) {
        return JSON.parseObject(str, InfoProtocol.class);
    }

    public static TextWebSocketFrame buildWsMsgText(String channelId, String msgInfo) {
        InfoProtocol infoProtocol = new InfoProtocol();
        infoProtocol.setChannelId(channelId);
        infoProtocol.setMsgType(1);
        infoProtocol.setMsgObj(new Text(msgInfo));
        return new TextWebSocketFrame(JSON.toJSONString(infoProtocol));
    }

}
