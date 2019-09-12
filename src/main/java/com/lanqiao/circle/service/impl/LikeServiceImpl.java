package com.lanqiao.circle.service.impl;

import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.entity.Like;
import com.lanqiao.circle.entity.LikeKey;
import com.lanqiao.circle.mapper.BlogMapper;
import com.lanqiao.circle.mapper.LikeMapper;
import com.lanqiao.circle.service.LikeService;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author 王昊
 * @Date 2019/9/11 4:20 下午
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    LikeMapper likeMapper;

    @Transactional
    @Override
    public Result insertLike(LikeKey likeKey) {
        try{
            Like like1 = likeMapper.selectByPrimaryKey(likeKey);
            if(like1 != null){
                return Result.createByFailure("你已经点过赞了！");
            }else {
                Like like = new Like();
                like.setUserId(likeKey.getUserId());
                like.setBlogId(likeKey.getBlogId());
                if (likeMapper.insertSelective(like) > 0){
                    Blog blog = blogMapper.selectByPrimaryKey(like.getBlogId());
                    blog.setWeight(blog.getWeight() + 1);
                    blogMapper.updateByPrimaryKeySelective(blog);
                    return Result.createSuccessResult();
                }else {
                    return Result.createByFailure("点赞失败！");
                }
            }
        }catch (Exception e){
            return Result.createByFailure("操作异常，请联系管理人员！");
        }
    }
}
