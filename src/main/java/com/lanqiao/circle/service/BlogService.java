package com.lanqiao.circle.service;

import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.util.Result;

import java.util.List;

/**
 * @Author ohMyJasonw
 * @Date 2019/9/11 7:12 下午
 */
public interface BlogService {
    //转发微博
    public Result forwardBlog(Blog blog);

    //展示所有微博
    public Result showAllBlog(int page,int size);

    //展示原创微博
    public Result showOriginalBlog(int page,int size);

    //展示关注的微博
    public Result showConcernBlog(int userId,int page,int size);

    //展示最新微博
    public Result showBlogByCreateTime(int page,int size);

    //管理员查询全部微博
    public Result normalBlogs(String content,int page,int limit);

    //搜索微博
    public Result searchBlogByContent(List<Integer> list);

    //管理员删除微博
    public Result deleteBlog(Integer blogId);

    //管理员设置权重
    public Result setWeight(Integer blogId);

    //管理员修改权重
    public Result editWeight(Integer blogId,Integer weight);

    //管理员查询微博
    public Result findBlog(String content);
}
