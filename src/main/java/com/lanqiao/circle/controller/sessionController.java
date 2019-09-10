package com.lanqiao.circle.controller;

import com.lanqiao.circle.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//私信页面
@RestController
@RequestMapping("/session")
public class sessionController {


    //根据userId查询出当前登录用户的信息：userId,userName,avatarUrl
    public Result selectUserInfo(String userId)
    {

        return Result.createSuccessResult();
    }



}
