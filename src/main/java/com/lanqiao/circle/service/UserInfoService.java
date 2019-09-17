package com.lanqiao.circle.service;

import com.lanqiao.circle.entity.BlogInfo;
import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.util.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

public interface UserInfoService {
    // 查询用户基本信息
    public Result getUserInfoByUserId(int userId);

    // 修改用户信息
    public Result updateUserInfo(Users users);

    // 查询用户粉丝
    public Result getFansByUserId(int userId,int page,int size);

    // 查询用户关注
    public Result getBloggerByUserId(int userId,int page,int size);

    // 查看主页头像和关注
    public Result getUserAvatarAndRelation(int userId);

    // 查看主页微博
    public Result getUserAllBlog(int userId,int page,int size);

    //查看别人主页基本信息
    public Result getOthersInfo(int userId);

    //管理员查询用户
    public Result normalUsers(String userName,int page,int limit);

    //管理员删除用户
    public Result deleteUsers(Integer userId);

    //管理员封禁用户
    public Result bannedUsers(Integer userId);

    //管理员解封用户
    public Result unblockUsers(Integer userId);

    //管理员查询用户
    public Result findUsers(String userName);
}
