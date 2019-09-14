package com.lanqiao.circle.controller;

import com.lanqiao.circle.entity.Circles;
import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.util.CommentUtil;
import com.lanqiao.circle.util.RedisUtil;
import com.lanqiao.circle.util.Result;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author 刘佳昇
 * @Date 2019/9/10 11:24
 */

@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    UsersMapper usersMapper;

    @Autowired
    TokenService tokenService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CommentUtil commentUtil;

    @GetMapping("/testMapGet")
    public Result testMapGet() {
        return null;
    }

    @PostMapping("/testGetUserIdByToken")
    public Result testGetUserIdByToken(HttpServletRequest httpServletRequest) {
        String userId = tokenService.getUserId(httpServletRequest);
        System.out.println(userId);
        return Result.createSuccessResult();
    }


    @PostMapping("/testSetRedis")
    public Result testRedis() {
        if (redisUtil.hasKey("testString2")) {
            redisUtil.incr("testString", 3);
        } else {
            redisUtil.set("testString2", 1);
        }
        return Result.createSuccessResult();
    }

    @PostMapping("/testGetRedis")
    public Result testGetRedis() {
        String testString = (String) redisUtil.get("testString");
        return Result.createSuccessResult(testString);
    }

    @PostMapping("/toMd5")
    public Result toMd5() {
        List<Users> allUsers = usersMapper.getAllUser();
        for (Users user : allUsers) {
            user.setPassword(commentUtil.getMd5(user.getPassword()));
            usersMapper.updateByPrimaryKeySelective(user);
        }
        return Result.createSuccessResult();
    }


}
