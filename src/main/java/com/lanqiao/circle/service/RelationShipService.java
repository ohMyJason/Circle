package com.lanqiao.circle.service;

import com.lanqiao.circle.util.Result;

/**
 * @Author 王昊
 * @Date 2019/9/16 6:28 下午
 */
public interface RelationShipService {
    //确认是否关注
    public Result ifConcern(int loginId,int userId);

    //加关注或者取消关注
    public Result addConcernOrSub(int loginId,int userId);
}
