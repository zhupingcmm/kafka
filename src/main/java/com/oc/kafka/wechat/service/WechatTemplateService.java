package com.oc.kafka.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.oc.kafka.wechat.config.WechatTemplate;

/**
 * @Author: pzhu
 * @Date: 2023/9/29 15:26
 */
public interface WechatTemplateService {

    WechatTemplate getWechatTemplate();

    void templateReported(JSONObject reportInfo);

    JSONObject templateStatistics(String templateId);


}
