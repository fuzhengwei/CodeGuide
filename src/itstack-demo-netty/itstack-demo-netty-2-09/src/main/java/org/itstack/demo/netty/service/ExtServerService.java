package org.itstack.demo.netty.service;

import org.itstack.demo.netty.domain.MsgAgreement;
import org.itstack.demo.netty.redis.Publisher;
import org.itstack.demo.netty.redis.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 扩展服务
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛关注获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on 2019
 */
@Service("extServerService")
public class ExtServerService {

    @Resource
    private Publisher publisher;
    @Resource
    private RedisUtil redisUtil;

    public void push(MsgAgreement msgAgreement){
        publisher.pushMessage("itstack-demo-netty-push-msgAgreement", msgAgreement);
    }

    public RedisUtil getRedisUtil() {
        return redisUtil;
    }
}
