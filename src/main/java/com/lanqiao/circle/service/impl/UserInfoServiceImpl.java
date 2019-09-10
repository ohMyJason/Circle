package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.mapper.RelationShipMapper;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.UserInfoService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UsersMapper usersMapper;

    @Autowired
    RelationShipMapper relationShipMapper;
    @Override
    public Result getUserInfoByUserId(int userId) {
        try {
            Users users = usersMapper.selectByPrimaryKey(userId);
            if(users != null){
                return Result.createSuccessResult(users);
            }else {
                return Result.createByFailure("查无此人！");
            }
        }catch(Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }

    @Override
    public Result updateUserInfo(Users users) {
        try{
            if(usersMapper.updateByPrimaryKeySelective(users) > 0){
                return Result.createSuccessResult();
            }else {
                return Result.createByFailure("修改信息失败！");
            }
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }

    @Override
    public Result getFansByUserId(int userId) {
        try{
            List<HashMap> allFans = relationShipMapper.getFansByUserId(userId);
            return Result.createSuccessResult(allFans.size(),allFans);
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }

    @Override
    public Result getBloggerByUserId(int userId) {
        try{
            List<HashMap> allBlogger = relationShipMapper.getBloggerByUserId(userId);
            return Result.createSuccessResult(allBlogger.size(),allBlogger);
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }
}
