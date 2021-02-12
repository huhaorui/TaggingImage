package com.huhaorui.taggingimage.controller;

import com.huhaorui.taggingimage.common.ApiResponse;
import com.huhaorui.taggingimage.common.Responses;
import com.huhaorui.taggingimage.service.TaggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaggingController {
    TaggingService taggingService;

    @Autowired
    public void setImageService(TaggingService taggingService) {
        this.taggingService = taggingService;
    }

    @PostMapping(value = "/tagging/{id}")
    public ApiResponse<Object> tagging(@PathVariable String id) {
        taggingService.processImage(id);
        return Responses.ok();
    }
}
