package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.Comments;
import com.lanqiao.circle.mapper.CommentsMapper;
import com.lanqiao.circle.service.CommentsService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author 王昊
 * @Date 2019/9/11 3:13 下午
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsMapper commentsMapper;

    @Override
    public Result insertComment(Comments comments) {
        try{
            String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            comments.setCommentTime(timeStr1);
            if (commentsMapper.insertSelective(comments) > 0){
                return Result.createSuccessResult();
            }else {
                return Result.createByFailure("评论失败!");
            }
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }
}
