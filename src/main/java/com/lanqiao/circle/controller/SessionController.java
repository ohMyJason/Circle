package com.lanqiao.circle.controller;

import com.lanqiao.circle.service.SessionService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//私信页面
@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    SessionService sessionService;

    //根据userId查询出当前登录用户的信息：userId,userName,avatarUrl
    @PostMapping("/getUserInfo")
    public Result selectUserInfo(Integer userId)
    {
        return sessionService.selectUserInfo(userId);
    }

    //根据userId查找会话列表
    @PostMapping("/getUserList")
    public Result selectUserList(Integer userId){
        return sessionService.selectUserList(userId);
    }

    //根据senterId和receiverId查询历史消息
//    @PostMapping("/getMessageLog")


}
