package com.lanqiao.circle.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lanqiao.circle.annotations.UserLoginToken;
import com.lanqiao.circle.entity.Circles;
import com.lanqiao.circle.mapper.CirclesMapper;
import com.lanqiao.circle.service.CircleService;
import com.lanqiao.circle.service.TokenService;
import com.lanqiao.circle.util.FileUploadUtil;
import com.lanqiao.circle.util.RedisUtil;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Author ohMyJasonw
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
    @Autowired
    RedisUtil redisUtil;
    @PostMapping("/getAllCircle")
    public  Result getAllCircle(){
        List<Circles> circlesList = circlesMapper.getAllCircle();
        return Result.createSuccessResult(circlesList.size(),circlesList);
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
    /**
     * 手机端创建圈子
     * @param httpServletRequest
     * @param circles
     * @return
     */
    @UserLoginToken
    @PostMapping("/mobelCreateCircle")
    public Result mobelCreateCircle(HttpServletRequest httpServletRequest, String circleImgUrl, Circles circles){
        int userId = Integer.parseInt(tokenService.getUserId(httpServletRequest));
        circles.setUserId(userId);
        String url = circleImgUrl;
        circles.setCircleImgUrl(url);
        return circleService.createCircle(circles);
    }

    /**
     * 展示所有圈子
     * @return
     */
    @PostMapping("showAllCircle")
    public Result showAllCircle(){
        return circleService.showAllCircle();
    }

    @PostMapping("searchCircleLike")
    public Result searchCircleLike(@RequestParam(name = "name")String name){
        return circleService.showCircleLike(name);
    }
    /**
     * 管理员查询全部圈子
     */
    @PostMapping("/normalCircles")
    public Result normalCircles(String circleName,int page,int limit){
        return circleService.normalCircles(circleName,page,limit);
    }

    /**
     * 管理员删除圈子
     */

    @PostMapping("/deleteCircles")
    public Result deleteCircles(Integer circleId){
        return circleService.deleteCircles(circleId);
    }

    /**
     * 管理员查询圈子
     */
    @PostMapping("/findCircles")
    public Result findCircles(String circleName){
        return circleService.findCircles(circleName);
    }


    @PostMapping("/hotCircle")
    public Result hotCIrcle(){
        try {
            Set<ZSetOperations.TypedTuple<Object>> zsetRange = redisUtil.getZsetRange("circle-blog-num");
            Iterator<ZSetOperations.TypedTuple<Object>> iterator = zsetRange.iterator();
            JSONArray datas = new JSONArray();
            while (iterator.hasNext()){
                ZSetOperations.TypedTuple<Object> next = iterator.next();
                JSONObject data = new JSONObject();
                data.put("circleName",next.getValue());
                data.put("circleScore",next.getScore());
                data.put("circleImg",circlesMapper.getCircleByName((String) next.getValue()).getCircleImgUrl());
                data.put("circleId",circlesMapper.getCircleByName((String) next.getValue()).getCircleId());
                datas.add(data);
            }
            return Result.createSuccessResult(datas.size(),datas);
        }catch (Exception e){
            e.printStackTrace();
            return Result.createByFailure(e.getMessage());
        }
    }
}


