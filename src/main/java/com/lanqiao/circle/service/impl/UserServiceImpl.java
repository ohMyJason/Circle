package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘佳昇
 * @Date 2019/9/10 16:16
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UsersMapper usersMapper;
    //TODO 还没写
    @Override
    public Users findUserById(int parseInt) {
        return usersMapper.selectByPrimaryKey(parseInt);
    }
}
