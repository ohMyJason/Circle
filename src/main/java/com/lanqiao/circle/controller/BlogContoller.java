package com.lanqiao.circle.controller;

import com.lanqiao.circle.annotations.UserLoginToken;
import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.entity.Comments;
import com.lanqiao.circle.entity.LikeKey;
import com.lanqiao.circle.service.BlogService;
import com.lanqiao.circle.service.CommentsService;
import com.lanqiao.circle.service.LikeService;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 王昊
 * @Date 2019/9/11 3:09 下午
 */
@RestController
@RequestMapping("/blog")
public class BlogContoller {
    @Autowired
    TokenService tokenService;
    @Autowired
    CommentsService commentsService;
    @Autowired
    LikeService likeService;
    @Autowired
    BlogService blogService;
    /**
     * 实现评论功能
     * @param httpServletRequest
     * @param comments
     * @return
     */
    @UserLoginToken
    @PostMapping("insertComment")
    public Result insertComment(HttpServletRequest httpServletRequest,Comments comments){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        comments.setUserId(userId);
        return commentsService.insertComment(comments);
    }

    /**
     * 展示评论
     * @param blogId
     * @return
     */
    @PostMapping("showComment")
    public Result showComment(@RequestParam(name = "blogId") int blogId){
        return commentsService.showComment(blogId);
    }

    /**
     * 实现点赞功能
     * @param httpServletRequest
     * @param likeKey
     * @return
     */
    @UserLoginToken
    @PostMapping("insertLike")
    public Result insertLike(HttpServletRequest httpServletRequest, LikeKey likeKey){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        likeKey.setUserId(userId);
        return likeService.insertLike(likeKey);
    }

    /**
     * 实现转发功能
     * @param httpServletRequest
     * @param repostId
     * @param forwardcontent
     * @return
     */
    @UserLoginToken
    @PostMapping("forwardBlog")
    public Result forwardBlog(HttpServletRequest httpServletRequest, @RequestParam(name = "blogId") int repostId
    ,@RequestParam(name = "forwardContent") String forwardcontent){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        Blog blog  = new Blog();
        blog.setUserId(userId);
        blog.setIsRepost(1);
        blog.setRepostId(repostId);
        blog.setContent(forwardcontent);
        return blogService.forwardBlog(blog);
    }


    /**
     * 管理员查询用户
     */
    @PostMapping("/normalBlogs")
    public Result normalBlogs(String content,int page,int limit){
        return blogService.normalBlogs(content,page,limit);
    }

    /**
     * 管理员删除用户
     *
     */
    @PostMapping("/deleteBlog")
    public Result deleteBlog(Integer blogId){
        return blogService.deleteBlog(blogId);
    }

    /**
     * 管理员查询用户
     */
    @PostMapping("/findBlog")
    public Result findBlog(String content){
        return blogService.findBlog(content);
    }

}
