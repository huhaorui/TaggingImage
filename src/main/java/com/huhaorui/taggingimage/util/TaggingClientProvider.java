package com.huhaorui.taggingimage.util;

import com.aliyun.imagerecog20190930.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaggingClientProvider {
    @Value("${com.huhaorui.taggingimage.accesskeyid}")
    String accessKeyId;
    @Value("${com.huhaorui.taggingimage.accesskeysecret}")
    String accessKeySecret;


    public Client getClient() throws Exception {
        Config config = new Config();
        config.accessKeyId = accessKeyId;
        config.accessKeySecret = accessKeySecret;
        config.endpoint = "imagerecog.cn-shanghai.aliyuncs.com";
        return new Client(config);
    }
}
