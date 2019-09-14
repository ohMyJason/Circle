package com.lanqiao.circle.service;

import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.util.Result;

/**
 * @Author 王昊
 * @Date 2019/9/11 7:12 下午
 */
public interface BlogService {
    //转发微博
    public Result forwardBlog(Blog blog);


    //管理员查询用户
    public Result normalBlogs(String content,int page,int limit);

    //管理员删除用户
    public Result deleteBlog(Integer blogId);

    //管理员查询用户
    public Result findBlog(String content);
}
