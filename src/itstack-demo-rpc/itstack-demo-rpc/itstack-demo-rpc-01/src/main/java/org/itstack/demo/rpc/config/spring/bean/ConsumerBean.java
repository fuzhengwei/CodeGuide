package org.itstack.demo.rpc.config.spring.bean;

import org.itstack.demo.rpc.config.ConsumerConfig;
import org.springframework.beans.factory.FactoryBean;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class ConsumerBean<T> extends ConsumerConfig<T> implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return refer();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
