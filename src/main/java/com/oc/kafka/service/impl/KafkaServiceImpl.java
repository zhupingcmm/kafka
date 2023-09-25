package com.oc.kafka.service.impl;

import com.oc.kafka.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
//        Properties properties = new Properties();
//        properties.put(ProducerConfig.ACKS_CONFIG, "all");
//        properties.put(ProducerConfig.RETRIES_CONFIG, "0");
//        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
//        properties.put(ProducerConfig.LINGER_MS_CONFIG, "1");
//        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");

//
//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, message);
//        kafkaTemplate.send(producerRecord);

        try {
            ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, message);
            send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable ex) {
                    log.error(ex.toString());

                }

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    RecordMetadata recordMetadata = result.getRecordMetadata();
                    log.info("Partition: {}, offset {}", recordMetadata.partition(), recordMetadata.offset());
                }
            });


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void createTopic(List<String> names) {
        AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
        List<NewTopic> newTopics = names.stream()
                .map(name -> {
                    short rs = 1;
                    return new NewTopic(name, 1, rs);
                }).collect(Collectors.toList());
        adminClient.createTopics(newTopics);
        adminClient.close();
    }

    @Override
    public void deleteTopic(List<String> names) {
        AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
        adminClient.deleteTopics(names);
        adminClient.close();
    }

    @Override
    public void describeTopic(List<String> names) {

        try {
            AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
            DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(names);
            describeTopicsResult.allTopicIds().get().values().stream().forEach(System.out::println);

        } catch (Exception e) {

        }

    }

    @Override
    public void describeConfig(List<String> names) {
        try {
            AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
            List<ConfigResource> configResources = names.stream().map(name -> new ConfigResource(ConfigResource.Type.TOPIC, name)).collect(Collectors.toList());

            DescribeConfigsResult describeConfigsResult = adminClient.describeConfigs(configResources);
            describeConfigsResult.all().get().values().forEach(System.out::println);

        } catch (Exception e) {

        }
    }

    @Override
    public void increasePartitions(String topic, int partitionNumber) {
        try {
            AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
            Map<String, NewPartitions> partitionsMap = new ConcurrentHashMap<>();
            NewPartitions newPartitions = NewPartitions.increaseTo(partitionNumber);
            partitionsMap.put(topic, newPartitions);
            adminClient.createPartitions(partitionsMap);

        } catch (Exception e) {

        }
    }


}
