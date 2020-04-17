package org.itstack.demo.web;

import org.itstack.demo.domain.TreeDTO;
import org.itstack.demo.service.MallService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@RestController
public class FeignController {

    @Resource
    private MallService mallService;

    @GetMapping(value = "api/queryTreeSummaryInfo")
    public String queryTreeSummaryInfo(Long treeId) {
        return mallService.queryTreeSummaryInfo(new TreeDTO(treeId)).toString();
    }

}
