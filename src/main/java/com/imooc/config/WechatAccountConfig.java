package com.imooc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    //公众平台id
    private String mpAppId;
    private String mpAppSecret;
    //开放平台
    private String openAppId;
    private String openAppSecret;

    private String mchId;
    private String mchKey;
    private String keyPath;

    /**
     * 微信异步通知
     */
    private String notifyUrl;

    /**
     * 微信模板Id
     */
    private Map<String , String > templateId;
}
