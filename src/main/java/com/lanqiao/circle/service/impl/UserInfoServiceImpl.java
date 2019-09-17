package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.entity.BlogInfo;
import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.mapper.BlogMapper;
import com.lanqiao.circle.mapper.RelationShipMapper;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.UserInfoService;
import com.lanqiao.circle.util.PageCheck;
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

    @Autowired
    BlogMapper blogMapper;

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
    public Result getFansByUserId(int userId,int page,int size) {
        try{
            int pageIndex = (page-1) * size;
            List<HashMap> allFans = relationShipMapper.getFansByUserId(userId,pageIndex,size);
            return Result.createSuccessResult(allFans.size(),allFans);
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }

    @Override
    public Result getBloggerByUserId(int userId,int page,int size) {
        try{
            int pageIndex = (page-1) * size;
            List<HashMap> allBlogger = relationShipMapper.getBloggerByUserId(userId,pageIndex,size);
            return Result.createSuccessResult(allBlogger.size(),allBlogger);
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }

    @Override
    public Result getUserAvatarAndRelation(int userId) {
        try {
            HashMap<String,String> hashMap= new HashMap<>();
            Users users = usersMapper.selectByPrimaryKey(userId);
            hashMap.put("userName",users.getUserName());
            hashMap.put("avatarUrl",users.getAvatarUrl());
            List<HashMap> allFans = relationShipMapper.getFansByUserId(userId,0,1000);
            hashMap.put("fansNum",String.valueOf(allFans.size()));
            List<HashMap> allBlogger = relationShipMapper.getBloggerByUserId(userId,0,1000);
            hashMap.put("bloggerNum",String.valueOf(allBlogger.size()));
            List<Blog> blogList = blogMapper.getBlogByUserId(userId);
            hashMap.put("blogNum",String.valueOf(blogList.size()));
            return Result.createSuccessResult(hashMap);
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }

    }

    @Override
    public Result getUserAllBlog(int userId,int page,int size) {
        try{
            int pageIndex = (page-1) * size;
            List<BlogInfo> blogInfoList = usersMapper.getUserAllBlog(userId,pageIndex,size);
            for (BlogInfo blogInfo: blogInfoList) {
                List<String> resoureList = usersMapper.getAllResource(blogInfo.getBlogId());
                blogInfo.setResourceList(resoureList);
                if(blogInfo.getIsRepost() != 0) {
                    BlogInfo blogInfo1 = usersMapper.getRepostBlog(blogInfo.getRepostId());
                    List<String> resourceList1 = usersMapper.getAllResource(blogInfo1.getBlogId());
                    blogInfo1.setResourceList(resourceList1);
                    blogInfo.setBlogInfo(blogInfo1);
                }
            }
            return Result.createSuccessResult(blogInfoList.size(),blogInfoList);

        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }

    @Override
    public Result getOthersInfo(int userId) {
        try {
            HashMap<String,String> hashMap= new HashMap<>();
            Users users = usersMapper.selectByPrimaryKey(userId);
            hashMap.put("userName",users.getUserName());
            hashMap.put("avatarUrl",users.getAvatarUrl());
            hashMap.put("email",users.getEmail());
            hashMap.put("birthday",users.getBirthday());
            hashMap.put("address",users.getAddress());
            List<HashMap> allFans = relationShipMapper.getFansByUserId(userId,0,1000);
            hashMap.put("fansNum",String.valueOf(allFans.size()));
            List<HashMap> allBlogger = relationShipMapper.getBloggerByUserId(userId,0,1000);
            hashMap.put("bloggerNum",String.valueOf(allBlogger.size()));
            List<Blog> blogList = blogMapper.getBlogByUserId(userId);
            hashMap.put("blogNum",String.valueOf(blogList.size()));
            return Result.createSuccessResult(hashMap);
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }

    @Override
    public Result matchNameAndPhone(String userName, String phone) {
        try {
            Users users = usersMapper.getUserByUserName(userName);
            if(users != null && users.getPhone().equals(phone)){
                return Result.createSuccessResult(users.getUserId());
            }else {
                return Result.createByFailure("用户名与手机号不匹配！");
            }
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }

    @Override
    public Result normalUsers(String userName, int page, int limit) {
        try {
            page = PageCheck.checkPage(page);
            limit = PageCheck.checkLimit(limit);
            int start = PageCheck.calculateStart(page,limit);
            int count = usersMapper.getCount(userName);
            List<Users> allUser = usersMapper.normalUsers(start, limit, userName);
            if (count>0){
                return Result.createSuccessResult(count,allUser);
            }else {
                return Result.createByFailure("无数据");
            }
        }catch (Exception e ){
            System.out.println(e.getCause());
            return Result.createByFailure("异常");
        }

    }
    @Override
    public Result deleteUsers(Integer userId){
        try{
            if (usersMapper.deleteUsers(userId)>0){
                return Result.createSuccessResult();
            }else {
                return Result.createByFailure("ERROR");
            }
        }catch (Exception e){
            System.out.println(e.getCause());
            return Result.createByFailure("异常");
        }
    }
    @Override
    public Result bannedUsers(Integer userId){
        try{
            if (usersMapper.bannedUsers(userId)>0){
                return Result.createSuccessResult();
            }else {
                return Result.createByFailure("ERROR");
            }
        }catch (Exception e){
            System.out.println(e.getCause());
            return Result.createByFailure("异常");
        }
    }
    @Override
    public Result unblockUsers(Integer userId){
        try {
            if (usersMapper.unblockUsers(userId)>0){
                return Result.createSuccessResult();
            }else {
                return Result.createByFailure("ERROR");
            }
        }catch (Exception e){
            System.out.println(e.getCause());
            return Result.createByFailure("异常");
        }
    }
    @Override
    public Result findUsers(String userName){
        try {
            List<Users> usersList = usersMapper.findUsers(userName);
            return Result.createSuccessResult();
        }catch (Exception e){
            System.out.println(e.getCause());
            return Result.createByFailure("异常");
        }
    }
}
