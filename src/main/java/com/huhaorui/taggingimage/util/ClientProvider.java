package com.huhaorui.taggingimage.util;

import com.aliyun.imagerecog20190930.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientProvider {
    @Value("${com.huhaorui.taggingimage.accesskeyid}")
    String accessKeyId;
    @Value("${com.huhaorui.taggingimage.accesskeysecret}")
    String accessKeySecret;
    private Client client = null;

    public Client getClient() {
        if (this.client == null) {
            return this.createClient();
        } else {
            return client;
        }
    }

    private Client createClient() {
        Config config = new Config();
        config.accessKeyId = accessKeyId;
        config.accessKeySecret = accessKeySecret;
        config.endpoint = "imagerecog.cn-shanghai.aliyuncs.com";
        try {
            this.client = new Client(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.client;
    }
}
