package com.lanqiao.circle.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.LoginService;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.util.Result;
import com.lanqiao.circle.util.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
            user.setPassword(password);
            Users baseUser = usersMapper.selectUserByUserNameOrPhoneOrEmailAndPassword(user);
            if (baseUser!=null){
                String token = tokenService.getToken(baseUser);
                HashMap<String, String> res = new HashMap<>();
                res.put("token",token);
                return Result.createSuccessResult(res);
            }else {
                return Result.createByFailure("登录名或密码错误，没有此用户，登录失败");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.createByFailure("异常");
        }
    }

    /**
     * @param :phoneNumber （ 手机号码，需要真实有效）
     * @return:无
     */
    @PostMapping("/registered")
    public  Result registered(){
        return null;
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
            return Result.createSuccessResult(code);
        }catch(Exception e){
            return Result.createByFailure();
        }
    }

}
