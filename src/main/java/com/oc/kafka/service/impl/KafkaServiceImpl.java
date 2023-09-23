package com.oc.kafka.service.impl;

import com.oc.kafka.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @Author: pzhu
 * @Date: 2023/8/13 12:52
 */
@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaAdmin kafkaAdmin;

    @Override
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    @Override
    @KafkaListener(topics = "zp", groupId = "my-group")
    public void listen(String message) {
        log.info("Received Message: {}", message);
    }

    @Override
    public List<String> getAllTopics() {


        try (val adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())){

            ListTopicsOptions options = new ListTopicsOptions();
            options.listInternal(true);

            ListTopicsResult listTopics = adminClient.listTopics(options);
            Collection<TopicListing> topicListings = listTopics.listings().get();
           return topicListings.stream().map(TopicListing::name).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
