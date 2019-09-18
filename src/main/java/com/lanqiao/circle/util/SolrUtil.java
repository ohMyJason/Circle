package com.lanqiao.circle.util;

import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.mapper.BlogMapper;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author 刘佳昇
 * @Date 2019/9/18 16:44
 */

@Component
public class SolrUtil {

    @Autowired
    SolrTemplate solrTemplate;

    @Autowired
    BlogMapper blogMapper;

    /**
     * 向solr里插数据
     * @param blog
     * @return
     */
    public boolean saveBlog(Blog blog){
        if (!blog.getBlogId().equals(blog.getId())){
            throw new RuntimeException("id和blogid不符合");
        }
        UpdateResponse response = solrTemplate.saveBean("blogs", blog);
        solrTemplate.commit("blogs");
        return response.getStatus() == 0;
    }


    /**
     * 根据id删除
     * @param id
     * @return
     */
    public boolean delSolrBlogById(String id){
        UpdateResponse response = solrTemplate.deleteByIds("blogs", ""+id);
        solrTemplate.commit("blog");
        return response.getStatus()==0;
    }




    /**
     * 根据内容查询
     * @param content
     * @return 所有的blogId
     */
    public  List selectByContent(String content){
        //查询所有
        Query query = new SimpleQuery();
        //设置条件
        Criteria criteria = new Criteria("content").is(content);
        //设置分页
        query.addCriteria(criteria);
        query.setOffset(1L);
        query.setRows(100000);
        //设置排序规则
        Sort sort = new Sort(Sort.Direction.ASC,"createTime");
        query.addSort(sort);
        //blog容器
        ScoredPage<Blog> pages = solrTemplate.queryForPage("blogs", query,Blog.class);
        List<Blog> resBlogs = pages.getContent();
        //存储blogid容器
        List<Integer> blogIds=new ArrayList<>();
        //假如solr没有，则往里存
        if (resBlogs==null||resBlogs.size()==0){
            System.out.println("log:--------------solr里面没有啊----------------");
            List<Blog> blogByContent = blogMapper.getBlogByContent(content);
            for (Blog blog:blogByContent){
                saveBlog(blog);
                blogIds.add(blog.getBlogId());
            }
            //倒序，最新的动态展示在最前方
            Collections.reverse(blogIds);
            return blogIds;
        }
        System.out.println("log:---------------solr里有啊--------------");

        //solr里有
        for (Blog next : resBlogs) {
            blogIds.add(next.getBlogId());
        }
        Collections.reverse(blogIds);
        return blogIds;
    }



}
