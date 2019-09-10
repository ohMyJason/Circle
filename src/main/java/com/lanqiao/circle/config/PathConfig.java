package com.lanqiao.circle.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 刘佳昇
 * @Date 2019/9/10 11:10
 */
@Configuration
@PropertySource("classpath:PathConfig.properties")
public class PathConfig {

    @Value("${img.path}")
    String imgPath;
    @Value("${pdf.path}")
    String pdfPath;
    @Value("${mp3.path}")
    String mp3Path;
    @Value("${chatImg.path}")
    String  chatImgPath;


    public String getChatImgPath() {
        return chatImgPath;
    }

    public void setChatImgPath(String chatImgPath) {
        this.chatImgPath = chatImgPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getMp3Path() {
        return mp3Path;
    }

    public void setMp3Path(String mp3Path) {
        this.mp3Path = mp3Path;
    }
}
