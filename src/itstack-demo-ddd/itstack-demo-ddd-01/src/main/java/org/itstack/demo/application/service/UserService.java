package org.itstack.demo.application.service;

import org.itstack.demo.domain.model.aggregates.UserRichInfo;

/**
 * 应用层用户服务
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈 | 欢迎关注并获取更多专题案例源码
 * Create by fuzhengwei on @2019
 */
public interface UserService {

    UserRichInfo queryUserInfoById(Long id);

}
