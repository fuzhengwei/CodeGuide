package org.itstack.demo.infrastructure.repository.mysql;

import org.itstack.demo.domain.tree.model.vo.TreeInfo;
import org.itstack.demo.domain.tree.model.vo.TreeRulePoint;
import org.itstack.demo.domain.tree.repository.ITreeRepository;
import org.itstack.demo.infrastructure.dao.RuleTreeDao;
import org.itstack.demo.infrastructure.dao.RuleTreeNodeDao;
import org.itstack.demo.infrastructure.dao.RuleTreeNodeLineDao;
import org.itstack.demo.infrastructure.po.RuleTree;
import org.itstack.demo.infrastructure.po.RuleTreeNode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Service("treeMysqlRepository")
public class TreeMysqlRepository implements ITreeRepository {

    @Resource
    private RuleTreeDao ruleTreeDao;
    @Resource
    private RuleTreeNodeDao ruleTreeNodeDao;
    @Resource
    private RuleTreeNodeLineDao ruleTreeNodeLineDao;

    @Override
    public TreeInfo queryTreeInfo(Long treeId) {

        // 查询规则树简要信息
        RuleTree ruleTree = ruleTreeDao.queryTreeSummaryInfo(treeId);
        int nodeCount = ruleTreeNodeDao.queryTreeNodeCount(treeId);
        int nodeLineCount = ruleTreeNodeLineDao.queryTreeNodeLineCount(treeId);

        TreeInfo treeInfo = new TreeInfo();
        treeInfo.setTreeId(treeId);
        treeInfo.setTreeName(ruleTree.getTreeName());
        treeInfo.setTreeDesc(ruleTree.getTreeDesc());
        treeInfo.setNodeCount(nodeCount);
        treeInfo.setLineCount(nodeLineCount);

        return treeInfo;
    }

    @Override
    public List<TreeRulePoint> queryTreeRulePointList(Long treeId) {
        List<RuleTreeNode> treeNodeList = ruleTreeNodeDao.queryTreeRulePoint(treeId);
        List<TreeRulePoint> treeRulePointList = new ArrayList<>(treeNodeList.size());
        for (RuleTreeNode treeNode : treeNodeList) {
            treeRulePointList.add(new TreeRulePoint(treeNode.getRuleKey(),treeNode.getRuleDesc()));
        }
        return treeRulePointList;
    }

}
