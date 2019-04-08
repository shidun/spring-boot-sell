package com.imooc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrl {
    /**
     * 微信公众平台账号授权url
     */
    public String wxchatMpAuthorize;

    /**
     * 微信开发平台账号授权url
     */
    public String wxchatOpenAuthorize;

    /**
     * 点餐系统url
     */
    public String sell;
}
