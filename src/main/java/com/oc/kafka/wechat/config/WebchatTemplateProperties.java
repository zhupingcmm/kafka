package com.oc.kafka.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: pzhu
 * @Date: 2023/9/29 15:03
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "template")
public class WebchatTemplateProperties {

    private List<WechatTemplate> templates;
    private int templateResultType;
    private String templateResultFilePath;

}
