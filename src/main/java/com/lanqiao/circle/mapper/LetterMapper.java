package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.Letter;

public interface LetterMapper {
    int deleteByPrimaryKey(Integer letterId);

    int insert(Letter record);

    int insertSelective(Letter record);

    Letter selectByPrimaryKey(Integer letterId);

    int updateByPrimaryKeySelective(Letter record);

    int updateByPrimaryKey(Letter record);
}