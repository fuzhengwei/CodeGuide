package org.itstack.demo.service;

import org.itstack.demo.domain.TreeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@FeignClient(value = "itstack-demo-ddd-case")
public interface MallService {

    @RequestMapping(value = "/api/tree/queryTreeSummaryInfo", method = RequestMethod.POST)
    Object queryTreeSummaryInfo(@RequestBody TreeDTO request);

}
