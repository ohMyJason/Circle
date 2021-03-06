package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.BlogItem;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogItemMapper {
    int deleteByPrimaryKey(Integer blogItemId);

    int insert(BlogItem record);

    int insertSelective(BlogItem record);

    BlogItem selectByPrimaryKey(Integer blogItemId);

    int updateByPrimaryKeySelective(BlogItem record);

    int updateByPrimaryKey(BlogItem record);
}