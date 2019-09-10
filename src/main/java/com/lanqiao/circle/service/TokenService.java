package com.lanqiao.circle.service;



import com.lanqiao.circle.entity.Users;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 刘佳昇
 * @Date 2019/8/14 15:23
 */

public interface TokenService {
    String getToken(Users user);
    String getUserId(HttpServletRequest httpServletRequest);
}
