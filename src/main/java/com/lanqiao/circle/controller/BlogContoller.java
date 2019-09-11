package com.lanqiao.circle.controller;

import com.lanqiao.circle.annotations.UserLoginToken;
import com.lanqiao.circle.entity.Comments;
import com.lanqiao.circle.service.CommentsService;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 王昊
 * @Date 2019/9/11 3:09 下午
 */
@RestController
@RequestMapping("/blog")
public class BlogContoller {
    @Autowired
    TokenService tokenService;
    @Autowired
    CommentsService commentsService;

    @UserLoginToken
    @PostMapping("insertComment")
    public Result insertComment(HttpServletRequest httpServletRequest, Comments comments){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        comments.setUserId(userId);
        return commentsService.insertComment(comments);
    }
}
