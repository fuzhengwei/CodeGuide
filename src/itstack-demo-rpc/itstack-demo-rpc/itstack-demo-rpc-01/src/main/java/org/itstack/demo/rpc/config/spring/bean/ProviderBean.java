package org.itstack.demo.rpc.config.spring.bean;

import org.itstack.demo.rpc.config.ProviderConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class ProviderBean extends ProviderConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //发布生产者
        doExport();
    }

}
