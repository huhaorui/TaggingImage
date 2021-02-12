package com.huhaorui.taggingimage.service;

import com.aliyun.imagerecog20190930.models.TaggingImageRequest;
import com.huhaorui.taggingimage.util.ClientProvider;
import com.aliyun.imagerecog20190930.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaggingService {
    ClientProvider clientProvider;
    @Value("${com.huhaorui.taggingimage.path}")
    String path;

    @Autowired
    public void setClientProvider(ClientProvider clientProvider) {
        this.clientProvider = clientProvider;
    }

    public void processImage(String id) {
        Client client = clientProvider.getClient();
        TaggingImageRequest taggingImageRequest = new TaggingImageRequest();
        taggingImageRequest.setImageURL(path + id);
        try {
            client.taggingImage(taggingImageRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
