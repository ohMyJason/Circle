package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.BlogInfo;
import com.lanqiao.circle.entity.Circles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository
public interface CirclesMapper {
    int deleteByPrimaryKey(Integer circleId);

    int insert(Circles record);

    int insertSelective(Circles record);

    Circles selectByPrimaryKey(Integer circleId);

    int updateByPrimaryKeySelective(Circles record);

    int updateByPrimaryKey(Circles record);

    HashMap getCircleInfo(Integer circleId);

    List<BlogInfo> getCircleBlogDESCByWeight(@Param("circleId")int circleId,@Param("pageIndex")int pageIndex,@Param("size")int size);

    List<Circles> getAllCircle();

    List<HashMap> getAllCirclesInfo();

    List<HashMap> getCircleUserNum(Integer circleId);

    List<HashMap> getCircleBlogNum(Integer circleId);

    List<HashMap> normalCircles(@Param("start") int start, @Param("limit") int limit, @Param("circleName")  String circleName);
    int getCirclesCount(@Param("circleName")  String circleName);
    int deleteCircles(Integer circleId);
    List<Circles> findCircles(String circleName);


    //根据名字精确查找圈子-刘佳昇
    Circles getCircleByName(@Param("circleName") String circleName);
}