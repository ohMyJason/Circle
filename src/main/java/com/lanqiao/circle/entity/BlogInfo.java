package com.lanqiao.circle.entity;

import java.util.List;

/**
 * @Author 王昊
 * @Date 2019/9/10 11:48 下午
 */
public class BlogInfo {
    private Integer blogId;

    private Integer userId;

    private String userName;

    private String avatarUrl;

    private String createTime;

    private String content;

    private Integer likeNum = 0;

    private Integer commentNum = 0;

    private Integer isRepost;

    private Integer repostId;

    private Integer circleId;

    private String circleName;

    private Integer type;

    private Integer weight;

    private List<String> resourceList;

    private BlogInfo blogInfo;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
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

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<String> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<String> resourceList) {
        this.resourceList = resourceList;
    }

    public BlogInfo getBlogInfo() {
        return blogInfo;
    }

    @Override
    public String toString() {
        return "BlogInfo{" +
                "blogId=" + blogId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", createTime='" + createTime + '\'' +
                ", content='" + content + '\'' +
                ", likeNum=" + likeNum +
                ", commentNum=" + commentNum +
                ", isRepost=" + isRepost +
                ", repostId=" + repostId +
                ", circleId=" + circleId +
                ", circleName='" + circleName + '\'' +
                ", type=" + type +
                ", weight=" + weight +
                ", resourceList=" + resourceList +
                ", blogInfo=" + blogInfo +
                '}';
    }

    public void setBlogInfo(BlogInfo blogInfo) {
        this.blogInfo = blogInfo;
    }
}
