package org.itstack.demo.test;

import com.alibaba.fastjson.JSON;
import org.itstack.demo.domain.rule.model.aggregates.TreeRuleRich;
import org.itstack.demo.domain.rule.model.vo.DecisionMatter;
import org.itstack.demo.domain.rule.model.vo.EngineResult;
import org.itstack.demo.domain.rule.repository.IRuleRepository;
import org.itstack.demo.domain.rule.service.engine.impl.RuleEngineHandle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Resource
    private RuleEngineHandle ruleEngineHandle;
    @Resource
    private IRuleRepository ruleRepository;

    @Test
    public void test_process() throws Exception {
        DecisionMatter matter = new DecisionMatter();
        matter.setTreeId(10001L);
        matter.setUserId("fuzhengwei");
        matter.setValMap(new ConcurrentHashMap<String, Object>() {{
            put("gender", "man");
            put("age", "25");
        }});
        EngineResult engineResult = ruleEngineHandle.process(matter);
        logger.info("测试结果：" + JSON.toJSONString(engineResult));
    }

    @Test
    public void test_queryTreeRuleRich(){
        TreeRuleRich treeRuleRich = ruleRepository.queryTreeRuleRich(10001L);
        logger.info("测试结果：" + JSON.toJSONString(treeRuleRich));
        /** 测试结果
         * {
         *     "treeNodeMap": {
         *         "1": {
         *             "nodeType": 1,
         *             "ruleDesc": "用户性别[男/女]",
         *             "ruleKey": "userGender",
         *             "treeId": 10001,
         *             "treeNodeId": 1,
         *             "treeNodeLineInfoList": [
         *                 {
         *                     "nodeIdFrom": 1,
         *                     "nodeIdTo": 11,
         *                     "ruleLimitType": 1,
         *                     "ruleLimitValue": "man"
         *                 },
         *                 {
         *                     "nodeIdFrom": 1,
         *                     "nodeIdTo": 12,
         *                     "ruleLimitType": 1,
         *                     "ruleLimitValue": "woman"
         *                 }
         *             ]
         *         },
         *         "11": {
         *             "nodeType": 1,
         *             "ruleDesc": "用户年龄",
         *             "ruleKey": "userAge",
         *             "treeId": 10001,
         *             "treeNodeId": 11,
         *             "treeNodeLineInfoList": [
         *                 {
         *                     "nodeIdFrom": 11,
         *                     "nodeIdTo": 111,
         *                     "ruleLimitType": 3,
         *                     "ruleLimitValue": "25"
         *                 },
         *                 {
         *                     "nodeIdFrom": 11,
         *                     "nodeIdTo": 112,
         *                     "ruleLimitType": 3,
         *                     "ruleLimitValue": "25"
         *                 }
         *             ]
         *         },
         *         "12": {
         *             "nodeType": 1,
         *             "ruleDesc": "用户年龄",
         *             "ruleKey": "userAge",
         *             "treeId": 10001,
         *             "treeNodeId": 12,
         *             "treeNodeLineInfoList": [
         *                 {
         *                     "nodeIdFrom": 12,
         *                     "nodeIdTo": 121,
         *                     "ruleLimitType": 3,
         *                     "ruleLimitValue": "25"
         *                 },
         *                 {
         *                     "nodeIdFrom": 12,
         *                     "nodeIdTo": 122,
         *                     "ruleLimitType": 3,
         *                     "ruleLimitValue": "25"
         *                 }
         *             ]
         *         },
         *         "111": {
         *             "nodeType": 2,
         *             "nodeValue": "果实A",
         *             "treeId": 10001,
         *             "treeNodeId": 111,
         *             "treeNodeLineInfoList": [ ]
         *         },
         *         "112": {
         *             "nodeType": 2,
         *             "nodeValue": "果实B",
         *             "treeId": 10001,
         *             "treeNodeId": 112,
         *             "treeNodeLineInfoList": [ ]
         *         },
         *         "121": {
         *             "nodeType": 2,
         *             "nodeValue": "果实C",
         *             "treeId": 10001,
         *             "treeNodeId": 121,
         *             "treeNodeLineInfoList": [ ]
         *         },
         *         "122": {
         *             "nodeType": 2,
         *             "nodeValue": "果实D",
         *             "treeId": 10001,
         *             "treeNodeId": 122,
         *             "treeNodeLineInfoList": [ ]
         *         }
         *     },
         *     "treeRoot": {
         *         "treeId": 10001,
         *         "treeName": "购物分类规则树",
         *         "treeRootNodeId": 1
         *     }
         * }
         */
    }

}
