package com.lanqiao.circle.controller;


import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.service.UserInfoService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    /**
     * 查询用户基本信息
     * @Param  userId
     * @return  Users
     */
    @PostMapping("getUserInfo")
    public Result getUserInfoByUserId(@RequestParam(name = "userId") int userId){
        return userInfoService.getUserInfoByUserId(userId);
    }

    /**
     * 修改用户基本信息
     * @Param  Users
     * @return  int
     */
    @PostMapping("updateUserInfo")
    public Result updateUserInfo(Users users){
        return userInfoService.updateUserInfo(users);
    }

    /**
     * 查询用户粉丝
     * @Param  userId
     * @return  FansList
     */
    @PostMapping("getAllFans")
    public Result getAllFansByUserId(@RequestParam(name = "userId") int userId){
        return userInfoService.getFansByUserId(userId);
    }

    /**
     * 查询用户关注
     * @Parm  userId
     * @return  BloggerList
     */
    @PostMapping("getAllBlogger")
    public Result getAllBloggerByUserId(@RequestParam(name = "userId") int userId){
        return userInfoService.getBloggerByUserId(userId);
    }

}
