package com.huhaorui.taggingimage.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OssClientProvider {
    @Value("${com.huhaorui.taggingimage.oss.path}")
    String endpoint;
    @Value("${com.huhaorui.taggingimage.accesskeyid}")
    String accessKeyId;
    @Value("${com.huhaorui.taggingimage.accesskeysecret}")
    String accessKeySecret;

    public OSS getClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
