package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.Letter;
import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.mapper.LetterMapper;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.SessionService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author ohMyJasonq
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

    public Result selectMessageLog(int senterId,String userName)
    {
        Integer receiverId = usersMapper.getUserByUserName(userName).getUserId();

        Letter letter = new Letter();
        letter.setSenterId(senterId);

        letter.setReceiverId(receiverId);
        List<HashMap> messageLog = letterMapper.selectMessageLog(letter);
        return Result.createSuccessResult(messageLog.size(),messageLog);
    }

    //发送消息
    public Result sendMsg(Integer senterId,String userName,String  letterContent,String  resourceUrl)
    {
        String  data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Users users = new Users();
        users.setUserName(userName);
        Integer receiverId = usersMapper.getUserByUserName(userName).getUserId();
        if(senterId == receiverId)
        {
            return Result.createByFailure("不能和自己聊天噢");
        }
        Letter letter = new Letter();
        letter.setSenterId(senterId);
        letter.setReceiverId(receiverId);
        letter.setSendTime(data);

        if(resourceUrl != null)
        {
            letter.setLetterContent("[图片]");
            letter.setResourceUrl(resourceUrl);
            letter.setType(1);
        }
        else if(letterContent != null)
        {
            letter.setLetterContent(letterContent);
            letter.setType(0);
        }


        int col = letterMapper.insertSelective(letter);
        if(col == 0)
        {
            return Result.createByFailure("信息发送失败，请重试");
        }
        return Result.createSuccessResult();
    }

    //查找两个人是否又聊天信息
    @Override
    public Result selectSession(Integer senterId,String userName) {
        Integer receiverId = usersMapper.getUserByUserName(userName).getUserId();
//        if(senterId == receiverId)
//        {
//            return Result.createByFailure("不能和自己聊天噢");
//        }
        Letter letter = new Letter();
        letter.setSenterId(senterId);
        letter.setReceiverId(receiverId);
        HashMap res = letterMapper.selectSession(letter);
        if(res == null)
        {
            return Result.createSuccessResult(0);
        }
        else if(res != null)
        {
            return Result.createSuccessResult(1);
        }
        else
        {
            return Result.createByFailure("未知错误，联系管理员");
        }
    }
}
