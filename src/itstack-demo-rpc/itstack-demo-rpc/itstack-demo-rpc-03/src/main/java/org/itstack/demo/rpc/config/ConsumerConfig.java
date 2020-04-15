package org.itstack.demo.rpc.config;

import io.netty.channel.ChannelFuture;
import org.itstack.demo.rpc.registry.RedisRegistryCenter;
import redis.clients.jedis.Jedis;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class ConsumerConfig<T> {

    protected String nozzle; //接口
    protected String alias;  //别名

    public String getNozzle() {
        return nozzle;
    }

    public void setNozzle(String nozzle) {
        this.nozzle = nozzle;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
