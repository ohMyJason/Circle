package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.BlogInfo;
import com.lanqiao.circle.entity.Users;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface UsersMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    List<HashMap> getAllUser();

    HashMap selectUserInfo(Integer userId);

    Users selectUserByUserNameOrPhoneOrEmailAndPassword(Users user);

    List<BlogInfo> getUserAllBlog(Integer userId);

    List<String> getAllResource(Integer blogId);

    BlogInfo getRepostBlog(Integer repostId);
}