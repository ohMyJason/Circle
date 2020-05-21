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
                //若存在条目返回1，表示取消点赞
                likeMapper.deleteByPrimaryKey(like1);
                Blog blog = blogMapper.selectByPrimaryKey(like1.getBlogId());
                blog.setWeight(blog.getWeight() - 1);
                blogMapper.updateByPrimaryKeySelective(blog);
                return Result.createSuccessResult(1);
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
            e.printStackTrace();
            return Result.createByFailure(e.getMessage());
        }
    }
}
