package com.lanqiao.circle.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.entity.Comments;
import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.mapper.BlogMapper;
import com.lanqiao.circle.mapper.CommentsMapper;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.CommentsService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * @Author ohMyJasonw
 * @Date 2019/9/11 3:13 下午
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsMapper commentsMapper;
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    UsersMapper usersMapper;

    @Transactional
    @Override
    public Result insertComment(Comments comments) {
        try{
            String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            comments.setCommentTime(timeStr1);
            if (commentsMapper.insertSelective(comments) > 0){
                Blog blog = blogMapper.selectByPrimaryKey(comments.getBlogId());
                blog.setWeight(blog.getWeight() + 1);
                blogMapper.updateByPrimaryKeySelective(blog);
                //返回评论实例，前端可以立即显示
                JSONObject commentRes = new JSONObject();
                Users commentUser = usersMapper.selectByPrimaryKey(comments.getUserId());
                commentRes.put("commentTime",comments.getCommentTime());
                commentRes.put("userName",commentUser.getUserName());
                commentRes.put("userId",commentUser.getUserId());
                commentRes.put("commentContent",comments.getCommentContent());
                commentRes.put("commentContent",comments.getCommentContent());
                commentRes.put("avatarUrl",commentUser.getAvatarUrl());
                return Result.createSuccessResult(commentRes);
            }else {
                return Result.createByFailure("评论失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.createByFailure(e.getMessage());
        }
    }

    @Override
    public Result showComment(int blogId) {
        try{
            List<HashMap> hashMapList = commentsMapper.showComments(blogId);
            return Result.createSuccessResult(hashMapList.size(),hashMapList);
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");

        }
    }
}
