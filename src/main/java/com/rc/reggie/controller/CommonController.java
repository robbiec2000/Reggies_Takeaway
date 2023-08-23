package com.rc.reggie.controller;

import com.rc.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){

        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf('.'));

        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        String newFileName =  UUID.randomUUID().toString() + suffix;
        try{
            file.transferTo(new File(basePath + newFileName));
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.success(newFileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try{
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            ServletOutputStream servletOutputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while((len = fileInputStream.read(bytes)) != -1){
                servletOutputStream.write(bytes, 0, len);
                servletOutputStream.flush();
            }

            servletOutputStream.close();
            fileInputStream.close();

        }catch(Exception e){
            //e.printStackTrace();
        }

    }
}
