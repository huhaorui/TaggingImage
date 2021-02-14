package com.huhaorui.taggingimage.controller;

import com.aliyun.imagerecog20190930.models.DetectFruitsResponseBody.DetectFruitsResponseBodyDataElements;
import com.aliyun.imagerecog20190930.models.RecognizeFoodResponseBody.RecognizeFoodResponseBodyDataTopFives;
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
    public ApiResponse<?> uploadAndTagging(@RequestParam("file") MultipartFile file, String option) {
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
        switch (option) {
            case "fruit":
                return taggingFruit(url.toString());
            case "food":
                return taggingFood(url.toString());
            default:
                return taggingAll(url.toString());
        }
    }


    @PostMapping(value = "/tagging/all")
    public ApiResponse<List<TaggingImageResponseBodyDataTags>> taggingAll(String url) {
        return taggingService.processImage(url);
    }

    @PostMapping(value = "/tagging/fruit")
    public ApiResponse<List<DetectFruitsResponseBodyDataElements>> taggingFruit(String url) {
        return taggingService.taggingFruit(url);
    }

    @PostMapping(value = "/tagging/food")
    public ApiResponse<List<RecognizeFoodResponseBodyDataTopFives>> taggingFood(String url) {
        return taggingService.taggingFood(url);
    }
}
