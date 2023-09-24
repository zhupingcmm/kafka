package com.oc.kafka.service;

import java.util.List;
import java.util.Set;

/**
 * @Author: pzhu
 * @Date: 2023/8/13 12:52
 */
public interface KafkaService {

    void sendMessage(String topic, String message);

    void listen(String message);

    List<String> getAllTopics();

    void createTopic(List<String> names);


    void deleteTopic(List<String> names);

    void describeTopic(List<String> names);

    void describeConfig(List<String> names);
}
