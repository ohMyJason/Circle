package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.Blog;

import java.util.HashMap;
import java.util.List;

public interface BlogMapper {
    int deleteByPrimaryKey(Integer blogId);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer blogId);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

    List<HashMap> getUserAllblogByUserId(Integer userId);
}