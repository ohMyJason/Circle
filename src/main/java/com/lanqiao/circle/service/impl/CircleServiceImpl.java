package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.BlogInfo;
import com.lanqiao.circle.mapper.CirclesMapper;
import com.lanqiao.circle.mapper.UsersMapper;
import com.lanqiao.circle.service.CircleService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
