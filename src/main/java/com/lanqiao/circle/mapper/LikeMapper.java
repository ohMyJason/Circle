package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.Like;
import com.lanqiao.circle.entity.LikeKey;

public interface LikeMapper {
    int deleteByPrimaryKey(LikeKey key);

    int insert(Like record);

    int insertSelective(Like record);

    Like selectByPrimaryKey(LikeKey key);

    int updateByPrimaryKeySelective(Like record);

    int updateByPrimaryKey(Like record);
}