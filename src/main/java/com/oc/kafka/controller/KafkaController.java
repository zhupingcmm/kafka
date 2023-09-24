package com.oc.kafka.controller;

import com.oc.kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: pzhu
 * @Date: 2023/8/13 12:56
 */
@RestController
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/message")
    public String sendMessage(@RequestParam String topic, @RequestParam String message) {
        kafkaService.sendMessage(topic, message);
        return "message send";
    }

    @GetMapping("/topics")
    public List<String> getAllTopics() {
        return kafkaService.getAllTopics();
    }

    @PostMapping("/topics")
    public void createTopics(@RequestBody List<String> names) {
        kafkaService.createTopic(names);
    }
    @PostMapping("/topics/describe")
    public void describeTopic (@RequestBody List<String> names) {
        kafkaService.describeTopic(names);
    }

    @PostMapping("/config/describe")
    public void describeConfig (@RequestBody List<String> names) {
        kafkaService.describeConfig(names);
    }


    @DeleteMapping("/topics")
    public void deleteTopic (@RequestBody List<String> names) {
        kafkaService.deleteTopic(names);
    }


}
