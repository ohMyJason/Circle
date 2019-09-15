package com.lanqiao.circle.controller;

import com.lanqiao.circle.annotations.UserLoginToken;
import com.lanqiao.circle.entity.Letter;
import com.lanqiao.circle.service.SessionService;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.util.FileUploadUtil;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    FileUploadUtil fileUploadUtil;

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

    //查询历史消息
    @UserLoginToken
    @PostMapping("/getMessageLog")
    public Result selectMessageLog(HttpServletRequest httpServletRequest,String userName)
    {
        int senterId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return sessionService.selectMessageLog(senterId,userName);
    }

    //发送信息
    @UserLoginToken
    @PostMapping("/sendMsg")
    public Result sendMsg(HttpServletRequest httpServletRequest,String userName,String letterContent,String resourceUrl)
    {
        int senterId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return sessionService.sendMsg(senterId,userName, letterContent,resourceUrl);
    }

    @UserLoginToken
    @PostMapping("/test")
    public Result test(@RequestParam(name = "file")MultipartFile file)
    {
        String relaPath = fileUploadUtil.fileUpload(file,2);
        System.out.println(relaPath);
        return Result.createByFailure(relaPath);
    }
}
