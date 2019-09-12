package com.lanqiao.circle.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author 刘佳昇
 * @Date 2019/9/12 19:57
 */
@Configuration
@PropertySource("classpath:temp.properties")
public class TempPathConfig {

    @Value("${temp.path}")
    String tempPath;

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }
}
