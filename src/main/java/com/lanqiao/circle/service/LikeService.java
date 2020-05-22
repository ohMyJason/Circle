package com.lanqiao.circle.service;


import com.lanqiao.circle.entity.LikeKey;
import com.lanqiao.circle.util.Result;

/**
 * @Author ohMyJasonw
 * @Date 2019/9/11 4:19 下午
 */
public interface LikeService {
    //点赞
    public Result insertLike(LikeKey likeKey);
}
