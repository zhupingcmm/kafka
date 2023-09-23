package com.oc.kafka.admin;

import org.apache.kafka.clients.admin.*;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @Author: pzhu
 * @Date: 2023/8/12 14:58
 */
public class AdminSample {
    public final static String TOPIC_NAME = "gc";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        createTopic();
//        topicLists();
//        describeTopics();
//        deleteTopics();
    }

    public static AdminClient adminClient() {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "119.3.42.73:9092");
//        properties.setProperty(AdminClientConfig.PORT)

        return AdminClient.create(properties);
    }

    public static void createTopic() {
        AdminClient adminClient = adminClient();
        // 副本集
        short rs = 1;
        NewTopic newTopic = new NewTopic("TOPIC_NAME", 1, rs);
        CreateTopicsResult topics = adminClient.createTopics(Arrays.asList(newTopic));
        System.out.println("topics" + topics);
    }

    public static void topicLists() throws ExecutionException, InterruptedException {
        AdminClient adminClient = adminClient();
        ListTopicsResult listTopics = adminClient.listTopics();
//        Set<String> names = listTopics.names().get();
//        names.forEach(System.out::println);

        Collection<TopicListing>topicListings =listTopics.listings().get();
        topicListings.forEach(System.out::println);
    }

    public static void describeTopics() {
        AdminClient adminClient = adminClient();
        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Arrays.asList("pzhu"));
        System.out.println("describeTopicsResult is " + describeTopicsResult);
    }

    public static void deleteTopics() throws ExecutionException, InterruptedException {
        AdminClient adminClient = adminClient();
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Arrays.asList("test"));
        System.out.println("DeleteTopicsResult is " + deleteTopicsResult);
    }



}
