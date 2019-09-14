package com.lanqiao.circle.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 刘佳昇
 * @Date 2019/9/14 14:17
 */
@Component
public class CommentUtil {


    /**
     * MD5加密
     * @param str
     * @return
     */
    public String getMd5(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1,md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "-1";
        }
    }


    /**
     * 获取当前时间
     * @return
     */
    public String getCurTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
