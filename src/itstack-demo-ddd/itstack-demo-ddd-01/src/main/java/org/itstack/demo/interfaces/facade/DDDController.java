package org.itstack.demo.interfaces.facade;

import org.itstack.demo.application.service.UserService;
import org.itstack.demo.interfaces.dto.UserInfoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 虫洞栈：https://bugstack.cn
 * 公众号：bugstack虫洞栈 | 欢迎关注并获取更多专题案例源码
 * Create by fuzhengwei on @2019
 */
@Controller
public class DDDController {

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/api/user/queryUserInfo")
    @ResponseBody
    public ResponseEntity queryUserInfo(@RequestBody UserInfoDto request) {
        return new ResponseEntity<>(userService.queryUserInfoById(request.getId()), HttpStatus.OK);
    }

}
