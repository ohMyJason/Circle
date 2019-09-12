package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.mapper.CirclesMapper;
import com.lanqiao.circle.service.CircleService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author 王昊
 * @Date 2019/9/12 7:24 下午
 */
@Service
public class CircleServiceImpl implements CircleService {
    @Autowired
    CirclesMapper circlesMapper;
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
}
