package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.Circles;
import org.springframework.stereotype.Repository;

import java.util.HashMap;


@Repository
public interface CirclesMapper {
    int deleteByPrimaryKey(Integer circleId);

    int insert(Circles record);

    int insertSelective(Circles record);

    Circles selectByPrimaryKey(Integer circleId);

    int updateByPrimaryKeySelective(Circles record);

    int updateByPrimaryKey(Circles record);

    HashMap getCircleInfo(Integer circleId);
}