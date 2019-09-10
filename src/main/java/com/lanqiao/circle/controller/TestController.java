package com.lanqiao.circle.controller;

import com.lanqiao.circle.config.PathConfig;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘佳昇
 * @Date 2019/9/10 11:24
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    PathConfig pathConfig;


    @GetMapping("/testGetPath")
    public Result testGetPath(){
        return Result.createSuccessResult(pathConfig.getChatImgPath());
    }
}
