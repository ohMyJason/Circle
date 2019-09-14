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
     *
     * @return Users
     * @Param 头部加token
     */
    @UserLoginToken
    @PostMapping("getUserInfo")
    public Result getUserInfoByUserId(HttpServletRequest httpServletRequest) {
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getUserInfoByUserId(userId);
    }

    /**
     * 修改用户基本信息
     *
     * @return int
     * @Param 头部加token, Users
     */
    @UserLoginToken
    @PostMapping("updateUserInfo")
    public Result updateUserInfo(HttpServletRequest httpServletRequest, Users users) {
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        users.setUserId(userId);
        return userInfoService.updateUserInfo(users);
    }

    /**
     * 自己查询粉丝
     *
     * @return FansList
     * @Param 头部加token
     */
    @UserLoginToken
    @PostMapping("getAllFansSelf")
    public Result getAllFansToken(HttpServletRequest httpServletRequest) {
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getFansByUserId(userId);
    }

    /**
     * 查询别人的粉丝
     *
     * @return FansList
     * @Param userId
     */
    @PostMapping("getAllFansOthers")
    public Result getAllFansByUserId(@RequestParam(name = "userId") int userId) {
        return userInfoService.getFansByUserId(userId);
    }

    /**
     * 自己查询关注
     *
     * @return BloggerList
     * @Param 头部加token
     */
    @UserLoginToken
    @PostMapping("getAllBloggerSelf")
    public Result getAllBloggerByToken(HttpServletRequest httpServletRequest) {
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getBloggerByUserId(userId);
    }

    /**
     * 查询别人的关注
     *
     * @return BloggerList
     * @Param 头部加token
     */
    @PostMapping("getAllBloggerOthers")
    public Result getAllBloggerByUserId(@RequestParam(name = "userId") int userId) {
        return userInfoService.getBloggerByUserId(userId);
    }

    /**
     * 查看自己主页除微博外的东西
     * @param httpServletRequest
     * @return
     */
    @UserLoginToken
    @PostMapping("getUserAvatarAndRelation")
    public Result getUserIndex(HttpServletRequest httpServletRequest) {
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getUserAvatarAndRelation(userId);
    }

    /**
     * 查看自己主页所有微博
     * @param httpServletRequest
     * @return
     */

    @UserLoginToken
    @PostMapping("getUserAllBlog")
    public Result getUserAllBlog(HttpServletRequest httpServletRequest){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return userInfoService.getUserAllBlog(userId);
    }

    /**
     * 管理员查询用户信息
     */
    @PostMapping("/getNormalUser")
    public Result normalUsers(String userName, int page, int limit){
        return userInfoService.normalUsers(userName,page,limit);
    }
    /**
     * 管理员删除用户
     */
    @PostMapping("/delete")
    public Result deleteUsers(Integer usersId){
        return userInfoService.deleteUsers(usersId);
    }
    /**
     * 管理员封禁用户
     */
    @PostMapping("/banned")
    public Result bannedUsers(Integer usersId){
        return userInfoService.bannedUsers(usersId);
    }
    /**
     * 管理员查询用户
     */
    @PostMapping("/find")
    public Result findUsers(String userName){
        return userInfoService.findUsers(userName);
    }
}