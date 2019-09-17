package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.Letter;
import com.lanqiao.circle.util.Result;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface LetterMapper {
    int deleteByPrimaryKey(Integer letterId);

    int insert(Letter record);

    int insertSelective(Letter record);

    Letter selectByPrimaryKey(Integer letterId);

    int updateByPrimaryKeySelective(Letter record);

    int updateByPrimaryKey(Letter record);

    List<HashMap> selectUserList(Integer userId);

    Letter selecLastLetter(Letter letter);

    List<HashMap> selectMessageLog(Letter letter);

    HashMap selectSession(Letter letter);

}