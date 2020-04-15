package org.itstack.demo.rpc.config.spring.bean;

import org.itstack.demo.rpc.config.ServerConfig;
import org.itstack.demo.rpc.domain.LocalServerInfo;
import org.itstack.demo.rpc.network.server.ServerSocket;
import org.itstack.demo.rpc.registry.RedisRegistryCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class ServerBean extends ServerConfig implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(ServerBean.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //启动注册中心
        logger.info("启动注册中心 ...");
        RedisRegistryCenter.init(host, port);
        logger.info("启动注册中心完成 {} {}", host, port);

        //初始化服务端
        logger.info("初始化生产端服务 ...");
        ServerSocket serverSocket = new ServerSocket(applicationContext);
        Thread thread = new Thread(serverSocket);
        thread.start();
        while (!serverSocket.isActiveSocketServer()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }

        logger.info("初始化生产端服务完成 {} {}", LocalServerInfo.LOCAL_HOST, LocalServerInfo.LOCAL_PORT);
    }


}
