package com.lanqiao.circle.service;

import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.util.Result;
import org.springframework.stereotype.Service;

public interface UserInfoService {
    // 查询用户基本信息
    public Result getUserInfoByUserId(int userId);

    // 修改用户信息
    public Result updateUserInfo(Users users);

    // 查询用户粉丝
    public Result getFansByUserId(int userId);

    // 查询用户关注
    public Result getBloggerByUserId(int userId);


}
