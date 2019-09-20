package com.lanqiao.circle.service;

import com.lanqiao.circle.entity.Circles;
import com.lanqiao.circle.util.Result;

/**
 * @Author 王昊
 * @Date 2019/9/12 7:23 下午
 */
public interface CircleService {
    //得到圈子的基本信息
    public Result circleInfo(int circleId);

    //得到圈子微博
    public Result circleBlog(int circleId,int page,int size);

    //创建圈子
    public Result createCircle(Circles circles);

    //展示所有圈子
    public Result showAllCircle();

    //模糊查询圈子
    public Result showCircleLike(String name);

    //管理员查询全部圈子
    public Result normalCircles(String circleName,int page,int limit);

    //管理员删除圈子

    public Result deleteCircles(Integer circleId);

    //管理员查询圈子
    public Result findCircles(String circleName);
}
