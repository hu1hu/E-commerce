package com.ecommerce.utils;

import com.aliyun.dm20151123.Client;
import com.aliyun.dm20151123.models.SingleSendMailRequest;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

@Component
@Slf4j
public class AliEmailUtils {

    @Autowired
    private AliyunProperties aliyunProperties;

    public void send(String text,String toAddress) throws Exception {
        log.info("发送邮件，toAddress：{}",toAddress);
        // 创建Client实例并初始化
        Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(aliyunProperties.getAccessKeyId())
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(aliyunProperties.getAccessKeySecret());
        // Endpoint 请参考 https://api.aliyun.com/product/Dm
        config.endpoint = aliyunProperties.getEmail().getEndpoint();
        Client client = new Client(config);

        SingleSendMailRequest singleSendMailRequest = new SingleSendMailRequest()
                .setAccountName(aliyunProperties.getEmail().getAccountName())
                .setAddressType(1)
                .setReplyToAddress(false)
                .setToAddress(toAddress)
                .setSubject(aliyunProperties.getEmail().getSubject())
                .setFromAlias(aliyunProperties.getEmail().getFromAlias())
                .setHtmlBody(
                        text
                );
        RuntimeOptions runtime = new RuntimeOptions();
        // 发送邮件
        client.singleSendMailWithOptions(singleSendMailRequest, runtime);
    }

    public String buildContent(String username, String orderId, String productName, LocalDateTime time){
        //加载邮件html模板
        Resource resource = new ClassPathResource("templates/email.html");
        BufferedReader fileReader = null;
        StringBuffer content = new StringBuffer();
        String line = "";
        try {
            fileReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            while ((line = fileReader.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            log.info("发送邮件读取模板失败{}", e);
        } finally { // 关闭流
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 替换模板中的占位符
        content.replace(content.indexOf("${username}"),content.indexOf("${username}")+"${username}".length(),username);
        content.replace(content.indexOf("${username}"),content.indexOf("${username}")+"${username}".length(),username);
        content.replace(content.indexOf("${orderId}"),content.indexOf("${orderId}")+"${orderId}".length(),orderId);
        content.replace(content.indexOf("${productName}"),content.indexOf("${productName}")+"${productName}".length(),productName);
        content.replace(content.indexOf("${time}"),content.indexOf("${time}")+"${time}".length(),TimeUtils.format(time));
        return content.toString();
    }

    }
