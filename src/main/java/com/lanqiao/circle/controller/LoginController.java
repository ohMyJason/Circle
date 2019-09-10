package com.lanqiao.circle.controller;

import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.LoginService;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.util.Result;
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

    @PostMapping("/registered")
    public  Result registered(){
        return null;
    }

}
