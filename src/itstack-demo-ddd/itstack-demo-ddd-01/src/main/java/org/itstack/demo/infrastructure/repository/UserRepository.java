package org.itstack.demo.infrastructure.repository;

import org.itstack.demo.domain.repository.IUserRepository;
import org.itstack.demo.infrastructure.po.UserEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈 | 欢迎关注并获取更多专题案例源码
 * Create by fuzhengwei on @2019
 */
@Repository("userRepository")
public class UserRepository implements IUserRepository {

    @Resource(name = "userMysqlRepository")
    private IUserRepository userMysqlRepository;

    @Resource(name = "userRedisRepository")
    private IUserRepository userRedisRepository;

    @Override
    public void save(UserEntity userEntity) {
        //保存到DB
        userMysqlRepository.save(userEntity);

        //保存到Redis
        userRedisRepository.save(userEntity);
    }

    @Override
    public UserEntity query(Long id) {

        UserEntity userEntityRedis = userRedisRepository.query(id);
        if (null != userEntityRedis) return userEntityRedis;

        UserEntity userEntityMysql = userMysqlRepository.query(id);
        if (null != userEntityMysql){
            //保存到Redis
            userRedisRepository.save(userEntityMysql);
            return userEntityMysql;
        }

        // 查询为NULL
        return null;
    }

}
