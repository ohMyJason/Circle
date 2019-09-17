package com.lanqiao.circle.mapper;

import com.lanqiao.circle.entity.BlogInfo;
import com.lanqiao.circle.entity.Users;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
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

    List<Users> getAllUser();

    HashMap selectUserInfo(Integer userId);

    Users selectUserByUserNameOrPhoneOrEmailAndPassword(Users user);

    List<BlogInfo> getUserAllBlog(@Param("userId") Integer userId,@Param("pageIndex")Integer pageIndex,@Param("size")Integer size);

    List<String> getAllResource(Integer blogId);

    BlogInfo getRepostBlog(Integer repostId);

    List<Users> normalUsers(@Param("start") int start, @Param("limit") int limit, @Param("userName")  String userName);
    int getCount(@Param("userName")  String userName);
    int deleteUsers(Integer userId);
    int bannedUsers(Integer userId);
    int unblockUsers(Integer userId);
    List<Users> findUsers(String userName);

    Users getUserByUserName(String userName);

    Users getUsersByPhone(String phone);
}