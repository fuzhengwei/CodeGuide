package org.itstack.demo.domain.model.aggregates;

import org.itstack.demo.domain.model.vo.UserInfo;
import org.itstack.demo.domain.model.vo.UserSchool;

import java.util.List;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈 | 欢迎关注并获取更多专题案例源码
 * Create by fuzhengwei on @2019
 */
public class UserRichInfo {

    private UserInfo userInfo;
    private List<UserSchool> userSchoolList;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<UserSchool> getUserSchoolList() {
        return userSchoolList;
    }

    public void setUserSchoolList(List<UserSchool> userSchoolList) {
        this.userSchoolList = userSchoolList;
    }
    
}
