package com.lanqiao.circle.config;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * @author 刘佳昇
 * @Date 2019/9/18 15:46
 */

@Configuration
public class SolrConfig {
    @Autowired
    SolrClient solrClient;


    @Bean
    public SolrTemplate getSolrTemplate(){
        return new SolrTemplate(solrClient);
    }
}
