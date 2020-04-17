package org.itstack.demo.infrastructure.util;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Service("cacheUtil")
public class CacheUtil {

    public static Map<Long, Object> cacheMap = new ConcurrentHashMap<>();

}
