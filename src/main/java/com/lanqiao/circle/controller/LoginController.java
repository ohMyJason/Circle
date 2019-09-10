package com.lanqiao.circle.controller;

import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘佳昇
 * @Date 2019/9/10 15:15
 */

@RestController
@RequestMapping("/userLogin")
public class LoginController {


    @Autowired
    UsersMapper usersMapper;


    /**
     * 登录 可选择手机号登录，用户名登录，邮箱登录
     * @param loginName
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result login(String loginName,String password){


        return  null;
    }

}
