package com.oc.kafka.controller;

import com.oc.kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: pzhu
 * @Date: 2023/8/13 12:56
 */
@RestController
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @GetMapping("/send")
    public String sendMessage() {
        kafkaService.sendMessage("zp", "hello");
        return "message send";
    }

    @GetMapping("/topics")
    public List<String> getAllTopics() {
        return kafkaService.getAllTopics();
    }
}
