package com.lanqiao.circle.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 刘佳昇
 * @Date 2019/8/8 15:29
 */

@Component
public class FileUtil {
    //    图片路径
    @Value("${img.path}")
    public String imgPath;

    @Value("${pdf.path}")
    public String pdfPath;

    @Value("${mp3.path}")
    public String mp3Path;

    @Value("${chatImg.path}")
    public String chatImgPath;

    @Value("${userImg.path}")
    public String userImgPath;

    /**
     * 文件上传
     *
     * @param file
     * @return flag 1-pdf 2-img 3-mp3 4-chatImg 5-userImg
     */
    public String fileUpload(MultipartFile file, Integer flag) {
        //判断文件是否为空
        if (file.isEmpty()) {
            return "-1";
        }

        String fileName = file.getOriginalFilename();

        String prePath = "";


        switch (flag) {
            case 1:
//                pdf
                prePath = this.pdfPath;
                break;
            case 2:
//                img
                prePath = this.imgPath;
                break;
            case 3:
//                mp3
                prePath = this.mp3Path;
                break;
            case 4:
//                chatImg
                prePath = this.chatImgPath;
                break;
            case 5:
//                uerImg
                prePath = this.userImgPath;
                break;
        }
        //加个时间戳，尽量避免文件名称重复
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        //用来当做返回的url，必须和resourceConfig中的配置一致
        String path = prePath + newFileName;
        File dest = new File(path);

        //判断文件是否已经存在
        if (dest.exists()) {
            return "-2";
        }

        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }

        try {
            file.transferTo(dest); //保存文件
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "-3";
        }

        if (flag == 1) {

            return "/file/pdf/" + newFileName;
        } else if (flag == 2) {
            return "/file/img/" + newFileName;
        } else if (flag==3){
            return "/file/mp3/" + newFileName;
        }else if (flag==4){
            return "/file/chatImg/" +newFileName;
        }else if (flag==5){
            return "/file/userImg/"+newFileName;
        }else{
            throw new RuntimeException("上传flag出现异常");
        }

    }

    /**
     * 文件下载
     *
     * @param response
     * @param filePathName
     * @return
     */
    public String downloadFile(HttpServletResponse response, String filePathName) {
        File file = new File(filePathName);
        if (!file.exists()) {
            return "-1";
        }

        response.reset();
        response.setHeader("Content-Disposition", "attachment;fileName=" + filePathName);

//        jdk7新写法，由于实现了autucloseable接口，定义在try中的流会自动关闭
        try (OutputStream os = response.getOutputStream()) {
            InputStream inStream = new FileInputStream(filePathName);
//            OutputStream os = response.getOutputStream();

            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buff)) > 0) {
                os.write(buff, 0, len);
            }

//            os.flush();
//            os.close();
            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "-2";
        }

        return "0";
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public String getCurrTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }


    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public String deleteFile(String fileName) {
        if (fileName == null) {
            return "-3";
        }

        File file = new File(fileName);
        if (!file.exists()) {
            return "-2";
        }
        if (file.delete()) {
            return "0";
        } else {
            return "-1";
        }

    }

    /**
     * 访问文件名
     * @param relaUrl 文件虚拟路径名
     * @return
     */
    public String getFileName(String relaUrl){
        String[] split = relaUrl.split("/");
        return split[split.length-1];
    }

}
