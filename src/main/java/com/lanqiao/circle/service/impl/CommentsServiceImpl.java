package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.entity.Comments;
import com.lanqiao.circle.mapper.BlogMapper;
import com.lanqiao.circle.mapper.CommentsMapper;
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
 * @Author 王昊
 * @Date 2019/9/11 3:13 下午
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsMapper commentsMapper;
    @Autowired
    BlogMapper blogMapper;

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
                return Result.createSuccessResult();
            }else {
                return Result.createByFailure("评论失败!");
            }
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
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
