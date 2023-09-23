package com.oc.kafka.service.impl;

import com.oc.kafka.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: pzhu
 * @Date: 2023/8/13 12:52
 */
@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    @Override
    @KafkaListener(topics = "zp", groupId = "my-group")
    public void listen(String message) {
        log.info("Received Message: {}", message);
    }


}
