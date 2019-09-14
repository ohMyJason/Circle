package com.lanqiao.circle.util;

import com.lanqiao.circle.config.TempPathConfig;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 刘佳昇
 * @Date 2019/9/12 20:00
 */
@Component
public class FileUploadUtil {
    @Autowired
    TempPathConfig tempPathConfig;

    /**
     * 文件上传
     * @param file
     * @param flag  1-blogImg 2-chatImg 3-userImg 4-vedio 5-circle,img
     * @return
     */
    public String fileUpload(@RequestParam(name = "file") MultipartFile file, Integer flag){
        try {
            OkHttpClient client = new OkHttpClient();

            /**
             * 上传文件格式
             */
            //存临时文件
            File f = new File(tempPathConfig.getTempPath()+file.getOriginalFilename());
            file.transferTo(f);
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), f);//将file转换成RequestBody文件
            RequestBody requestBody=new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file",f.getName(),fileBody)
                    .addFormDataPart("flag",""+flag)
                    .build();
            Request request = new Request.Builder()
                    .url("http://47.98.46.243:8080/uploadFile")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String url = Objects.requireNonNull(response.body()).string();

            if (f.delete()){
                return url;
            }else {
                throw new RuntimeException("临时文件删除失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "-2";
        }
    }


}
