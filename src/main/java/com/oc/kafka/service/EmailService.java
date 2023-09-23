package com.oc.kafka.service;

import org.springframework.mail.SimpleMailMessage;

import java.util.Map;

/**
 * @Author: pzhu
 * @Date: 2023/9/6 20:48
 */
public interface EmailService {
    void sendMessage(SimpleMailMessage simpleMailMessage);

    void senTemplateMail(String subject, Map<String, Object> params);
}
