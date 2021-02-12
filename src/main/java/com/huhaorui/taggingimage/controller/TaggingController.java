package com.huhaorui.taggingimage.controller;

import com.aliyun.imagerecog20190930.models.TaggingImageResponseBody.TaggingImageResponseBodyDataTags;
import com.huhaorui.taggingimage.common.ApiResponse;
import com.huhaorui.taggingimage.service.TaggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaggingController {
    TaggingService taggingService;

    @Autowired
    public void setImageService(TaggingService taggingService) {
        this.taggingService = taggingService;
    }

    @PostMapping(value = "/tagging")
    public ApiResponse<List<TaggingImageResponseBodyDataTags>> tagging(String url) {
        return taggingService.processImage(url);
    }
}
