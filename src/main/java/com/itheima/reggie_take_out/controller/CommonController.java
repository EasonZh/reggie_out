package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 上传
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;
    @PostMapping("/upload")
    public R<String>upload(MultipartFile file){
        //原始文件名
        String filename = file.getOriginalFilename();//abc.jpg
        String suffix = filename.substring(filename.lastIndexOf("."));
        //使用UUID重新生成文件名，防止文件名重复造成文件覆盖
        String name = UUID.randomUUID().toString()+suffix;
        //创建文件目录对象
        File dir = new File(basePath);
        //判断文件是否存在
        if(!dir.exists()){
            //文件不存在，需要创建
            dir.mkdir();
        }

        try {
            file.transferTo(new File(basePath+name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(name);
    }
    /**
     * 下载
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        //通过输入流读取文件内容
        try {
            FileInputStream fileInputStream = new  FileInputStream(new File(basePath+name));
            //输出流，通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();
            //响应回去的格式
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while((len = fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            //关闭
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
