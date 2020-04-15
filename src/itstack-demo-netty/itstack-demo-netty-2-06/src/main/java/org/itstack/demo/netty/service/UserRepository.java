package org.itstack.demo.netty.service;

import org.itstack.demo.netty.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * Create by fuzhengwei on 2019
 */
public interface UserRepository extends ElasticsearchRepository<User, String> {

    Page<User> findByName(String name, Pageable pageable);

}
