package com.lanqiao.circle.controller;

import com.lanqiao.circle.util.Result;
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

    @PostMapping("/login")
    public Result login(){

        return  null;
    }

}
