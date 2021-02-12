package com.huhaorui.taggingimage.service;

import com.aliyun.imagerecog20190930.models.TaggingImageRequest;
import com.huhaorui.taggingimage.util.TaggingClientProvider;
import com.aliyun.imagerecog20190930.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaggingService {
    TaggingClientProvider taggingClientProvider;
    @Value(value = "${com.huhaorui.taggingimage.path}")
    String path;

    @Autowired
    public void setClientProvider(TaggingClientProvider taggingClientProvider) {
        this.taggingClientProvider = taggingClientProvider;
    }

    public void processImage(String id) {
        Client client;
        try {
            client = taggingClientProvider.getClient();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        TaggingImageRequest taggingImageRequest = new TaggingImageRequest();
        taggingImageRequest.setImageURL(path + id);
        try {
            var response = client.taggingImage(taggingImageRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
