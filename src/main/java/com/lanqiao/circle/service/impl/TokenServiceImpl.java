package com.lanqiao.circle.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lanqiao.circle.entity.Users;
import com.lanqiao.circle.service.TokenService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 刘佳昇
 * @Date 2019/9/10 16:25
 */

@Service
public class TokenServiceImpl implements TokenService {
    /**
     * 根据用户Id和密码生成token
     * @param user
     * @return
     */
    @Override
    public String getToken(Users user) {
        String token = "";
        token = JWT.create().withAudience(""+user.getUserId()).sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }


    /**
     * 根据token获取userId
     * @param httpServletRequest
     * @return
     */
    @Override
    public String getUserId(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }
}
