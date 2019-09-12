package com.lanqiao.circle.controller;

import com.alibaba.fastjson.JSONObject;
import com.lanqiao.circle.config.TempPathConfig;
import com.lanqiao.circle.util.FileUploadUtil;
import com.lanqiao.circle.util.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author 刘佳昇
 * @Date 2019/9/12 17:22
 */
@RestController
public class FileController {
    @Autowired
    FileUploadUtil fileUploadUtil;

    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestParam(name = "file") MultipartFile file, Integer flag) {
        try{
            String url = fileUploadUtil.fileUpload(file, flag);
            if (!url.equals("-2")){
                JSONObject res = new JSONObject();

                res.put("url",url);
                return Result.createSuccessResult(res);
            }else {
                return Result.createByFailure("异常");
            }
        } catch (Exception e ){
            e.printStackTrace();
            return Result.createByFailure(e.getMessage());
        }
    }


}
