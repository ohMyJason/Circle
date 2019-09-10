package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.Circles;

public interface CirclesMapper {
    int deleteByPrimaryKey(Integer circleId);

    int insert(Circles record);

    int insertSelective(Circles record);

    Circles selectByPrimaryKey(Integer circleId);

    int updateByPrimaryKeySelective(Circles record);

    int updateByPrimaryKey(Circles record);
}