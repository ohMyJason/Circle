package com.lanqiao.circle.entity;

public class Blog {
    private Integer blogId;

    private Integer userId;

    private String createTime;

    private String content;

    private Integer isRepost;

    private Integer repostId;

    private Integer circleId;

    private Integer weight;

    private Integer isDelete;

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getIsRepost() {
        return isRepost;
    }

    public void setIsRepost(Integer isRepost) {
        this.isRepost = isRepost;
    }

    public Integer getRepostId() {
        return repostId;
    }

    public void setRepostId(Integer repostId) {
        this.repostId = repostId;
    }

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}