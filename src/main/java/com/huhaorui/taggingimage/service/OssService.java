package com.huhaorui.taggingimage.service;

import com.aliyun.oss.OSS;
import com.huhaorui.taggingimage.util.OssClientProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@Service
public class OssService {
    OssClientProvider ossClientProvider;
    @Value(value = "${com.huhaorui.taggingimage.oss.bucket}")
    String bucketName;

    @Autowired
    public void setOssClientProvider(OssClientProvider ossClientProvider) {
        this.ossClientProvider = ossClientProvider;
    }

    public URL uploadFile(String filename, InputStream inputStream) {
        OSS oss = ossClientProvider.getClient();
        oss.putObject(bucketName, filename, inputStream);
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        URL url = oss.generatePresignedUrl(bucketName, filename, expiration);
        oss.shutdown();
        return url;
    }
}
