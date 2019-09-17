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
        return Result.createSuccessResult(userId);
    }


    @PostMapping("/testSetRedis")
    public Result testRedis(String key,String member,Double count) {
        redisUtil.addZSet(key,count,member);
        return Result.createSuccessResult();
    }

    @PostMapping("/testGetRedis")
    public Result testGetRedis(String key) {
        System.out.println(redisUtil.getZsetRange(key));
        return Result.createSuccessResult();
    }

    @PostMapping("/testIncrRedis")
    public  Result testIncrRedis(){
        System.out.println(redisUtil.updateZet("circle-range",1,"搞笑圈"));
        return Result.createSuccessResult();
    }

    @PostMapping("/testHashKey")
    public  Result testHashKey(String member){
        if (redisUtil.hasMember("circle-range",member)){
            return Result.createSuccessResult("有");
        }else {
            return Result.createSuccessResult("没有");
        }
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
