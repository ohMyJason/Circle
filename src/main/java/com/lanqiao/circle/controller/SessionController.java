package com.lanqiao.circle.controller;

import com.lanqiao.circle.annotations.UserLoginToken;
import com.lanqiao.circle.entity.Letter;
import com.lanqiao.circle.service.SessionService;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

//私信页面
@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    SessionService sessionService;

    @Autowired
    TokenService tokenService;

    //根据userId查询出当前登录用户的信息：userId,userName,avatarUrl
    @UserLoginToken
    @PostMapping("/getUserInfo")
    public Result selectUserInfo(HttpServletRequest httpServletRequest)
    {
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return sessionService.selectUserInfo(userId);
    }

    //根据userId查找会话列表
    @UserLoginToken
    @PostMapping("/getUserList")
    public Result selectUserList(HttpServletRequest httpServletRequest){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return sessionService.selectUserList(userId);
    }

    //根据senterId和receiverId查询历史消息
    @UserLoginToken
    @PostMapping("/getMessageLog")
    public Result selectMessageLog(HttpServletRequest httpServletRequest,Integer receiverId)
    {
        int senterId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        Letter letter = new Letter();
        letter.setSenterId(senterId);
        letter.setReceiverId(receiverId);
        return sessionService.selectMessageLog(letter);
    }

    //发送信息
    @UserLoginToken
    @PostMapping("/sendMsg")
    public Result sendMsg(HttpServletRequest httpServletRequest,Integer receiverId,String letterContent,String resourceUrl)
    {
        int senterId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        String  data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Letter letter = new Letter();
        letter.setSenterId(senterId);
        letter.setReceiverId(receiverId);
        letter.setSendTime(data);
        if(letterContent != null)
        {
            letter.setLetterContent(letterContent);
            letter.setType(0);
        }if(resourceUrl != null)
        {
            letter.setLetterContent("[图片]");
            letter.setResourceUrl(resourceUrl);
            letter.setType(1);
        }
        return sessionService.sendMsg(letter);
    }
}
