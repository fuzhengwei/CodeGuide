package org.itstack.demo.interfaces;

import com.alibaba.fastjson.JSON;
import org.itstack.demo.application.MallRuleService;
import org.itstack.demo.application.MallTreeService;
import org.itstack.demo.domain.rule.model.vo.DecisionMatter;
import org.itstack.demo.domain.rule.model.vo.EngineResult;
import org.itstack.demo.domain.tree.model.aggregates.TreeCollect;
import org.itstack.demo.interfaces.dto.DecisionMatterDTO;
import org.itstack.demo.interfaces.dto.TreeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@EnableEurekaClient
@RestController
public class DDDController {

    private Logger logger = LoggerFactory.getLogger(DDDController.class);

    @Resource
    private MallTreeService mallTreeService;
    @Resource
    private MallRuleService mallRuleService;

    /**
     * 测试接口：http://localhost:8080/api/tree/queryTreeSummaryInfo
     * 请求参数：{"treeId":10001}
     */
    @RequestMapping(path = "/api/tree/queryTreeSummaryInfo", method = RequestMethod.POST)
    public TreeCollect queryTreeSummaryInfo(@RequestBody TreeDTO request) {
        String reqStr = JSON.toJSONString(request);
        try {
            logger.info("查询规则树信息{}Begin req：{}", request.getTreeId(), reqStr);
            TreeCollect treeCollect = mallTreeService.queryTreeSummaryInfo(request.getTreeId());
            logger.info("查询规则树信息{}End res：{}", request.getTreeId(), JSON.toJSON(treeCollect));
            return treeCollect;
        } catch (Exception e) {
            logger.error("查询规则树信息{}Error req：{}", request.getTreeId(), reqStr, e);
            return null;
        }
    }

    /**
     * 测试接口：http://localhost:8080/api/tree/decisionRuleTree
     * 请求参数：{"treeId":10001,"userId":"fuzhengwei","valMap":{"gender":"man","age":"25"}}
     */
    @RequestMapping(path = "/api/tree/decisionRuleTree", method = RequestMethod.POST)
    public EngineResult decisionRuleTree(@RequestBody DecisionMatterDTO request) {
        String reqStr = JSON.toJSONString(request);
        try {
            logger.info("规则树行为信息决策{}Begin req：{}", request.getTreeId(), reqStr);
            DecisionMatter decisionMatter = new DecisionMatter();
            decisionMatter.setTreeId(request.getTreeId());
            decisionMatter.setUserId(request.getUserId());
            decisionMatter.setValMap(request.getValMap());
            EngineResult engineResult = mallRuleService.process(decisionMatter);
            logger.info("规则树行为信息决策{}End res：{}", request.getTreeId(), JSON.toJSON(engineResult));
            return engineResult;
        } catch (Exception e) {
            logger.error("规则树行为信息决策{}Error req：{}", request.getTreeId(), reqStr, e);
            return new EngineResult(false);
        }
    }

}
