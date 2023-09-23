package com.oc.kafka.service;

/**
 * @Author: pzhu
 * @Date: 2023/8/13 12:52
 */
public interface KafkaProducerService {

    void sendMessage(String topic, String message);
}
