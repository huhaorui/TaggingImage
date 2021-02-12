package com.huhaorui.taggingimage.controller;

import com.aliyun.imagerecog20190930.models.TaggingImageResponseBody.TaggingImageResponseBodyDataTags;
import com.huhaorui.taggingimage.common.ApiResponse;
import com.huhaorui.taggingimage.common.Responses;
import com.huhaorui.taggingimage.service.OssService;
import com.huhaorui.taggingimage.service.TaggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@RestController
public class TaggingController {
    TaggingService taggingService;
    OssService ossService;

    @Autowired
    public void setImageService(TaggingService taggingService) {
        this.taggingService = taggingService;
    }

    @Autowired
    public void setOssService(OssService ossService) {
        this.ossService = ossService;
    }

    @PostMapping(value = "/tagging/upload")
    public ApiResponse<List<TaggingImageResponseBodyDataTags>> uploadAndTagging(@RequestParam("file") MultipartFile file) {
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
        return tagging(url.toString());
    }


    @PostMapping(value = "/tagging")
    public ApiResponse<List<TaggingImageResponseBodyDataTags>> tagging(String url) {
        return taggingService.processImage(url);
    }
}
