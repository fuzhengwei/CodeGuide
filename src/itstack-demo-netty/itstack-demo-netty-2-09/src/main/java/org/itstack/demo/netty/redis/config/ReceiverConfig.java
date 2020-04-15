package org.itstack.demo.netty.redis.config;

import org.itstack.demo.netty.redis.MsgAgreementReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * 订阅者
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * 虫洞群：①群5398358 ②群5360692
 * Create by fuzhengwei on @2019
 */
@Configuration
public class ReceiverConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter msgAgreementListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(msgAgreementListenerAdapter, new PatternTopic("itstack-demo-netty-push-msgAgreement"));
        return container;
    }

    @Bean
    public MessageListenerAdapter msgAgreementListenerAdapter(MsgAgreementReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
