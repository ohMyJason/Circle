package com.lanqiao.circle.service;

import com.lanqiao.circle.entity.Letter;
import com.lanqiao.circle.util.Result;
/**
 * @Author ohMyJasonq
 * @Date 2019/9/10 16:34
 */
public interface SessionService {


    //根据userId查询出当前登录用户的信息：userId,userName,avatarUrl
    public Result selectUserInfo(Integer userId);

    //根据userId查询会话列表
    public Result selectUserList(Integer userId);

    //根据senterId和receiverId查询历史消息
//    public Result selectMessageLog(Letter letter);
    public Result selectMessageLog(int senterId,String userName);

    //发送信息
    public Result sendMsg(Integer senterId,String userName,String  letterContent,String  resourceUrl);

    //查询两人是否已经建立会话
    public Result selectSession(Integer senterId, String userName);
}
