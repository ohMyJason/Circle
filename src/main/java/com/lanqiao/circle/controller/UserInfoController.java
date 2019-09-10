package com.lanqiao.circle.controller;


import com.lanqiao.circle.annotations.UserLoginToken;
import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.service.UserInfoService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    TokenService tokenService;


    /**
     * 查询用户基本信息
     * @Param   头部加token
     * @return  Users
     */
    @UserLoginToken
    @PostMapping("getUserInfo")
    public Result getUserInfoByUserId(HttpServletRequest httpServletRequest){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getUserInfoByUserId(userId);
    }

    /**
     * 修改用户基本信息
     * @Param  头部加token, Users
     * @return  int
     */
    @UserLoginToken
    @PostMapping("updateUserInfo")
    public Result updateUserInfo(HttpServletRequest httpServletRequest,Users users){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        users.setUserId(userId);
        return userInfoService.updateUserInfo(users);
    }

    /**
     * 自己查询粉丝
     * @Param  头部加token
     * @return  FansList
     */
    @UserLoginToken
    @PostMapping("getAllFansSelf")
    public Result getAllFansToken(HttpServletRequest httpServletRequest){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getFansByUserId(userId);
    }
    /**
     * 查询别人的粉丝
     * @Param  userId
     * @return  FansList
     */
    @PostMapping("getAllFansOthers")
    public Result getAllFansByUserId(@RequestParam(name = "userId")int userId){
        return userInfoService.getFansByUserId(userId);
    }
    /**
     * 自己查询关注
     * @Parm  头部加token
     * @return  BloggerList
     */
    @UserLoginToken
    @PostMapping("getAllBloggerSelf")
    public Result getAllBloggerByToken(HttpServletRequest httpServletRequest){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getBloggerByUserId(userId);
    }

    /**
     * 查询别人的关注
     * @Parm  头部加token
     * @return  BloggerList
     */
    @PostMapping("getAllBloggerOthers")
    public Result getAllBloggerByUserId(@RequestParam(name = "userId")int userId){
        return userInfoService.getBloggerByUserId(userId);
    }

}
