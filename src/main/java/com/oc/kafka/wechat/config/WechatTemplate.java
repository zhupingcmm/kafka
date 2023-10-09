package com.oc.kafka.wechat.config;

import lombok.Data;

/**
 * @Author: pzhu
 * @Date: 2023/9/29 15:29
 */
@Data
public class WechatTemplate {
    private String templateId;
    private String templateFilePath;
    private boolean active;
}
