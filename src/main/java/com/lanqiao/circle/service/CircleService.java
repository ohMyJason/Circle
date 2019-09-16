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
}
