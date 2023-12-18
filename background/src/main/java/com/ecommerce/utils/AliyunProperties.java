package com.ecommerce.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun")
public class AliyunProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private OssProperties oss;
    private EmailProperties email;
}

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
class OssProperties {
    private String endpoint;
    private String bucketName;
}

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.email")
class EmailProperties {
    private String endpoint; // 邮件服务的URL
    private String accountName; // 发信地址
    private String subject; // 邮件主题
    private String fromAlias; // 发信人昵称
}
