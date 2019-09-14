package com.lanqiao.circle.entity;

import java.io.Serializable;

public class Circles implements Serializable {
    private Integer circleId;

    private Integer userId;

    private String circleName;

    private String circleTitle;

    private String createTime;

    private String circleImgUrl;

    private Integer isDelete;

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName == null ? null : circleName.trim();
    }

    public String getCircleTitle() {
        return circleTitle;
    }

    public void setCircleTitle(String circleTitle) {
        this.circleTitle = circleTitle == null ? null : circleTitle.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCircleImgUrl() {
        return circleImgUrl;
    }

    public void setCircleImgUrl(String circleImgUrl) {
        this.circleImgUrl = circleImgUrl == null ? null : circleImgUrl.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}