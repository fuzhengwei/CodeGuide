package org.itstack.demo.infrastructure.repository;

import org.itstack.demo.domain.tree.model.vo.TreeInfo;
import org.itstack.demo.domain.tree.model.vo.TreeRulePoint;
import org.itstack.demo.domain.tree.repository.ITreeRepository;
import org.itstack.demo.infrastructure.repository.mysql.TreeMysqlRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Repository("treeRepository")
public class TreeRepository implements ITreeRepository {

    @Resource
    private TreeMysqlRepository treeMysqlRepository;

    @Override
    public TreeInfo queryTreeInfo(Long treeId) {
        return treeMysqlRepository.queryTreeInfo(treeId);
    }

    @Override
    public List<TreeRulePoint> queryTreeRulePointList(Long treeId) {
        return treeMysqlRepository.queryTreeRulePointList(treeId);
    }

}
