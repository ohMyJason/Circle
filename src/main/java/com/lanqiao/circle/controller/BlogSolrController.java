package com.lanqiao.circle.controller;

import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.mapper.BlogMapper;
import com.lanqiao.circle.util.Result;
import com.lanqiao.circle.util.SolrUtil;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author 刘佳昇
 * @Date 2019/9/18 15:52
 */
@RestController
@RequestMapping("/solr")
public class BlogSolrController {

    @Autowired
    SolrTemplate solrTemplate;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    SolrUtil solrUtil;

    //---------------------手动封装solr函数工具-------------------------------

    @PostMapping("/testSetId")
    public Result testSetId(){
        List<Blog> allBaseBlog = blogMapper.getAllBaseBlog();
        return Result.createSuccessResult(allBaseBlog);

    }

    @PostMapping("/saveBlog")
    public Result save(Blog blog){
        return Result.createSuccessResult(solrUtil.saveBlog(blog));
    }

    @PostMapping("/deleteById")
    public Result deleteById(String id){
        return Result.createSuccessResult(solrUtil.delSolrBlogById(id));
    }

    @PostMapping("/getSolrBlogById")
    public Result getSolrBlogById(String id){
        Optional<Blog> optionalBlog = solrTemplate.getById("blogs",id,Blog.class);
        if (optionalBlog.isPresent()){
            Blog blog = optionalBlog.get();
            return Result.createSuccessResult(blog);
        }
        return Result.createSuccessResult();
    }


    @PostMapping("/saveAll")
    public Result saveAll(){
        try {

            List<Blog> allBaseBlog = blogMapper.getAllBaseBlog();
            for (Blog blog:allBaseBlog){
                solrUtil.saveBlog(blog);
            }
            return Result.createSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return Result.createByFailure(e.getMessage());
        }
    }

//    @PostMapping("delById")
//    public  Result delById(Integer blogId){
//    }

    //手动封装solr函数工具----------------------------------------------------------------结束

    @PostMapping("/testSearch")
    public Result testSearch(String content){
        List list = solrUtil.selectByContent(content);
        return Result.createSuccessResult(list.size(),list);
    }


}
