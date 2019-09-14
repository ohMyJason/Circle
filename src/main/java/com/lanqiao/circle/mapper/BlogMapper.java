package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.entity.BlogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface BlogMapper {
    int deleteByPrimaryKey(Integer blogId);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer blogId);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

    List<HashMap> getUserAllblogByUserId(Integer userId);

    List<BlogInfo> showAllBlog(@Param("pageIndex")int pageIndex,@Param("size")int size);

    List<BlogInfo> showOriginalBlog(@Param("pageIndex")int pageIndex,@Param("size")int size);

    List<Blog> normalBlogs(@Param("start") int start, @Param("limit") int limit, @Param("content")  String content);

    int getBlogCount(@Param("content")  String content);

    int deleteBlog(Integer blogId);

    List<Blog> findBlog(String content);

}