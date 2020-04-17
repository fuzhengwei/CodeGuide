package org.itstack.demo.application.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Application启动后运行，日志打印微服务关键配置信息(服务名、微服务注册中心地址、配置中心)
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈 | 欢迎关注并获取更多专题案例源码
 * Create by fuzhengwei on @2019
 */
@Component
public class ApplicationRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(String... strings) {
        Environment environment = applicationContext.getEnvironment();
        try {
            logger.info("spring.application.name : {}", environment.getProperty("spring.application.name"));
            logger.info("eureka.client.service-url.defaultZone : {}", environment.getProperty("eureka.client.service-url.defaultZone"));
            logger.info("spring.cloud.config.discovery.service-id : {}", environment.getProperty("spring.cloud.config.discovery.service-id"));
        } catch (Exception e) {
            logger.warn("Get Properties Exception : {}", e.getMessage());
        }
    }

}
