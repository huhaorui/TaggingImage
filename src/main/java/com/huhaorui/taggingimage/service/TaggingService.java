package com.huhaorui.taggingimage.service;

import com.aliyun.imagerecog20190930.Client;
import com.aliyun.imagerecog20190930.models.*;
import com.aliyun.imagerecog20190930.models.DetectFruitsResponseBody.DetectFruitsResponseBodyDataElements;
import com.aliyun.imagerecog20190930.models.RecognizeFoodResponseBody.RecognizeFoodResponseBodyDataTopFives;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huhaorui.taggingimage.common.ApiResponse;
import com.huhaorui.taggingimage.common.Responses;
import com.huhaorui.taggingimage.util.TaggingClientProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aliyun.imagerecog20190930.models.TaggingImageResponseBody.TaggingImageResponseBodyDataTags;

@Service
public class TaggingService {
    TaggingClientProvider taggingClientProvider;

    @Autowired
    public void setClientProvider(TaggingClientProvider taggingClientProvider) {
        this.taggingClientProvider = taggingClientProvider;
    }

    public ApiResponse<List<TaggingImageResponseBodyDataTags>> processImage(String url) {
        Client client;
        try {
            client = taggingClientProvider.getClient();
        } catch (Exception e) {
            e.printStackTrace();
            return Responses.fail("error");
        }
        TaggingImageRequest taggingImageRequest = new TaggingImageRequest();
        taggingImageRequest.setImageURL(url);
        try {
            var response = client.taggingImage(taggingImageRequest);
            return Responses.ok(response.getBody().getData().getTags());
        } catch (Exception e) {
            e.printStackTrace();
            return Responses.fail("error");
        }
    }

    public ApiResponse<List<DetectFruitsResponseBodyDataElements>> taggingFruit(String url) {
        Client client;
        try {
            client = taggingClientProvider.getClient();
        } catch (Exception e) {
            e.printStackTrace();
            return Responses.fail("error");
        }
        var request = new DetectFruitsRequest();
        request.setImageURL(url);
        try {
            var response = client.detectFruits(request);
            var elements = response.getBody().getData().getElements();
            return Responses.ok("success", new Gson().fromJson(new Gson().toJson(elements), new TypeToken<List<DetectFruitsResponseBodyDataElements>>() {
            }.getType()));
        } catch (Exception e) {
            e.printStackTrace();
            return Responses.fail("error");
        }
    }

    public ApiResponse<List<RecognizeFoodResponseBodyDataTopFives>> taggingFood(String url) {
        Client client;
        try {
            client = taggingClientProvider.getClient();
        } catch (Exception e) {
            e.printStackTrace();
            return Responses.fail("error");
        }
        var request = new RecognizeFoodRequest();
        request.setImageURL(url);
        try {
            var response = client.recognizeFood(request);
            return Responses.ok(response.getBody().getData().getTopFives());
        } catch (Exception e) {
            e.printStackTrace();
            return Responses.fail("error");
        }
    }
}
