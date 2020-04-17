package org.itstack.demo.infrastructure.dao.impl;

import org.itstack.demo.infrastructure.dao.UserDao;
import org.itstack.demo.infrastructure.po.UserEntity;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈 | 欢迎关注并获取更多专题案例源码
 * Create by fuzhengwei on @2019
 */
public class UserDaoImpl implements UserDao {

    @Override
    public void save(UserEntity userEntity) {
        //TODO 数据库操作
    }

    @Override
    public UserEntity query(Long id) {
        //TODO 数据库操作
        return null;
    }

}
