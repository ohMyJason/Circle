package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.BlogInfo;
import com.lanqiao.circle.entity.Circles;
import com.lanqiao.circle.mapper.CirclesMapper;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.CircleService;
import com.lanqiao.circle.util.PageCheck;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * @Author 王昊
 * @Date 2019/9/12 7:24 下午
 */
@Service
public class CircleServiceImpl implements CircleService {
    @Autowired
    CirclesMapper circlesMapper;
    @Autowired
    UsersMapper usersMapper;
    @Override
    public Result circleInfo(int circleId) {
        try {
            HashMap hashMap = circlesMapper.getCircleInfo(circleId);
            if(hashMap != null){
                return Result.createSuccessResult(hashMap);
            }else {
                return Result.createByFailure("无此信息！");
            }
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }

    @Override
    public Result circleBlog(int circleId, int page, int size) {
        try{
            int pageIndex = (page-1) * size;
            List<BlogInfo> blogInfoList = circlesMapper.getCircleBlogDESCByWeight(circleId,pageIndex,size);
            for (BlogInfo blogInfo: blogInfoList) {
                List<String> resoureList = usersMapper.getAllResource(blogInfo.getBlogId());
                blogInfo.setResourceList(resoureList);
                if(blogInfo.getIsRepost() != 0) {
                    BlogInfo blogInfo1 = usersMapper.getRepostBlog(blogInfo.getRepostId());
                    List<String> resourceList1 = usersMapper.getAllResource(blogInfo1.getBlogId());
                    blogInfo1.setResourceList(resourceList1);
                    blogInfo.setBlogInfo(blogInfo1);
                }
            }
            return Result.createSuccessResult(blogInfoList.size(),blogInfoList);
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }

    @Override
    public Result createCircle(Circles circles) {
        try{
            String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            circles.setCreateTime(timeStr1);
            if(circlesMapper.insertSelective(circles) > 0){
                return Result.createSuccessResult();
            }else {
                return Result.createByFailure("插入失败！");
            }
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");

        }
    }

    @Override
    public Result showAllCircle() {
        try{
            List<HashMap> hashMapList = circlesMapper.getAllCirclesInfo();
            for (HashMap hashMap:hashMapList) {
                List<HashMap> hashMaps1 = circlesMapper.getCircleUserNum((Integer)hashMap.get("circleId"));
                hashMap.put("userNum",hashMaps1.size());
                List<HashMap> hashMaps2 = circlesMapper.getCircleBlogNum((Integer)hashMap.get("circleId"));
                hashMap.put("blogNum",hashMaps2.size());
            }
            return Result.createSuccessResult(hashMapList);
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }


    @Override
    public Result normalCircles(String circleName,int page,int limit){
        try {
            page = PageCheck.checkPage(page);
            limit = PageCheck.checkLimit(limit);
            int start = PageCheck.calculateStart(page,limit);
            int count = circlesMapper.getCirclesCount(circleName);
            List<Circles> circlesList = circlesMapper.normalCircles(start, limit, circleName);
            if (count>0){
                return Result.createSuccessResult(count,circlesList);
            }else {
                return Result.createByFailure("无数据");
            }
        }catch (Exception e ){
            System.out.println(e.getCause());
            return Result.createByFailure("异常");
        }
    }
    @Override
    public Result deleteCircles(Integer circleId){
        try{
            if (circlesMapper.deleteCircles(circleId)>0){
                return Result.createSuccessResult();
            }else {
                return Result.createByFailure("ERROR");
            }
        }catch (Exception e){
            System.out.println(e.getCause());
            return Result.createByFailure("异常");
        }
    }
    @Override
    public Result findCircles(String circleName){
        try {
            List<Circles> circlesList = circlesMapper.findCircles(circleName);
            return Result.createSuccessResult();
        }catch (Exception e){
            System.out.println(e.getCause());
            return Result.createByFailure("异常");
        }
    }
}
