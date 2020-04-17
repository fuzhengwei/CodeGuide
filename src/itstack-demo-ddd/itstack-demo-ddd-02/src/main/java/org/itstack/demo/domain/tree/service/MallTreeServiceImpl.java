package org.itstack.demo.domain.tree.service;

import org.itstack.demo.application.MallTreeService;
import org.itstack.demo.domain.tree.model.aggregates.TreeCollect;
import org.itstack.demo.domain.tree.model.vo.TreeInfo;
import org.itstack.demo.domain.tree.model.vo.TreeRulePoint;
import org.itstack.demo.domain.tree.repository.ITreeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Service("mallTreeService")
public class MallTreeServiceImpl implements MallTreeService {

    private Logger logger = LoggerFactory.getLogger(MallTreeServiceImpl.class);

    @Resource
    private ITreeRepository treeRepository;

    @Override
    public TreeCollect queryTreeSummaryInfo(Long treeId) {
        TreeInfo treeInfo = treeRepository.queryTreeInfo(treeId);
        List<TreeRulePoint> treeRulePointList = treeRepository.queryTreeRulePointList(treeId);
        // 封装结果
        TreeCollect treeCollect = new TreeCollect();
        treeCollect.setTreeInfo(treeInfo);
        treeCollect.setTreeRulePointList(treeRulePointList);
        return treeCollect;
    }

}
