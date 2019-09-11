package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.RelationShip;
import com.lanqiao.circle.entity.RelationShipKey;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
@Repository
public interface RelationShipMapper {
    int deleteByPrimaryKey(RelationShipKey key);

    int insert(RelationShip record);

    int insertSelective(RelationShip record);

    RelationShip selectByPrimaryKey(RelationShipKey key);

    int updateByPrimaryKeySelective(RelationShip record);

    int updateByPrimaryKey(RelationShip record);

    List<HashMap> getFansByUserId(Integer userId);

    List<HashMap> getBloggerByUserId(Integer userId);
}