package com.lanqiao.circle.controller;

/**
 * @Author ohMyJasonq
 * @Date 2019/9/12 17:34
 */

import com.aliyun.oss.model.OSSObjectSummary;
import com.lanqiao.circle.service.impl.FileUploadService;
import com.lanqiao.circle.util.FileUploadResult;
import com.lanqiao.circle.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;

@Controller
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping("file/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile uploadFile)
            throws Exception {

        FileUploadResult  fileUploadResult =  this.fileUploadService.upload(uploadFile);
        //上传出错
        if(fileUploadResult.getStatus().equals("error")){
            return Result.createByFailure("上传出错");
        }
        //上传结束
        else if(fileUploadResult.getStatus().equals("done"))
        {
            //上传成功
            if(fileUploadResult.getResponse().equals("success"))
            {
                return Result.createSuccessResult(fileUploadResult.getName());
            }
            //上传失败
            else
            {
                return Result.createByFailure("上传出错，联系后端");
            }
        }
        else{
            return Result.createByFailure("未知错误，联系后端");
        }
    }

//    @RequestMapping("file/delete")
//    @ResponseBody
//    public FileUploadResult delete(@RequestParam("fileName") String objectName) throws Exception {
//        return this.fileUploadService.delete(objectName);
//    }

    @RequestMapping("file/list")
    @ResponseBody
    public List<OSSObjectSummary> list() throws Exception {
        return this.fileUploadService.list();
    }

//    @RequestMapping("file/download")
//    @ResponseBody
//    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {
//        //通知浏览器以附件形式下载
//        response.setHeader("Content-Disposition",
//                "attachment;filename=" + new String(objectName.getBytes(), "ISO-8859-1"));
//        this.fileUploadService.exportOssFile(response.getOutputStream(),objectName);
//    }
}
