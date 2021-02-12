package com.huhaorui.taggingimage.service;

import com.aliyun.imagerecog20190930.Client;
import com.aliyun.imagerecog20190930.models.TaggingImageRequest;
import com.huhaorui.taggingimage.common.ApiResponse;
import com.huhaorui.taggingimage.common.Responses;
import com.huhaorui.taggingimage.util.TaggingClientProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aliyun.imagerecog20190930.models.TaggingImageResponseBody.TaggingImageResponseBodyDataTags;

@Service
public class TaggingService {
    TaggingClientProvider taggingClientProvider;
    @Value(value = "${com.huhaorui.taggingimage.path}")
    String path;

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
}
