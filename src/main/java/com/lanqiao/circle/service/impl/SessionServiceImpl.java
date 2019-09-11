package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.Letter;
import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.mapper.LetterMapper;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.SessionService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author 钱琦瑛
 * @Date 2019/9/10 16:34
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    LetterMapper letterMapper;

    @Autowired
    UsersMapper usersMapper;

    //根据userId查询出当前登录用户的信息：userId,userName,avatarUrl
    @Override
    public Result selectUserInfo(Integer userId) {
        HashMap userInfo = usersMapper.selectUserInfo(userId);
        if(userInfo == null)
        {
            return Result.createByFailure("用户不存在，请联系管理员");
        }
        return Result.createSuccessResult(userInfo);
    }

    //查询会话列表
    @Override
    public Result selectUserList(Integer userId) {
        //查询出userId（对方）,userName,avatarUrl
        List<HashMap> userList= letterMapper.selectUserList(userId);

        //根据自己和对方的id号查询出最近的信息以及发送时间
        //对于查询出来的每一个会话信息
        for(HashMap hashMap:userList){
            Letter temp = new Letter();
            temp.setSenterId(userId);
            Integer receiverId =  (Integer)hashMap.get("userId");
            temp.setReceiverId(receiverId);
            //调用
            Letter letter = letterMapper.selecLastLetter(temp);
            hashMap.put("letterContent",letter.getLetterContent());
            hashMap.put("sendTime",letter.getSendTime());
        }
        return Result.createSuccessResult(userList.size(),userList);
    }

    //根据senterId和receiverId查询历史消息
    public Result selectMessageLog(Letter letter)
    {
        List<HashMap> messageLog = letterMapper.selectMessageLog(letter);
        return Result.createSuccessResult(messageLog.size(),messageLog);
    }
}
