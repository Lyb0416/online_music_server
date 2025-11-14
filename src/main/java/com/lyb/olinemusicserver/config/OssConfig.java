package com.lyb.olinemusicserver.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置OssClient
 */
@Configuration
public class OssConfig {

    @Value("${aliyun.oss.endPoint}")
    private String ENDPOINT;
    @Value("${aliyun.oss.accessKeyId}")
    private String ACCESS_KEY_ID;
    @Value("${aliyun.oss.accessKeySecret}")
    private String ACCESS_KEY_SECRET;
    @Value("${aliyun.oss.bucketName}")
    private String BUCKET_NAME;

    //注入一个OSS实例
    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }
}
