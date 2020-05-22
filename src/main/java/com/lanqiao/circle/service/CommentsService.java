package com.lanqiao.circle.service;

import com.lanqiao.circle.entity.Comments;
import com.lanqiao.circle.util.Result;

/**
 * @Author ohMyJasonw
 * @Date 2019/9/11 3:13 下午
 */
public interface CommentsService {
    //插入评论
    public Result insertComment(Comments comments);
    //展示评论
    public Result showComment(int blogId);
}
