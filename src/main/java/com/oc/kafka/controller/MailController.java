package com.oc.kafka.controller;

import com.oc.kafka.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: pzhu
 * @Date: 2023/9/6 20:53
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public void sendEmail() {
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom("13774495234@163.com");
//        simpleMailMessage.setText("ok");
//        simpleMailMessage.setSubject("test");
//        simpleMailMessage.setTo("591679403@qq.com");
//        emailService.sendMessage(simpleMailMessage);

        Map<String, Object> params = new HashMap<>();
        params.put("username", "Tom");
        emailService.senTemplateMail("test", params);
    }
}
