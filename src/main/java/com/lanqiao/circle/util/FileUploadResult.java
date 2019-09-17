package com.lanqiao.circle.util;
import lombok.Data;

/**
 * @Author 钱琦瑛
 * @Date 2019/9/12 17:34
 */
@Data
public class FileUploadResult {
    // 文件唯一标识
    private String uid;

    // 文件名
    private String name;

    // 状态有：uploading done error removed
    private String status;

    // 服务端响应内容，如：'{"status": "success"}'
    private String response;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
