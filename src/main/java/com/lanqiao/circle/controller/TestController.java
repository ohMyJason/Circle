package com.lanqiao.circle.controller;

import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.util.Result;
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



    @GetMapping("/testMapGet")
    public Result testMapGet(){
        List<HashMap> allUser = usersMapper.getAllUser();
        for (HashMap hashMap : allUser) {
            System.out.println(hashMap.toString());
        }
        return Result.createSuccessResult();
    }

    @PostMapping("/testGetUserIdByToken")
    public Result testGetUserIdByToken(HttpServletRequest httpServletRequest){
        String userId = tokenService.getUserId(httpServletRequest);
        System.out.println(userId);
        return Result.createSuccessResult();
    }
}
