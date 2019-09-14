package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.mapper.BlogMapper;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.BlogService;
import com.lanqiao.circle.util.PageCheck;
import com.lanqiao.circle.util.Result;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                    String content = content1 + " //@" + name +" " +content2;
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
    public Result normalBlogs(String content,int page,int limit){
        try {
            page = PageCheck.checkPage(page);
            limit = PageCheck.checkLimit(limit);
            int start = PageCheck.calculateStart(page,limit);
            int count = blogMapper.getBlogCount(content);
            List<Blog> blogList = blogMapper.normalBlogs(start, limit, content);
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
