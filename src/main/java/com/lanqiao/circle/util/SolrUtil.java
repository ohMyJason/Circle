package com.lanqiao.circle.util;

import com.lanqiao.circle.entity.Blog;
import com.lanqiao.circle.mapper.BlogMapper;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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
     *
     * @param blog
     * @return
     */
    public boolean saveBlog(Blog blog) {
        if (!blog.getBlogId().equals(blog.getId())) {
            throw new RuntimeException("id和blogid不符合");
        }
        UpdateResponse response = solrTemplate.saveBean("blogs", blog);
        solrTemplate.commit("blogs");
        return response.getStatus() == 0;
    }


    /**
     * 根据id删除
     *
     * @param ids
     * @return
     */
    public List delSolrBlogById(List<String> ids) {
        ArrayList<String> errorIds = new ArrayList<>();
        for (String id : ids) {
            UpdateResponse response = solrTemplate.deleteByIds("blogs", "" + id);
            solrTemplate.commit("blogs");
            if (!(response.getStatus() == 0)) {
                errorIds.add(id);

            }
        }
        return errorIds;
    }


    /**
     * 根据内容查询
     *
     * @param content
     * @return 所有的blogId
     */
    public List selectByContent(String content) {
        return selectByContent(content, null);
    }


    /**
     * 根据内容和圈子id查询
     *
     * @param content
     * @param circleId
     * @return
     */
    public List selectByContent(String content, String circleId) {
//查询所有
        Query query = new SimpleQuery();

        Criteria criteria;
        criteria = new Criteria("content").is(content);

        //设置条件
        query.addCriteria(criteria);
        //设置分页
        query.setOffset(1L);
        query.setRows(100000);
        //设置排序规则
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        query.addSort(sort);
        //blog容器
        ScoredPage<Blog> pages = solrTemplate.queryForPage("blogs", query, Blog.class);
        List<Blog> resBlogs = pages.getContent();
        //加入circle不为null，过滤circleid
        if (circleId!=null){
            resBlogs = filterCircle(resBlogs,circleId);
        }
        //存储blogid容器
        List<Integer> blogIds = new ArrayList<>();
        //假如solr没有，则往里存
        if (resBlogs == null || resBlogs.size() == 0) {
            System.out.println("log:--------------solr里面没有啊----------------");
            List<Blog> blogByContent = blogMapper.getBlogByContent(content);
            //过滤CIrcle
            if (circleId!=null){
                blogByContent = filterCircle(blogByContent,circleId);
            }
            for (Blog blog : blogByContent) {
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


    private List<Blog> filterCircle(List<Blog> blogs, String circleId){
        List<Blog> res = new ArrayList<>();
        for(Blog blog:blogs){
            if ((blog.getCircleId().equals(Integer.parseInt(circleId)))){
                res.add(blog);
            }
        }
        return res;
    }


}
