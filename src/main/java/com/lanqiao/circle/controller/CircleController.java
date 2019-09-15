package com.lanqiao.circle.controller;

import com.lanqiao.circle.annotations.UserLoginToken;
import com.lanqiao.circle.entity.Circles;
import com.lanqiao.circle.mapper.CirclesMapper;
import com.lanqiao.circle.service.CircleService;
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

/**
 * @Author 王昊
 * @Date 2019/9/12 7:21 下午
 */
@RequestMapping("/circle")
@RestController
public class CircleController {
    @Autowired
    CircleService circleService;
    @Autowired
    TokenService tokenService;
    @Autowired
    FileUploadUtil fileUploadUtil;
    @Autowired
    CirclesMapper circlesMapper;


    @PostMapping("/getAllCircle")
    public  Result getAllCircle(){
        return Result.createSuccessResult(circlesMapper.getAllCircle());
    }
    /**
     * 查看圈子基本信息
     * @param circleId
     * @return
     */
    @PostMapping("circleInfo")
    public Result circleInfo(@RequestParam(name = "circleId") int circleId){
        return circleService.circleInfo(circleId);
    }

    /**
     * 展示圈子微博
     * @param circleId
     * @param page
     * @param size
     * @return
     */
    @PostMapping("circleBlog")
    public Result circleBlog(@RequestParam(name = "circleId") int circleId,@RequestParam(name = "page") int page,
                             @RequestParam(name = "size") int size){
        return circleService.circleBlog(circleId,page,size);
    }

    /**
     * 创建圈子
     * @param httpServletRequest
     * @param file
     * @param circles
     * @return
     */
    @UserLoginToken
    @PostMapping("createCircle")
    public Result createCircle(HttpServletRequest httpServletRequest, @RequestParam(name = "file")MultipartFile file, Circles circles){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        circles.setUserId(userId);
        String strUrl = fileUploadUtil.fileUpload(file,5);
        String url = "//" + strUrl;
        circles.setCircleImgUrl(url);
        return circleService.createCircle(circles);
    }

}

