package com.huhaorui.taggingimage.controller;


import com.huhaorui.taggingimage.common.ApiResponse;
import com.huhaorui.taggingimage.common.Responses;
import com.huhaorui.taggingimage.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;


@RestController(value = "/image")
public class ImageController {
    OssService ossService;

    @Autowired
    public void setOssService(OssService ossService) {
        this.ossService = ossService;
    }


    @PostMapping(value = "image/put")
    public ApiResponse<Object> putImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Responses.fail("文件为空");
        }
        URL url;
        try {
            url = ossService.uploadFile(UUID.randomUUID().toString(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return Responses.fail("file save error");
        }
        return Responses.ok(url);
    }
}


