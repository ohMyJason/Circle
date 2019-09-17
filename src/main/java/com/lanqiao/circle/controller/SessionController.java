package com.lanqiao.circle.controller;

import com.lanqiao.circle.annotations.UserLoginToken;
import com.lanqiao.circle.entity.Letter;
import com.lanqiao.circle.service.SessionService;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.service.impl.FileUploadService;
import com.lanqiao.circle.util.FileUploadResult;
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

    @Autowired
    private FileUploadService fileUploadService;

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
//        System.out.println("controller" + resourceUrl);
//        return Result.createSuccessResult();
        return sessionService.sendMsg(senterId,userName, letterContent,resourceUrl);
    }

    //查询两人是否已经建立会话
//    @UserLoginToken
    @PostMapping("/selectSession")
    public Result selectSession(HttpServletRequest httpServletRequest,String userName)
    {
        int senterId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        return sessionService.selectSession(senterId,userName);
    }




    @UserLoginToken
    @PostMapping("/test")
    public Result test(@RequestParam(name = "file")MultipartFile file)
    {
//        System.out.println(relaPath);
        FileUploadResult fileUploadResult =  this.fileUploadService.upload(file);
        //上传出错
        if(fileUploadResult.getStatus().equals("error")){
            return Result.createByFailure("上传出错");
        }
        //上传结束
        else if(fileUploadResult.getStatus().equals("done"))
        {
            //上传成功
            if(fileUploadResult.getResponse().equals("success"))
            {
                return Result.createSuccessResult(fileUploadResult.getName());
            }
            //上传失败
            else
            {
                return Result.createByFailure("上传出错，联系后端");
            }
        }
//        else{
        return Result.createByFailure("未知错误，联系后端");
//        }

    }


}
