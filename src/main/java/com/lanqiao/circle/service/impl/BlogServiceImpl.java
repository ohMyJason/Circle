package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.entity.BlogInfo;
import com.lanqiao.circle.mapper.BlogMapper;
import com.lanqiao.circle.mapper.RelationShipMapper;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.BlogService;
import com.lanqiao.circle.util.PageCheck;
import com.lanqiao.circle.util.Result;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author 王昊
 * @Date 2019/9/11 7:13 下午
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    RelationShipMapper relationShipMapper;

    @Override
    public Result forwardBlog(Blog blog) {
        try {
            Blog blog1 = blogMapper.selectByPrimaryKey(blog.getRepostId());
            if (blog1 != null){
                String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                blog.setCircleId(blog1.getCircleId());
                blog.setCreateTime(timeStr1);
                if(blog1.getIsRepost() != 0){
                    String content1 = blog.getContent();
                    String content2 = blog1.getContent();
                    String name = usersMapper.selectByPrimaryKey(blog1.getUserId()).getUserName();
//                    if (blog.getUserId()==blog1.getUserId()){
//                        content = content1 + "//<a href=\"/user/user-center/personal.html\">@" + name +"</a>" + content2;
//                    }else {
                    String content = content1 + "//<a href=\"/user/user-center/personals.html?userId=" + blog1.getUserId() +"\">@"+ name + "：</a>" + content2;
//                    }
                    blog.setContent(content);
                    blog.setRepostId(blog1.getRepostId());
                }else {}
                if(blogMapper.insertSelective(blog) >0){
                    return Result.createSuccessResult();
                }else {
                    return Result.createByFailure("一级转载失败！");
                }
            }else {
                return Result.createByFailure("原微博不存在！");
            }
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }

    @Override
    public Result showAllBlog(int page,int size) {
        try {
            int pageIndex = (page-1) * size;
            List<BlogInfo> blogInfoList = blogMapper.showAllBlog(pageIndex,size);
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
    public Result showOriginalBlog(int page, int size) {
      try {
          int pageIndex = (page-1) * size;
          List<BlogInfo> blogInfoList = blogMapper.showOriginalBlog(pageIndex,size);
          for (BlogInfo blogInfo: blogInfoList) {
              List<String> resoureList = usersMapper.getAllResource(blogInfo.getBlogId());
              blogInfo.setResourceList(resoureList);
          }
          return Result.createSuccessResult(blogInfoList.size(),blogInfoList);
      }catch (Exception e){
          return Result.createByFailure("操作异常，请联系管理人员！");

      }
    }

    @Override
    public Result showConcernBlog(int userId, int page, int size) {
        try{
            int pageIndex = (page-1) * size;
            List<HashMap> allBlogger = relationShipMapper.getAllBloggerUserId(userId);
            List<Integer> integers = new ArrayList<>();
            for (HashMap hashMap:allBlogger) {
                integers.add((Integer) hashMap.get("bloggerId"));
            }
            List<BlogInfo> blogInfoList = blogMapper.showConcernBlog(integers,pageIndex,size);
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
            e.printStackTrace();
            return Result.createByFailure("操作异常，请联系管理人员！");

        }
    }

    @Override
    public Result showBlogByCreateTime(int page, int size) {
        try {
            int pageIndex = (page - 1) * size;
            List<BlogInfo> blogInfoList = blogMapper.showBlogByCreateTime(pageIndex, size);
            for (BlogInfo blogInfo : blogInfoList) {
                List<String> resoureList = usersMapper.getAllResource(blogInfo.getBlogId());
                blogInfo.setResourceList(resoureList);
                if (blogInfo.getIsRepost() != 0) {
                    BlogInfo blogInfo1 = usersMapper.getRepostBlog(blogInfo.getRepostId());
                    List<String> resourceList1 = usersMapper.getAllResource(blogInfo1.getBlogId());
                    blogInfo1.setResourceList(resourceList1);
                    blogInfo.setBlogInfo(blogInfo1);
                }
            }
            return Result.createSuccessResult(blogInfoList.size(), blogInfoList);
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }


    @Override
    public Result normalBlogs(String content,int page,int limit){
        try {
            page = PageCheck.checkPage(page);
            limit = PageCheck.checkLimit(limit);
            int start = PageCheck.calculateStart(page,limit);
            int count = blogMapper.getBlogCount(content);
            List<HashMap> blogList = blogMapper.normalBlogs(start, limit, content);
            if (count>0){
                return Result.createSuccessResult(count,blogList);
            }else {
                return Result.createByFailure("无数据");
            }
        }catch (Exception e ){
            System.out.println(e.getCause());
            return Result.createByFailure("异常");
        }
    }

    @Override
    public Result searchBlogByContent(List<Integer> list) {
        try{
            List<BlogInfo> blogInfoList = new ArrayList<>();
            for (Integer i:list) {
                BlogInfo blogInfo = usersMapper.getRepostBlog(i);
                List<String> resoureList = usersMapper.getAllResource(blogInfo.getBlogId());
                blogInfo.setResourceList(resoureList);
                if (blogInfo.getIsRepost() != 0) {
                    BlogInfo blogInfo1 = usersMapper.getRepostBlog(blogInfo.getRepostId());
                    List<String> resourceList1 = usersMapper.getAllResource(blogInfo1.getBlogId());
                    blogInfo1.setResourceList(resourceList1);
                    blogInfo.setBlogInfo(blogInfo1);
                }
                blogInfoList.add(blogInfo);
            }
            return Result.createSuccessResult(blogInfoList.size(),blogInfoList);
        }catch (Exception e){
            return Result.createByFailure();
        }
    }

    @Override
    public Result deleteBlog(Integer blogId){
        try{
            if (blogMapper.deleteBlog(blogId)>0){
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
    public Result setWeight(Integer blogId){
        try {
            if (blogMapper.setWeight(blogId)>0){
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
    public Result editWeight(Integer blogId,Integer weight){
        try {
            if(blogMapper.editWeight(blogId,weight) > 0){
                return Result.createSuccessResult();
            }
            else{
                return Result.createByFailure("数据库错误");
            }
        }
        catch (Exception e){
            System.out.println(e.getCause());
            return Result.createByFailure("异常");
        }
    }
    @Override
    public Result findBlog(String content){
        try {
            List<Blog> blogList = blogMapper.findBlog(content);
            return Result.createSuccessResult();
        }catch (Exception e){
            System.out.println(e.getCause());
            return Result.createByFailure("异常");
        }
    }
}
