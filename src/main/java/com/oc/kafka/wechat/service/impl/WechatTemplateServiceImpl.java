package com.oc.kafka.wechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oc.kafka.wechat.config.WebchatTemplateProperties;
import com.oc.kafka.wechat.config.WechatTemplate;
import com.oc.kafka.wechat.service.WechatTemplateService;
import com.oc.kafka.wechat.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: pzhu
 * @Date: 2023/9/29 15:31
 */
@Slf4j
@Service
public class WechatTemplateServiceImpl implements WechatTemplateService {

    @Autowired
    private WebchatTemplateProperties webchatTemplateProperties;

    @Override
    public WechatTemplate getWechatTemplate() {
        List<WechatTemplate> templates = webchatTemplateProperties.getTemplates();

        Optional<WechatTemplate> wechatTemplate
                = templates.stream().filter(WechatTemplate::isActive).findFirst();

        return wechatTemplate.orElse(null);
    }

    @Override
    public void templateReported(JSONObject reportInfo) {
        log.info("template Reported: [{}]", reportInfo);

    }

    @Override
    public JSONObject templateStatistics(String templateId) {
        // 判断数据结果获取类型
        if(webchatTemplateProperties.getTemplateResultType() == 0){ // 文件获取
            return FileUtils.readFile2JsonObject(webchatTemplateProperties.getTemplateResultFilePath()).get();
        }else{
            // DB ..
        }
        return null;
    }
}
