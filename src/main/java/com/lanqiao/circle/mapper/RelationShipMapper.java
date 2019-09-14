package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.RelationShip;
import com.lanqiao.circle.entity.RelationShipKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface RelationShipMapper {
    int deleteByPrimaryKey(RelationShipKey key);

    int insert(RelationShip record);

    int insertSelective(RelationShip record);

    RelationShip selectByPrimaryKey(RelationShipKey key);

    int updateByPrimaryKeySelective(RelationShip record);

    int updateByPrimaryKey(RelationShip record);

    List<HashMap> getFansByUserId(@Param("userId") Integer userId, @Param("pageIndex")Integer pageIndex, @Param("size")Integer size);

    List<HashMap> getBloggerByUserId(@Param("userId") Integer userId,@Param("pageIndex")Integer pageIndex,@Param("size")Integer size);
}