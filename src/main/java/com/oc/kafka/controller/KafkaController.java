package com.oc.kafka.controller;

import com.oc.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: pzhu
 * @Date: 2023/8/13 12:56
 */
@RestController
public class KafkaController {

    @Autowired
    private KafkaProducerService producerService;

    @GetMapping("/send")
    public String sendMessage() {
        producerService.sendMessage("zp", "hello");
        return "message send";
    }
}
