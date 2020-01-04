package com.lanqiao.circle.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.LoginService;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.util.CommentUtil;
import com.lanqiao.circle.util.MailUtil;
import com.lanqiao.circle.util.Result;
import com.lanqiao.circle.util.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author 刘佳昇
 * @Date 2019/9/10 15:15
 */

@RestController
@RequestMapping("/userLogin")
public class LoginController {


    @Autowired
    UsersMapper usersMapper;

    @Autowired
    TokenService tokenService;

    @Autowired
    SmsUtils smsUtils;

    @Autowired
    CommentUtil commentUtil;
    /**
     * 登录 可选择手机号登录，用户名登录，邮箱登录
     * @param loginName
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestParam(name = "loginName") String loginName, @RequestParam(name = "password") String password){
        try {
            Users user = new Users();
            user.setUserName(loginName);
            user.setEmail(loginName);
            user.setPhone(loginName);
            user.setPassword(commentUtil.getMd5(password));
            Users baseUser = usersMapper.selectUserByUserNameOrPhoneOrEmailAndPassword(user);
            if (baseUser!=null){
                String token = tokenService.getToken(baseUser);
                JSONObject res = new JSONObject();
                res.put("token",token);
                res.put("user",JSON.toJSON(baseUser));
                return Result.createSuccessResult(res);
            }else {
                return Result.createByFailure("登录名或密码错误，z");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.createByFailure("异常");
        }
    }

    /**
     * 注册接口
     * @param user
     * @return
     */
    @PostMapping("/registered")
    public  Result registered(Users user){
        try {
            user.setUserName(user.getPhone());
            String newPassword = commentUtil.getMd5(user.getPassword());
            user.setPassword(newPassword);
            String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            user.setCreateTime(timeStr1);
            user.setAvatarUrl("//47.98.46.243:8080/userImg/20190915122520_moren.png");
//            if (usersMapper.getUserByUserName(user.getUserName())==null){
            if (usersMapper.getUsersByPhone(user.getPhone())==null){
                usersMapper.insertSelective(user);
                String token = tokenService.getToken(user);
                return Result.createSuccessResult(token);
            }else {
                return Result.createByFailure("手机已经注册过了！");
            }
        }catch (Exception e ){
            e.printStackTrace();
            return Result.createByFailure(e.getMessage());
        }

    }



    //发送手机验证码
    @PostMapping("/sendMsg")
    public Result sendMsg(@RequestParam(name = "phoneNumber") String phoneNumber){
        int newCode = (int)(Math.random()*9999)+100;  //每次调用生成一次四位数的随机数
        String code = Integer.toString(newCode);
        try{
            SendSmsResponse sendSms =smsUtils.sendSms(phoneNumber,code);//填写你需要测试的手机号码
            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + sendSms.getCode());
            System.out.println("Message=" + sendSms.getMessage());
            System.out.println("RequestId=" + sendSms.getRequestId());
            System.out.println("BizId=" + sendSms.getBizId());
            System.out.println(code);
            return Result.createSuccessResult(code);
        }catch(Exception e){
            return Result.createByFailure();
        }
    }





    //邮箱发送验证码
    //发送邮件代码
    @PostMapping("/sendEmailCode")
    public Result sendAuthCodeEmail(@RequestParam("email") String email) {
        String code = MailUtil.sendEmail(email);
        return Result.createSuccessResult(code);

    }







}
