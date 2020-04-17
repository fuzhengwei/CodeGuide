package org.itstack.demo.infrastructure.common;

/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class Constants {

    public static final class Global {
        public static final Long TreeNullNode = 0L;    //空节点值
    }

    public static final class NodeType{
        public static final Integer STEM = 1;  //树茎
        public static final Integer FRUIT = 2; //果实
    }

    public static final class RuleLimitType {
        public static final int EQUAL = 1;    //等于
        public static final int GT = 2;       //大于
        public static final int LT = 3;       //小于
        public static final int GE = 4;       //大于等于
        public static final int LE = 5;       //小于等于
        public static final int ENUM = 6;     //枚举
    }

}
