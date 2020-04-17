package org.itstack.demo.test;

import com.alibaba.fastjson.JSON;
import org.itstack.demo.domain.TreeDTO;
import org.itstack.demo.service.MallService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Resource
    private MallService mallService;

    @Test
    public void test() {
        Object obj = mallService.queryTreeSummaryInfo(new TreeDTO(10001L));
        logger.info("测试结果：" + JSON.toJSONString(obj));
    }

}
