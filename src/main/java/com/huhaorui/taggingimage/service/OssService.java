package com.huhaorui.taggingimage.service;

import com.aliyun.oss.OSS;
import com.huhaorui.taggingimage.util.OssClientProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class OssService {
    OssClientProvider ossClientProvider;
    @Value(value = "${com.huhaorui.taggingimage.oss.bucket}")
    String bucketName;

    @Autowired
    public void setOssClientProvider(OssClientProvider ossClientProvider) {
        this.ossClientProvider = ossClientProvider;
    }

    public void uploadFile(String filename, InputStream inputStream) {
        OSS oss = ossClientProvider.getClient();
        var response = oss.putObject(bucketName, filename, inputStream);

    }
}
