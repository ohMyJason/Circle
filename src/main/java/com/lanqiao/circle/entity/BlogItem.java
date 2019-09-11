package com.lanqiao.circle.entity;

public class BlogItem {
    private Integer blogItemId;

    private String resourceUrl;

    private Integer type;

    private Integer blogId;

    private Integer isDelete;

    public Integer getBlogItemId() {
        return blogItemId;
    }

    public void setBlogItemId(Integer blogItemId) {
        this.blogItemId = blogItemId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl == null ? null : resourceUrl.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}